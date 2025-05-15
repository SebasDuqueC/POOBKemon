package domain;
import exceptions.PoobkemonException;

import java.util.List;
import java.io.Serializable;

public class Entrenador implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private boolean esHumano;
    private List<Pokemon> pokemones;
    private List<Item> items;
    private int activoIndex;

    public Entrenador(String nombre, boolean esHumano, List<Pokemon> pokemones, List<Item> items) throws PoobkemonException {
        if (pokemones.size() < 2) {
            throw new PoobkemonException("Cada entrenador debe tener al menos 2 pokémones.");
        }
        this.nombre = nombre;
        this.esHumano = esHumano;
        this.pokemones = pokemones;
        this.items = items;
        this.activoIndex = 0; // El primer pokémon es el activo inicialmente
    }

    public Pokemon getActivo() {
        return pokemones.get(activoIndex);
    }

    public void cambiarPokemon(int nuevoIndice) throws PoobkemonException {
        if (nuevoIndice < 0 || nuevoIndice >= pokemones.size()) {
            throw new PoobkemonException("Índice de pokémon inválido.");
        }
        if (pokemones.get(nuevoIndice).estaDebilitado()) {
            throw new PoobkemonException("No puedes enviar a combate un pokémon debilitado.");
        }
        activoIndex = nuevoIndice;
    }

    public void usarItem(int index, Pokemon pokemon) throws PoobkemonException {
            if (index >= items.size()) {
                throw new PoobkemonException("Índice de ítem inválido");
            }
            
            Item item = items.get(index);
            if (!item.disponible()) {
                throw new PoobkemonException("No quedan unidades de este ítem");
            }
            
            // Aplicar el efecto según el tipo de ítem
            switch (item.getTipo()) {
                case POCION:
                case SUPERPOCION:
                case HYPERPOCION:
                    // Usar recuperarPS en lugar de curar
                    pokemon.recuperarPS(item.getPotencia());
                    break;
                case REVIVIR:
                    if (!pokemon.estaDebilitado()) {
                        throw new PoobkemonException("Este ítem solo puede usarse en Pokémon debilitados");
                    }
                    pokemon.revivir();
                    break;
                default:
                    throw new PoobkemonException("Tipo de ítem no implementado");
            }
            
            // Consumir una unidad del ítem
            item.usar();
    }

    public boolean todosDebilitados() {
        for (Pokemon p : pokemones) {
            if (!p.estaDebilitado()) return false;
        }
        return true;
    }

    public String getNombre() { return nombre; }
    public boolean esHumano() { return esHumano; }
    public List<Pokemon> getPokemones() { return pokemones; }
    public List<Item> getItems() { return items; }
    public int getActivoIndex() { return activoIndex; }

    public boolean tienePokemonesVivos() {
        for (Pokemon p : pokemones) {
            if (!p.estaDebilitado()) {
                return true;
            }
        }
        return false;
    }

    public Pokemon getPokemonActivo() {
        return pokemones.get(activoIndex);
    }

    public void reemplazarPokemon() {
        for (int i = 0; i < pokemones.size(); i++) {
            if (!pokemones.get(i).estaDebilitado()) {
                activoIndex = i;
                return;
            }
        }
    }
    
    
}

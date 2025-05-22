package domain;
import exceptions.PoobkemonException;

import java.util.List;
import java.io.Serializable;

/**
 * Representa un entrenador en el juego POOBkemon.
 * Un entrenador puede ser humano o controlado por la máquina, y tiene una lista de Pokémon
 * y objetos que puede usar durante las batallas.
 *
 * Esta clase implementa la interfaz Serializable para permitir la persistencia de los datos
 * del entrenador.
 */

public class Entrenador implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private boolean esHumano;
    private List<Pokemon> pokemones;
    private List<Item> items;
    private int activoIndex;

    /**
     * Constructor para crear un objeto de tipo Entrenador.
     *
     * @param nombre    El nombre del entrenador.
     * @param esHumano  Indica si el entrenador es humano o controlado por la máquina.
     * @param pokemones La lista de Pokémon que posee el entrenador. Debe contener al menos 2 Pokémon.
     * @param items     La lista de ítems que el entrenador puede usar durante las batallas.
     * @throws PoobkemonException Si la lista de Pokémon tiene menos de 2 elementos.
     */

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

    /**
     * Obtiene el Pokémon actualmente activo del entrenador.
     *
     * @return El Pokémon activo.
     */

    public Pokemon getActivo() {
        return pokemones.get(activoIndex);
    }

    /**
     * Cambia el Pokémon activo del entrenador al indicado por el índice.
     *
     * @param nuevoIndice El índice del nuevo Pokémon que será el activo.
     * @throws PoobkemonException Si el índice es inválido o si el Pokémon en el índice especificado está debilitado.
     */

    public void cambiarPokemon(int nuevoIndice) throws PoobkemonException {
        if (nuevoIndice < 0 || nuevoIndice >= pokemones.size()) {
            throw new PoobkemonException("Índice de pokémon inválido.");
        }
        if (pokemones.get(nuevoIndice).estaDebilitado()) {
            throw new PoobkemonException("No puedes enviar a combate un pokémon debilitado.");
        }
        activoIndex = nuevoIndice;
    }

    /**
     * Usa un ítem de la lista de ítems del entrenador en un Pokémon específico.
     *
     * @param index   El índice del ítem en la lista de ítems.
     * @param pokemon El Pokémon en el que se aplicará el ítem.
     * @throws PoobkemonException Si el índice del ítem es inválido, si no quedan unidades del ítem,
     *                            o si el ítem no puede aplicarse al Pokémon en su estado actual.
     */

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
                    // Usar recuperar PS en lugar de curar
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

    /**
     * Verifica si todos los Pokémon del entrenador están debilitados.
     *
     * @return true si todos los Pokémon están debilitados, false en caso contrario.
     */

    public boolean todosDebilitados() {
        for (Pokemon p : pokemones) {
            if (!p.estaDebilitado()) return false;
        }
        return true;
    }

    /**
     * Obtiene el nombre del entrenador.
     *
     * @return El nombre del entrenador.
     */
    public String getNombre() { return nombre; }
    /**
     * Indica si el entrenador es humano.
     *
     * @return true si el entrenador es humano, false si es controlado por la máquina.
     */
    public boolean esHumano() { return esHumano; }
    /**
     * Obtiene la lista de Pokémon del entrenador.
     *
     * @return La lista de Pokémon.
     */
    public List<Pokemon> getPokemones() { return pokemones; }
    /**
     * Obtiene la lista de ítems del entrenador.
     *
     * @return La lista de ítems.
     */
    public List<Item> getItems() { return items; }
    /**
     * Obtiene el índice del Pokémon activo.
     *
     * @return El índice del Pokémon activo.
     */
    public int getActivoIndex() { return activoIndex; }


    /**
     * Verifica si el entrenador tiene al menos un Pokémon que no esté debilitado.
     *
     * @return true si hay al menos un Pokémon vivo, false si todos están debilitados.
     */

    public boolean tienePokemonesVivos() {
        for (Pokemon p : pokemones) {
            if (!p.estaDebilitado()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el Pokémon actualmente activo del entrenador.
     *
     * @return El Pokémon activo.
     */
    public Pokemon getPokemonActivo() {
        return pokemones.get(activoIndex);
    }

    /**
     * Reemplaza el Pokémon activo del entrenador por el primer Pokémon que no esté debilitado.
     * Si todos los Pokémon están debilitados, no realiza ningún cambio.
     */

    public void reemplazarPokemon() {
        for (int i = 0; i < pokemones.size(); i++) {
            if (!pokemones.get(i).estaDebilitado()) {
                activoIndex = i;
                return;
            }
        }
    }
    
    
}

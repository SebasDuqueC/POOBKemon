package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class EntrenadorIA extends Entrenador {
    public EntrenadorIA(String nombre, java.util.List<Pokemon> pokemones, java.util.List<Item> items) throws exceptions.PoobkemonException {
        super(nombre, false, pokemones, items);
    }
    /**
     * Decide la acción a realizar en el turno de la IA.
     * @param batalla La batalla actual
     * @return El índice del movimiento a usar, o -1 para cambiar de Pokémon, o -2 para usar ítem (puedes extenderlo)
     */
    public abstract int decidirAccion(Batalla batalla);

    // Métodos utilitarios para IA
    public static List<Pokemon> generarEquipoAleatorio(int cantidad) {
        List<Pokemon> disponibles = PokemonLoader.obtenerPokemonesDisponibles();
        Collections.shuffle(disponibles, new Random());
        List<Pokemon> equipo = new ArrayList<>();
        for (int i = 0; i < Math.min(cantidad, disponibles.size()); i++) {
            // Clonar el Pokémon para evitar referencias compartidas
            Pokemon base = disponibles.get(i);
            Pokemon clon = PokemonLoader.buscarPokemonPorNombre(base.getNombre());
            equipo.add(clon);
        }
        return equipo;
    }

    public static List<Item> generarItemsAleatorios(int cantidad) {
        List<String> nombres = ItemLoader.obtenerNombresDeItems();
        Collections.shuffle(nombres, new Random());
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < Math.min(cantidad, nombres.size()); i++) {
            items.add(ItemLoader.crearItemConCantidad(nombres.get(i), 2 + new Random().nextInt(3))); // 2-4 unidades
        }
        return items;
    }
} 
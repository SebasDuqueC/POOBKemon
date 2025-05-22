package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Representa un entrenador controlado por la inteligencia artificial (IA) en el juego POOBkemon.
 * Esta clase extiende la funcionalidad de la clase `Entrenador` y proporciona métodos
 * específicos para la toma de decisiones automáticas durante las batallas.
 *
 * La clase es abstracta, por lo que debe ser extendida para implementar el método
 * `decidirAccion`, que define el comportamiento de la IA en cada turno.
 */

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

    /**
     * Genera un equipo aleatorio de Pokémon a partir de la lista de Pokémon disponibles.
     *
     * @param cantidad La cantidad de Pokémon que se desea en el equipo.
     * @return Una lista de Pokémon seleccionados aleatoriamente.
     */

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

    /**
     * Genera una lista de ítems aleatorios a partir de los nombres disponibles en el sistema.
     *
     * @param cantidad La cantidad de ítems que se desea generar.
     * @return Una lista de ítems seleccionados aleatoriamente con cantidades asignadas.
     */

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
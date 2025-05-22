package domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Representa un entrenador controlado por la IA que prioriza estrategias ofensivas.
 * Este entrenador toma decisiones basadas en el estado actual de la batalla,
 * como usar movimientos ofensivos, cambiar de Pokémon o usar ítems curativos.
 */

public class EntrenadorIAAtaque extends EntrenadorIA {
    /**
     * Constructor para un entrenador controlado por IA con estrategias ofensivas.
     *
     * @param nombre    El nombre del entrenador.
     * @param pokemones La lista de Pokémon que forman el equipo del entrenador.
     * @param items     La lista de ítems disponibles para el entrenador.
     * @throws exceptions.PoobkemonException Si el equipo no cumple con las reglas (por ejemplo, menos de 2 Pokémon).
     */
    public EntrenadorIAAtaque(String nombre, List<Pokemon> pokemones, List<Item> items) throws exceptions.PoobkemonException {
        super(nombre, pokemones, items);
    }

    /**
     * Decide la acción que tomará el entrenador IA en el turno actual.
     *
     * Las decisiones se toman en el siguiente orden de prioridad:
     * 1. Si el Pokémon activo tiene menos del 40% de sus PS, intenta usar un ítem curativo.
     * 2. Si no hay ítems curativos disponibles, intenta cambiar a otro Pokémon no debilitado.
     * 3. Si no es necesario curar o cambiar, selecciona un movimiento ofensivo aleatorio.
     * 4. Si no hay movimientos ofensivos disponibles, selecciona un movimiento de estado aleatorio.
     * 5. Si no puede realizar ninguna otra acción, intenta cambiar de Pokémon.
     * 6. Si no hay opciones disponibles, usa el primer movimiento por defecto.
     *
     * @param batalla La batalla actual en la que participa el entrenador.
     * @return El índice del movimiento a usar, un valor negativo para cambiar de Pokémon (-200 - índice del Pokémon),
     *         o un valor negativo para usar un ítem (-100 - índice del ítem).
     */

    @Override
    public int decidirAccion(Batalla batalla) {
        Pokemon activo = getActivo();
        // Si la vida está por debajo del 40%, intenta usar ítem curativo o cambiar
        if (activo.getPsActual() < activo.getPsMaximo() * 0.4) {
            List<Item> items = getItems();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                if (item.disponible() && (item.getTipo() == TipoItem.POCION || item.getTipo() == TipoItem.SUPERPOCION || item.getTipo() == TipoItem.HYPERPOCION)) {
                    return -100 - i;
                }
            }
            // Si no hay ítems, intenta cambiar de Pokémon si hay otro vivo
            List<Pokemon> equipo = getPokemones();
            for (int i = 0; i < equipo.size(); i++) {
                if (i != getActivoIndex() && !equipo.get(i).estaDebilitado()) {
                    return -200 - i;
                }
            }
        }

        // Si no, elige un movimiento ofensivo aleatorio
        List<Movimiento> movimientos = activo.getMovimientos();
        List<Integer> indicesDisponibles = new ArrayList<>();
        for (int i = 0; i < movimientos.size(); i++) {
            Movimiento m = movimientos.get(i);
            if (m.puedeUsarse() && m.getCategoria() != CategoriaMovimiento.ESTADO) {
                indicesDisponibles.add(i);
            }
        }
        if (!indicesDisponibles.isEmpty()) {
            return indicesDisponibles.get(new Random().nextInt(indicesDisponibles.size()));
        }
        // Si solo hay movimientos de estado, elige uno aleatorio
        indicesDisponibles.clear();
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i).puedeUsarse()) {
                indicesDisponibles.add(i);
            }
        }
        if (!indicesDisponibles.isEmpty()) {
            return indicesDisponibles.get(new Random().nextInt(indicesDisponibles.size()));
        }
        // Si no puede hacer nada, intenta cambiar de Pokémon
        List<Pokemon> equipo = getPokemones();
        for (int i = 0; i < equipo.size(); i++) {
            if (i != getActivoIndex() && !equipo.get(i).estaDebilitado()) {
                return -200 - i;
            }
        }
        // Si no puede hacer nada, usa el primer movimiento (aunque esté sin PP, para evitar errores)
        return 0;
    }
} 
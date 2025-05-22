package domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Representa un entrenador controlado por la IA que utiliza estrategias avanzadas y expertas.
 * Este entrenador toma decisiones basadas en el estado actual de la batalla, priorizando
 * movimientos que puedan debilitar al rival, curar a su equipo o realizar cambios estratégicos.
 */
public class EntrenadorIAExperto extends EntrenadorIA {
    /**
     * Constructor para un entrenador controlado por IA con estrategias avanzadas y expertas.
     *
     * @param nombre    El nombre del entrenador.
     * @param pokemones La lista de Pokémon que forman el equipo del entrenador.
     * @param items     La lista de ítems disponibles para el entrenador.
     * @throws exceptions.PoobkemonException Si el equipo no cumple con las reglas (por ejemplo, menos de 2 Pokémon).
     */
    public EntrenadorIAExperto(String nombre, List<Pokemon> pokemones, List<Item> items) throws exceptions.PoobkemonException {
        super(nombre, pokemones, items);
    }

    /**
     * Decide la acción que tomará el entrenador IA experto en el turno actual.
     *
     * Las decisiones se toman en el siguiente orden de prioridad:
     * 1. Si puede debilitar al Pokémon rival con un movimiento ofensivo, lo selecciona.
     * 2. Si el Pokémon activo tiene menos del 40% de sus PS, intenta usar un ítem curativo o cambiar de Pokémon.
     * 3. Si no es necesario curar, prioriza movimientos de estado aleatorios.
     * 4. Si no hay movimientos de estado disponibles, selecciona un movimiento ofensivo aleatorio.
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
        Pokemon rival = batalla.getTurnoRival().getActivo();
        // Si puede debilitar al rival con un ataque, lo hace
        List<Movimiento> movimientos = activo.getMovimientos();
        for (int i = 0; i < movimientos.size(); i++) {
            Movimiento m = movimientos.get(i);
            if (m.puedeUsarse() && m.getPotencia() >= rival.getPsActual() && m.getCategoria() != CategoriaMovimiento.ESTADO) {
                return i;
            }
        }
        // Si está herido, intenta curarse o cambiar
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
        // Si no, prioriza movimientos de estado aleatorios
        List<Integer> indicesEstado = new ArrayList<>();
        for (int i = 0; i < movimientos.size(); i++) {
            Movimiento m = movimientos.get(i);
            if (m.puedeUsarse() && m.getCategoria() == CategoriaMovimiento.ESTADO) {
                indicesEstado.add(i);
            }
        }
        if (!indicesEstado.isEmpty()) {
            return indicesEstado.get(new Random().nextInt(indicesEstado.size()));
        }
        // Si no hay nada especial, usa un movimiento ofensivo aleatorio
        List<Integer> indicesOfensivos = new ArrayList<>();
        for (int i = 0; i < movimientos.size(); i++) {
            Movimiento m = movimientos.get(i);
            if (m.puedeUsarse() && m.getCategoria() != CategoriaMovimiento.ESTADO) {
                indicesOfensivos.add(i);
            }
        }
        if (!indicesOfensivos.isEmpty()) {
            return indicesOfensivos.get(new Random().nextInt(indicesOfensivos.size()));
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
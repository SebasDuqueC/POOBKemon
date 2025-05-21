package domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class EntrenadorIAExperto extends EntrenadorIA {
    public EntrenadorIAExperto(String nombre, List<Pokemon> pokemones, List<Item> items) throws exceptions.PoobkemonException {
        super(nombre, pokemones, items);
    }

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
package domain;

import java.util.ArrayList;
import java.util.Collections;
import domain.CategoriaMovimiento;

/**
 * Clase \`MovimientoFactory\` que proporciona métodos para generar movimientos de Pokémon.
 *
 * Esta clase incluye un método para obtener una lista de cuatro movimientos seleccionados
 * aleatoriamente de un conjunto predefinido. Los movimientos generados pueden variar en tipo,
 * potencia, precisión, puntos de poder (PP) y categoría.
 */
public class MovimientoFactory {

    /**
     * Genera un arreglo de cuatro movimientos seleccionados aleatoriamente
     * de una lista predefinida de movimientos. Los movimientos generados
     * comparten el tipo especificado como parámetro.
     *
     * @param tipo El tipo de los movimientos a generar.
     * @return Un arreglo de cuatro movimientos seleccionados aleatoriamente.
     */

    public static Movimiento[] obtenerCuatroMovimientos(Tipo tipo) {
        // Lista fija o semi-aleatoria de movimientos por tipo
        ArrayList<Movimiento> movimientos = new ArrayList<>();

        movimientos.add(new Movimiento("Ataque Rápido", tipo, 40, 100, 30, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Impactrueno", tipo, 50, 95, 25, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Gruñido", tipo, 0, 100, 40, CategoriaMovimiento.ESTADO));
        movimientos.add(new Movimiento("Reflejo", tipo, 0, 100, 20, CategoriaMovimiento.ESTADO));
        movimientos.add(new Movimiento("Lanzallamas", tipo, 90, 85, 15, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Hiperrayo", tipo, 150, 70, 5, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Placaje", tipo, 35, 95, 35, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Maldición", tipo, 0, 100, 10, CategoriaMovimiento.ESTADO));

        // Mezclar y tomar los primeros 4
        Collections.shuffle(movimientos);
        Movimiento[] seleccionados = new Movimiento[4];
        for (int i = 0; i < 4; i++) {
            seleccionados[i] = movimientos.get(i);
        }

        return seleccionados;
    }
}

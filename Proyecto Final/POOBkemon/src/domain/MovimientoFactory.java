package domain;

import java.util.ArrayList;
import java.util.Collections;
import domain.CategoriaMovimiento;

public class MovimientoFactory {

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

package domain;
import java.util.List;

import exceptions.PoobkemonException;
import java.util.Scanner;

public class Batalla {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private boolean turnoentrenador1; // true = entrenador 1, false = entrenador 2

    public Batalla(Entrenador entrenador1, Entrenador entrenador2) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.turnoentrenador1 = lanzarMoneda();
    }

    private boolean lanzarMoneda() {
        return Math.random() < 0.5;
    }

    public Entrenador getTurnoActual() {
        return turnoentrenador1 ? entrenador1 : entrenador2;
    }

    public Entrenador getTurnoRival() {
        return turnoentrenador1 ? entrenador2 : entrenador1;
    }

    public void cambiarTurno() {
        turnoentrenador1 = !turnoentrenador1;
    }

    public String ejecutarMovimiento(int movimientoIndex) throws PoobkemonException {
        Entrenador actual = getTurnoActual();
        Entrenador rival = getTurnoRival();

        Pokemon atacante = actual.getActivo();
        Pokemon defensor = rival.getActivo();

        Movimiento movimiento = atacante.getMovimiento(movimientoIndex);

        if (!movimiento.puedeUsarse()) {
            throw new PoobkemonException("El movimiento no tiene PP disponibles.");
        }

        movimiento.usar();

        // Cálculo de daño básico (simplificado)
        int danoBase = movimiento.getPotencia();

        int ataque = movimiento.getCategoria() == CategoriaMovimiento.FISICO ? 
                        atacante.getAtaque() : atacante.getAtaqueEspecial();
        int defensa = movimiento.getCategoria() == CategoriaMovimiento.FISICO ?
                        defensor.getDefensa() : defensor.getDefensaEspecial();

        int danio = (int) ((danoBase * ((double) ataque / defensa)) + 2);
        defensor.recibirDanio(danio);

        String resultado = atacante.getNombre() + " usó " + movimiento.getNombre() +
                " e hizo " + danio + " puntos de daño a " + defensor.getNombre() + ".";

        if (defensor.estaDebilitado()) {
            resultado += "\n¡" + defensor.getNombre() + " ha sido debilitado!";
        }

        cambiarTurno();
        return resultado;
    }

    public boolean hayGanador() {
        return entrenador1.todosDebilitados() || entrenador2.todosDebilitados();
    }
    
    public Entrenador getGanador() {
        if (entrenador1.todosDebilitados()) return entrenador2;
        if (entrenador2.todosDebilitados()) return entrenador1;
        return null;
    }

    public void luchar() throws PoobkemonException {
        System.out.println("¡Comienza la batalla entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre() + "!");
    
        while (entrenador1.tienePokemonesVivos() && entrenador2.tienePokemonesVivos()) {
            realizarTurno(entrenador1, entrenador2);
            if (!entrenador2.tienePokemonesVivos()) break;
    
            realizarTurno(entrenador2, entrenador1);
        }
    
        if (entrenador1.tienePokemonesVivos()) {
            System.out.println("¡" + entrenador1.getNombre() + " ha ganado!");
        } else {
            System.out.println("¡" + entrenador2.getNombre() + " ha ganado!");
        }
    }

    private void realizarTurno(Entrenador atacante, Entrenador defensor) throws PoobkemonException {
    Pokemon atacanteActivo = atacante.getPokemonActivo();
    Pokemon defensorActivo = defensor.getPokemonActivo();

    System.out.println("\nTurno de " + atacante.getNombre() + " con " + atacanteActivo.getNombre());
    System.out.println("Movimientos disponibles:");
    List<Movimiento> movimientos = atacanteActivo.getMovimientos();

    for (int i = 0; i < movimientos.size(); i++) {
        Movimiento m = movimientos.get(i);
        System.out.println((i + 1) + ". " + m.getNombre() + " (PP: " + m.getPpActual() + ")");
    }

    int opcion = pedirMovimientoValido(movimientos);
    Movimiento elegido = movimientos.get(opcion);

    System.out.println(atacanteActivo.getNombre() + " usa " + elegido.getNombre() + " sobre " + defensorActivo.getNombre());
    elegido.ejecutar(atacanteActivo, defensorActivo);

    if (defensorActivo.estaDebilitado()) {
        System.out.println(defensorActivo.getNombre() + " ha sido derrotado.");
        defensor.reemplazarPokemon(); // método que deberías tener para cambiar al siguiente
    }
    }
    private int pedirMovimientoValido(List<Movimiento> movimientos) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
    
        while (opcion < 0 || opcion >= movimientos.size()) {
            System.out.println("Elige un movimiento:");
            for (int i = 0; i < movimientos.size(); i++) {
                Movimiento m = movimientos.get(i);
                System.out.println(i + ": " + m.getNombre() + " (PP: " + m.getPpActual() + "/" + m.getPpMaximo() + ")");
            }
    
            try {
                System.out.print("Opción: ");
                opcion = scanner.nextInt();
                Movimiento elegido = movimientos.get(opcion);
                if (elegido.getPpActual() <= 0) {
                    System.out.println("Ese movimiento no tiene PP suficientes. Elige otro.");
                    opcion = -1;
                }
            } catch (Exception e) {
                System.out.println("Opción inválida.");
                scanner.nextLine(); // Limpiar buffer
                opcion = -1;
            }
        }
    
        return opcion;
    }
    

}
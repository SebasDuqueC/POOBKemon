package domain;
import java.util.List;

import exceptions.PoobkemonException;
import java.util.Scanner;

/**
 * La clase `Batalla` representa una batalla entre dos entrenadores Pokémon.
 * Gestiona los turnos, movimientos y el estado de los Pokémon durante la batalla.
 */

public class Batalla {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private boolean turnoentrenador1; // true = entrenador 1, false = entrenador 2

    /**
     * Constructor de la clase `Batalla`.
     * Inicializa una batalla entre dos entrenadores Pokémon y determina
     * aleatoriamente quién comienza el primer turno.
     *
     * @param entrenador1 El primer entrenador participante en la batalla.
     * @param entrenador2 El segundo entrenador participante en la batalla.
     */

    public Batalla(Entrenador entrenador1, Entrenador entrenador2) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.turnoentrenador1 = lanzarMoneda();
    }

    /**
     * Lanza una moneda virtual para determinar aleatoriamente un valor booleano.
     *
     * @return `true` si el resultado es cara, `false` si es cruz.
     */

    private boolean lanzarMoneda() {
        return Math.random() < 0.5;
    }

    /**
     * Obtiene el entrenador que tiene el turno actual en la batalla.
     *
     * @return El entrenador cuyo turno es en este momento.
     */

    public Entrenador getTurnoActual() {
        return turnoentrenador1 ? entrenador1 : entrenador2;
    }

    /**
     * Obtiene el entrenador rival al que tiene el turno actual.
     *
     * @return El entrenador que no tiene el turno en este momento.
     */

    public Entrenador getTurnoRival() {
        return turnoentrenador1 ? entrenador2 : entrenador1;
    }

    /**
     * Cambia el turno actual de la batalla al otro entrenador.
     * Si el turno actual pertenece al entrenador 1, se cambia al entrenador 2, y viceversa.
     */

    public void cambiarTurno() {
        turnoentrenador1 = !turnoentrenador1;
    }

    /**
     * Ejecuta un movimiento seleccionado por el entrenador que tiene el turno actual.
     * Este método realiza el cálculo del daño infligido al Pokémon rival, verifica si
     * el movimiento tiene PP disponibles, aplica el daño y cambia el turno al finalizar.
     *
     * @param movimientoIndex El índice del movimiento que se desea ejecutar.
     * @return Un mensaje que describe el resultado del movimiento, incluyendo el daño
     *         infligido y si el Pokémon rival ha sido debilitado.
     * @throws PoobkemonException Si el movimiento seleccionado no tiene PP disponibles.
     */

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

        // Cálculo de daño básico
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

    /**
     * Verifica si hay un ganador en la batalla.
     * Un ganador se determina si todos los Pokémon de uno de los entrenadores están debilitados.
     *
     * @return `true` si uno de los entrenadores ha perdido todos sus Pokémon, `false` en caso contrario.
     */

    public boolean hayGanador() {
        return entrenador1.todosDebilitados() || entrenador2.todosDebilitados();
    }

    /**
     * Obtiene el entrenador ganador de la batalla.
     * Si todos los Pokémon de un entrenador están debilitados, el otro entrenador es declarado ganador.
     * Si ninguno de los entrenadores ha perdido todos sus Pokémon, no hay un ganador aún.
     *
     * @return El entrenador ganador si hay uno, o `null` si no hay un ganador.
     */

    public Entrenador getGanador() {
        if (entrenador1.todosDebilitados()) return entrenador2;
        if (entrenador2.todosDebilitados()) return entrenador1;
        return null;
    }

    /**
     * Inicia la batalla entre los dos entrenadores Pokémon.
     * Este método gestiona el flujo principal de la batalla, alternando turnos
     * entre los entrenadores hasta que uno de ellos se quede sin Pokémon vivos.
     * Al final, se declara al ganador.
     *
     * @throws PoobkemonException Si ocurre un error durante la ejecución de un turno,
     *                            como intentar usar un movimiento sin PP disponibles.
     */

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

    /**
     * Realiza el turno de un entrenador en la batalla.
     * Este método gestiona la ejecución de un movimiento por parte del Pokémon activo
     * del entrenador atacante hacia el Pokémon activo del entrenador defensor.
     * También verifica si el Pokémon defensor ha sido debilitado y, de ser así,
     * solicita al entrenador defensor que reemplace su Pokémon activo.
     *
     * @param atacante El entrenador que realiza el turno.
     * @param defensor El entrenador que recibe el ataque.
     * @throws PoobkemonException Si ocurre un error durante la ejecución del movimiento,
     *                            como intentar usar un movimiento sin PP disponibles.
     */

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

    /**
     * Solicita al usuario que seleccione un movimiento válido de la lista de movimientos disponibles.
     * Este método muestra los movimientos disponibles con sus PP actuales y máximos, y valida que
     * el usuario elija un movimiento con PP suficientes.
     *
     * @param movimientos La lista de movimientos disponibles del Pokémon activo.
     * @return El índice del movimiento seleccionado por el usuario.
     */

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
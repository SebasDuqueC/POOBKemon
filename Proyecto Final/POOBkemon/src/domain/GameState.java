package domain;

import java.io.Serializable;
import java.util.Date;
/**
 * Representa el estado actual del juego en un momento dado.
 * Esta clase implementa la interfaz Serializable para permitir la persistencia del estado del juego.
 * Contiene información sobre los entrenadores, el turno actual, el modo de juego y la fecha de guardado.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private boolean turnoEntrenador1;
    private String modoJuego;
    private Date fechaGuardado;

    /**
     * Constructor para crear un estado del juego.
     *
     * @param entrenador1       El primer entrenador participante en el juego.
     * @param entrenador2       El segundo entrenador participante en el juego.
     * @param turnoEntrenador1  Indica si es el turno del primer entrenador (true) o del segundo (false).
     * @param modoJuego         El modo de juego actual (por ejemplo, "Clásico", "Competitivo").
     */

    public GameState(Entrenador entrenador1, Entrenador entrenador2, 
                     boolean turnoEntrenador1, String modoJuego) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.turnoEntrenador1 = turnoEntrenador1;
        this.modoJuego = modoJuego;
        this.fechaGuardado = new Date();
    }

    /**
     * Obtiene el primer entrenador participante en el juego.
     *
     * @return El primer entrenador.
     */
    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    /**
     * Obtiene el segundo entrenador participante en el juego.
     *
     * @return El segundo entrenador.
     */
    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    /**
     * Verifica si es el turno del primer entrenador.
     *
     * @return true si es el turno del primer entrenador, false en caso contrario.
     */
    public boolean isTurnoEntrenador1() {
        return turnoEntrenador1;
    }

    /**
     * Obtiene el modo de juego actual.
     *
     * @return Una cadena que representa el modo de juego, ("Normal" o "Supervivencia")
     */
    public String getModoJuego() {
        return modoJuego;
    }

    /**
     * Obtiene la fecha en la que se guardó el estado del juego.
     *
     * @return La fecha de guardado del estado del juego.
     */
    public Date getFechaGuardado() {
        return fechaGuardado;
    }
}

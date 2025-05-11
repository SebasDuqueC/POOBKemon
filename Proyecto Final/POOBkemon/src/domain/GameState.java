package domain;

import java.io.Serializable;
import java.util.Date;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private boolean turnoEntrenador1;
    private String modoJuego;
    private Date fechaGuardado;
    
    public GameState(Entrenador entrenador1, Entrenador entrenador2, 
                     boolean turnoEntrenador1, String modoJuego) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.turnoEntrenador1 = turnoEntrenador1;
        this.modoJuego = modoJuego;
        this.fechaGuardado = new Date();
    }
    
    public Entrenador getEntrenador1() {
        return entrenador1;
    }
    
    public Entrenador getEntrenador2() {
        return entrenador2;
    }
    
    public boolean isTurnoEntrenador1() {
        return turnoEntrenador1;
    }
    
    public String getModoJuego() {
        return modoJuego;
    }
    
    public Date getFechaGuardado() {
        return fechaGuardado;
    }
}

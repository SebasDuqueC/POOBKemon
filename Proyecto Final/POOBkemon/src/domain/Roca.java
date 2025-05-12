package domain;

public class Roca extends Pokemon {
    
    public Roca(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " lanza Avalancha de Rocas!";
    }
}

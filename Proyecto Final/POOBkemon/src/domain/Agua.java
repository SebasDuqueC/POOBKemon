package domain;

public class Agua extends Pokemon {
    
    public Agua(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " lanza Torrente de Agua!";
    }
}
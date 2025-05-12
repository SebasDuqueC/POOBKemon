package domain;

public class Acero extends Pokemon {
    
    public Acero(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Puño Meteoro!";
    }
}
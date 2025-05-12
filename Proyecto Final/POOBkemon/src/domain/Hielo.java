package domain;

public class Hielo extends Pokemon {
    
    public Hielo(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Rayo Hielo!";
    }
}
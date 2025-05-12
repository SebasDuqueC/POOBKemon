package domain;

public class Tierra extends Pokemon {
    
    public Tierra(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " desata Terremoto!";
    }
}
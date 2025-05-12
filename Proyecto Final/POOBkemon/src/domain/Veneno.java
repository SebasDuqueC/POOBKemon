package domain;

public class Veneno extends Pokemon {
    
    public Veneno(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " lanza Bomba Lodo!";
    }
}
package domain;

public class Electrico extends Pokemon {
    
    public Electrico(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " lanza Impactrueno!";
    }
}
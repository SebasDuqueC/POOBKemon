package domain;

public class Planta extends Pokemon {
    
    public Planta(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " lanza Látigo Cepa!";
    }
}
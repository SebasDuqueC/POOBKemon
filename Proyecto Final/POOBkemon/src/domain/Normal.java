package domain;

public class Normal extends Pokemon {
    
    public Normal(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Golpe Cuerpo!";
    }
}
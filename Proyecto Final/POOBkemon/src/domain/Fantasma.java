package domain;

public class Fantasma extends Pokemon {
    
    public Fantasma(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Bola Sombra!";
    }
}
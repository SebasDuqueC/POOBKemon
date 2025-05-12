package domain;

public class Siniestro extends Pokemon {
    
    public Siniestro(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Bola Sombra!";
    }
}
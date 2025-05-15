package domain;

public class Siniestro extends Pokemon {
    
    public Siniestro(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Bola Sombra!";
    }
}
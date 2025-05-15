package domain;

public class Roca extends Pokemon {
    
    public Roca(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Lanza Rocas!";
    }
}
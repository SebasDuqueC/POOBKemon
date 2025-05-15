package domain;

public class Fuego extends Pokemon {
    
    public Fuego(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Lanzallamas!";
    }
}
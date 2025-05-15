package domain;

public class Lucha extends Pokemon {
    
    public Lucha(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Golpe Karate!";
    }
}
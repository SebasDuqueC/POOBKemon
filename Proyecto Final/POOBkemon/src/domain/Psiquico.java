package domain;

public class Psiquico extends Pokemon {
    
    public Psiquico(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Psíquico!";
    }
}
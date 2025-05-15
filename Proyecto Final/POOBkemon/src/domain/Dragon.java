package domain;

public class Dragon extends Pokemon {
    
    public Dragon(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }
    
    @Override
    public String mover() {
        return nombre + " usa Furia Dragón!";
    }
}
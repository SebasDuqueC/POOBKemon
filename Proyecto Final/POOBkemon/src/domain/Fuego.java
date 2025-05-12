package domain;

public class Fuego extends Pokemon {
    
    public Fuego(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " lanza Llama Infernal!";
    }
}

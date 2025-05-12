package domain;

public class Dragon extends Pokemon {
    
    public Dragon(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " lanza Aliento de Dragón!";
    }
}
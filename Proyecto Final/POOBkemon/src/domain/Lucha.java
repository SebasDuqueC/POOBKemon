package domain;

public class Lucha extends Pokemon {
    
    public Lucha(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " ejecuta Patada Salto!";
    }
}
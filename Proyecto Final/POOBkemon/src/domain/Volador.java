package domain;

public class Volador extends Pokemon {
    
    public Volador(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }
    
    @Override
    public String mover() {
        return nombre + " ejecuta Ataque Ala!";
    }
}
package domain;
/**
 * La clase `Agua` representa un Pokémon de tipo Agua.
 * Hereda de la clase base `Pokemon` y sobrescribe el método `mover`.
 */
public class Agua extends Pokemon {

    /**
     * Constructor para crear un Pokémon de tipo Agua.
     *
     * @param nombre           El nombre del Pokémon.
     * @param ps               Los puntos de salud (PS) del Pokémon.
     * @param ataque           El valor de ataque del Pokémon.
     * @param defensa          El valor de defensa del Pokémon.
     * @param velocidad        La velocidad del Pokémon.
     * @param ataqueEspecial   El valor de ataque especial del Pokémon.
     * @param defensaEspecial  El valor de defensa especial del Pokémon.
     */

    public Agua(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }

    /**
     * Realiza el movimiento característico de un Pokémon de tipo Agua.
     *
     * @return Un mensaje indicando que el Pokémon usa el movimiento "Hidrobomba".
     */

    @Override
    public String mover() {
        return nombre + " usa Hidrobomba!";
    }
}
package domain;
/**
 * La clase `Acero` representa un Pokémon de tipo Acero.
 * Hereda de la clase base `Pokemon` y sobrescribe el método `mover`.
 */
public class Acero extends Pokemon {

    /**
     * Constructor para crear un Pokémon de tipo Acero.
     *
     * @param nombre           El nombre del Pokémon.
     * @param ps               Los puntos de salud (PS) del Pokémon.
     * @param ataque           El valor de ataque del Pokémon.
     * @param defensa          El valor de defensa del Pokémon.
     * @param velocidad        La velocidad del Pokémon.
     * @param ataqueEspecial   El valor de ataque especial del Pokémon.
     * @param defensaEspecial  El valor de defensa especial del Pokémon.
     */

    public Acero(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }

    /**
     * Sobrescribe el método `mover` para definir el movimiento característico
     * de un Pokémon de tipo Acero.
     *
     * @return Una cadena que describe el movimiento "Puño Meteoro" realizado por el Pokémon.
     */
    @Override
    public String mover() {
        return nombre + " usa Puño Meteoro!";
    }
}
package domain;

/**
 * Clase Normal que representa un Pokémon de tipo Normal.
 *
 * Esta clase extiende la clase base \`Pokemon\` y define el comportamiento
 * específico de un Pokémon de tipo Normal. Incluye un método sobrescrito
 * para realizar un movimiento característico.
 */
public class Normal extends Pokemon {

    /**
     * Constructor para crear un Pokémon de tipo Normal.
     *
     * @param nombre El nombre del Pokémon.
     * @param ps Los puntos de salud (PS) del Pokémon.
     * @param ataque El valor de ataque del Pokémon.
     * @param defensa El valor de defensa del Pokémon.
     * @param velocidad La velocidad del Pokémon.
     * @param ataqueEspecial El valor de ataque especial del Pokémon.
     * @param defensaEspecial El valor de defensa especial del Pokémon.
     */

    public Normal(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }

    /**
     * Realiza el movimiento característico de un Pokémon de tipo Normal.
     *
     * Este método sobrescribe el comportamiento de la clase base \`Pokemon\`
     * para definir un movimiento específico del tipo Normal.
     *
     * @return Una cadena que describe el movimiento realizado por el Pokémon.
     */
    @Override
    public String mover() {
        return nombre + " usa Golpe Cuerpo!";
    }
}
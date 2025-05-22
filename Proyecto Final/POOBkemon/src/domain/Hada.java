package domain;

/**
 * Representa un Pokémon de tipo Hada.
 * Esta clase extiende la clase base `Pokemon` y define el comportamiento específico
 * de un Pokémon de tipo Hada, incluyendo su movimiento característico.
 */
public class Hada extends Pokemon {

    /**
     * Constructor para crear un Pokémon de tipo Hada.
     *
     * @param nombre           El nombre del Pokémon.
     * @param ps               Los puntos de salud (PS) del Pokémon.
     * @param ataque           El valor de ataque físico del Pokémon.
     * @param defensa          El valor de defensa física del Pokémon.
     * @param velocidad        La velocidad del Pokémon.
     * @param ataqueEspecial   El valor de ataque especial del Pokémon.
     * @param defensaEspecial  El valor de defensa especial del Pokémon.
     */
    public Hada(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }

    /**
     * Realiza el movimiento característico de un Pokémon de tipo Hada.
     *
     * @return Un mensaje indicando que el Pokémon usa el movimiento "Viento Feérico".
     */
    @Override
    public String mover() {
        return nombre + " usa Viento Feérico!";
    }
}
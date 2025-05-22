package domain;

/**
 * Representa un Pokémon de tipo Siniestro.
 *
 * La clase `Siniestro` extiende la clase base `Pokemon` y define el comportamiento
 * específico de un Pokémon de tipo Siniestro, incluyendo su movimiento característico.
 */
public class Siniestro extends Pokemon {

    /**
     * Constructor para crear un Pokémon de tipo Siniestro.
     *
     * @param nombre El nombre del Pokémon.
     * @param ps Los puntos de salud (PS) del Pokémon.
     * @param ataque El valor de ataque del Pokémon.
     * @param defensa El valor de defensa del Pokémon.
     * @param velocidad La velocidad del Pokémon.
     * @param ataqueEspecial El valor de ataque especial del Pokémon.
     * @param defensaEspecial El valor de defensa especial del Pokémon.
     */
    public Siniestro(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        super(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
    }

    /**
     * Realiza el movimiento característico de un Pokémon de tipo Siniestro.
     *
     * Este método sobrescribe el método `mover` de la clase base `Pokemon`
     * para proporcionar un comportamiento específico para los Pokémon de tipo Siniestro.
     *
     * @return Una cadena que describe el movimiento característico del Pokémon.
     */
    @Override
    public String mover() {
        return nombre + " usa Bola Sombra!";
    }
}
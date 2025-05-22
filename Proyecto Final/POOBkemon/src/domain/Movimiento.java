package domain;
import exceptions.PoobkemonException;
import java.io.Serializable;

/**
 * Representa un movimiento que un Pokémon puede realizar durante una batalla.
 * Esta clase incluye información sobre el nombre, tipo, potencia, precisión,
 * puntos de poder (PP) y la categoría del movimiento. También permite ejecutar
 * el movimiento y verificar si puede ser usado.
 *
 * Implementa la interfaz Serializable para permitir la serialización de objetos
 * de esta clase.
 */

public class Movimiento  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private Tipo tipo;
    private int potencia;
    private int precision;
    private int ppMaximo;
    private int ppActual;
    private CategoriaMovimiento categoria;

    /**
     * Constructor para crear un movimiento que un Pokémon puede realizar.
     *
     * @param nombre     El nombre del movimiento.
     * @param tipo       El tipo del movimiento (por ejemplo, Agua, Fuego, etc.).
     * @param potencia   La potencia del movimiento, que afecta el daño causado.
     * @param precision  La precisión del movimiento, representada como un porcentaje.
     * @param ppMaximo   Los puntos de poder (PP) máximos del movimiento.
     * @param categoria  La categoría del movimiento (físico, especial o de estado).
     */

    public Movimiento(String nombre, Tipo tipo, int potencia, int precision, int ppMaximo, CategoriaMovimiento categoria) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.precision = precision;
        this.ppMaximo = ppMaximo;
        this.ppActual = ppMaximo;
        this.categoria = categoria;
    }

    /**
     * Verifica si el movimiento puede ser usado.
     * Un movimiento puede usarse si tiene al menos 1 punto de poder (PP) disponible.
     *
     * @return true si el movimiento tiene PP disponibles, false en caso contrario.
     */

    public boolean puedeUsarse() {
        return ppActual > 0;
    }

    /**
     * Reduce en 1 el número de puntos de poder (PP) actuales del movimiento.
     * Si no hay PP disponibles, lanza una excepción indicando que el movimiento no puede usarse.
     *
     * @throws PoobkemonException Si el movimiento no tiene PP disponibles.
     */
    public void usar() throws PoobkemonException {
        if (!puedeUsarse()) {
            throw new PoobkemonException("El movimiento no tiene PP disponibles.");
        }
        ppActual--;
    }

    /**
     * Restaura los puntos de poder (PP) actuales del movimiento al máximo.
     * Este método se utiliza para recargar completamente los PP de un movimiento,
     * dejándolo listo para ser usado nuevamente.
     */
    public void restaurarPP() {
        ppActual = ppMaximo;
    }

    // Getters
    /**
     * Obtiene el nombre del movimiento.
     *
     * @return El nombre del movimiento.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el tipo del movimiento.
     *
     * @return El tipo del movimiento.
     */
    public Tipo getTipo() { return tipo; }

    /**
     * Obtiene la potencia del movimiento.
     *
     * @return La potencia del movimiento.
     */
    public int getPotencia() { return potencia; }

    /**
     * Obtiene la precisión del movimiento.
     *
     * @return La precisión del movimiento.
     */

    public int getPrecision() { return precision; }

    /**
     * Obtiene los puntos de poder (PP) actuales del movimiento.
     *
     * @return Los PP actuales del movimiento.
     */
    public int getPpActual() { return ppActual; }

    /**
     * Obtiene los puntos de poder (PP) máximos del movimiento.
     *
     * @return Los PP máximos del movimiento.
     */
    public int getPpMaximo() { return ppMaximo; }

    /**
     * Obtiene la categoría del movimiento.
     *
     * @return La categoría del movimiento.
     */
    public CategoriaMovimiento getCategoria() { return categoria; }

    /**
     * Ejecuta el movimiento contra un Pokémon defensor.
     * Este método calcula si el ataque acierta, calcula el daño y lo aplica al defensor.
     *
     * @param atacante El Pokémon que realiza el ataque.
     * @param defensor El Pokémon que recibe el ataque.
     */

    public void ejecutar(Pokemon atacante, Pokemon defensor) {
        if (ppActual <= 0) {
            System.out.println("No se puede usar " + nombre + ". No quedan PP.");
            return;
        }
    
        ppActual--;
    
        // Calcular si el ataque acierta
        int probabilidad = (int)(Math.random() * 100);
        if (probabilidad >= precision) {
            System.out.println(nombre + " falló.");
            return;
        }
    
        // Calcular daño básico (puedes ajustar este cálculo más adelante)
        int danio = (int)((potencia * (atacante.getAtaque() / (double)defensor.getDefensa())) + 2);
        defensor.recibirDanio(danio);
    
        System.out.println(defensor.getNombre() + " recibió " + danio + " de daño.");
    }
    
}

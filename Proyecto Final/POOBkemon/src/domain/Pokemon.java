package domain;

import exceptions.PoobkemonException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta Pokemon que representa la base para todos los Pokémon en el juego.
 *
 * Esta clase implementa la interfaz Serializable para permitir la serialización de objetos
 * y define los atributos y comportamientos comunes a todos los Pokémon. Incluye métodos para
 * gestionar la salud, movimientos, experiencia y estado de los Pokémon, así como un método
 * abstracto que debe ser implementado por las subclases para definir un movimiento característico.
 */
public abstract class Pokemon implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String nombre;
    protected int psMaximo;
    protected int psActual;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int ataqueEspecial;
    protected int defensaEspecial;
    protected List<Movimiento> movimientos;
    protected boolean debilitado;
    protected int nivel = 50; // Valor por defecto
    protected int experiencia = 0;
    protected int experienciaMaxima = 100;

    /**
     * Constructor de la clase abstracta `Pokemon`.
     *
     * Este constructor inicializa los atributos básicos de un Pokémon, como su nombre,
     * puntos de salud (PS), estadísticas de combate y movimientos. Además, asigna
     * automáticamente un conjunto de cuatro movimientos al Pokémon basados en su tipo.
     *
     * @param nombre El nombre del Pokémon.
     * @param ps Los puntos de salud máximos del Pokémon.
     * @param ataque El valor de ataque del Pokémon.
     * @param defensa El valor de defensa del Pokémon.
     * @param velocidad La velocidad del Pokémon.
     * @param ataqueEspecial El valor de ataque especial del Pokémon.
     * @param defensaEspecial El valor de defensa especial del Pokémon.
     */

    public Pokemon(String nombre, int ps, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial) {
        this.nombre = nombre;
        this.psMaximo = ps;
        this.psActual = ps;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.movimientos = new ArrayList<>();
        this.debilitado = false;
        
        // Obtener el tipo basado en la clase
        Tipo tipoPokemon = Tipo.valueOf(this.getClass().getSimpleName().toUpperCase());
        
        // Asignar movimientos usando MovimientoFactory
        Movimiento[] movimientosGenerados = MovimientoFactory.obtenerCuatroMovimientos(tipoPokemon);
        for (int i = 0; i < 4; i++) {
            if (movimientosGenerados[i] != null) {
                this.movimientos.add(movimientosGenerados[i]);
            }
        }
    }
    
    // Getters
    /**
     * Obtiene el nombre del Pokémon.
     *
     * @return El nombre del Pokémon.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene los puntos de salud máximos (PS) del Pokémon.
     *
     * @return Los puntos de salud máximos del Pokémon.
     */
    public int getVida() { return psMaximo; }

    /**
     * Obtiene los puntos de salud actuales (PS) del Pokémon.
     *
     * @return Los puntos de salud actuales del Pokémon.
     */
    public int getPsActual() { return psActual; }

    /**
     * Obtiene los puntos de salud máximos (PS) del Pokémon.
     *
     * Este método devuelve el valor máximo de puntos de salud que el Pokémon puede tener.
     *
     * @return El valor de los puntos de salud máximos del Pokémon.
     */
    public int getPsMaximo() { return psMaximo; }

    /**
     * Obtiene el valor de ataque del Pokémon.
     *
     * Este método devuelve el valor de la estadística de ataque del Pokémon,
     * que se utiliza para calcular el daño de los movimientos físicos.
     *
     * @return El valor de ataque del Pokémon.
     */
    public int getAtaque() { return ataque; }

    /**
     * Obtiene el valor de defensa del Pokémon.
     *
     * Este método devuelve el valor de la estadística de defensa del Pokémon,
     * que se utiliza para reducir el daño recibido de los movimientos físicos.
     *
     * @return El valor de defensa del Pokémon.
     */
    public int getDefensa() { return defensa; }

    /**
     * Obtiene el valor de ataque especial del Pokémon.
     *
     * Este método devuelve el valor de la estadística de ataque especial del Pokémon,
     * que se utiliza para calcular el daño de los movimientos especiales.
     *
     * @return El valor de ataque especial del Pokémon.
     */
    public int getAtaqueEspecial() { return ataqueEspecial; }

    /**
     * Obtiene el valor de defensa especial del Pokémon.
     *
     * Este método devuelve el valor de la estadística de defensa especial del Pokémon,
     * que se utiliza para reducir el daño recibido de los movimientos especiales.
     *
     * @return El valor de defensa especial del Pokémon.
     */
    public int getDefensaEspecial() { return defensaEspecial; }

    /**
     * Obtiene la velocidad del Pokémon.
     *
     * Este método devuelve el valor de la estadística de velocidad del Pokémon,
     * que se utiliza para determinar el orden de ataque en combate.
     *
     * @return La velocidad del Pokémon.
     */
    public int getVelocidad() { return velocidad; }

    /**
     * Obtiene la lista de movimientos del Pokémon.
     *
     * Este método devuelve una lista de objetos `Movimiento` que representan
     * los movimientos que el Pokémon puede realizar en combate.
     *
     * @return La lista de movimientos del Pokémon.
     */
    public List<Movimiento> getMovimientos() { return movimientos; }


    /**
     * Obtiene el nivel actual del Pokémon.
     *
     * Este método devuelve el nivel del Pokémon, que determina su
     * fuerza general y su progreso en el juego.
     *
     * @return El nivel actual del Pokémon.
     */
    public int getNivel() { return nivel; }

    /**
     * Obtiene la experiencia actual del Pokémon.
     *
     * Este método devuelve la cantidad de experiencia acumulada
     * por el Pokémon, que se utiliza para determinar su progreso
     * hacia el siguiente nivel.
     *
     * @return La experiencia actual del Pokémon.
     */
    public int getExperiencia() { return experiencia; }

    /**
     * Obtiene la experiencia máxima del Pokémon.
     *
     * Este método devuelve la cantidad de experiencia necesaria
     * para que el Pokémon alcance el siguiente nivel.
     *
     * @return La experiencia máxima del Pokémon.
     */
    public int getExperienciaMaxima() { return experienciaMaxima; }
    
    // Métodos necesarios para el funcionamiento del juego

    /**
     * Verifica si el Pokémon está debilitado.
     *
     * Este método comprueba si los puntos de salud actuales del Pokémon
     * son menores o iguales a cero, lo que indica que el Pokémon ha sido
     * derrotado en combate.
     *
     * @return true si el Pokémon está debilitado, false en caso contrario.
     */
    public boolean estaDebilitado() {
        return psActual <= 0;
    }


    /**
     * Reduce los puntos de salud (PS) actuales del Pokémon al recibir daño.
     *
     * Este método disminuye los PS actuales del Pokémon en función de la cantidad
     * de daño recibido. Si el daño recibido es mayor o igual a los PS actuales,
     * los PS se establecen en 0, indicando que el Pokémon está debilitado.
     *
     * @param danio La cantidad de daño que el Pokémon recibe.
     */
    public void recibirDanio(int danio) {
        psActual = Math.max(0, psActual - danio);
    }

    /**
     * Restaura los puntos de salud (PS) actuales del Pokémon.
     *
     * Este método aumenta los PS actuales del Pokémon en una cantidad
     * especificada, sin exceder el máximo de PS. Si la cantidad a restaurar
     * es mayor que el máximo, se establece en el valor máximo.
     *
     * @param cantidad La cantidad de PS a restaurar.
     */
    public void recuperarPS(int cantidad) {
        psActual = Math.min(psMaximo, psActual + cantidad);
    }


    /**
     * Revive al Pokémon si está debilitado.
     *
     * Este método verifica si el Pokémon está debilitado (es decir, si sus puntos de salud actuales son 0).
     * Si está debilitado, restaura sus puntos de salud actuales a la mitad de sus puntos de salud máximos.
     */
    public void revivir() {
        if (estaDebilitado()) {
            psActual = psMaximo / 2;
        }
    }

    /**
     * Obtiene un movimiento específico del Pokémon basado en su índice.
     *
     * Este método permite acceder a un movimiento de la lista de movimientos del Pokémon
     * utilizando un índice. Si el índice proporcionado está fuera de los límites de la lista,
     * se lanza una excepción `PoobkemonException`.
     *
     * @param index El índice del movimiento a obtener.
     * @return El movimiento correspondiente al índice especificado.
     * @throws PoobkemonException Si el índice está fuera de los límites de la lista de movimientos.
     */
    public Movimiento getMovimiento(int index) throws PoobkemonException {
        if (index < 0 || index >= movimientos.size()) {
            throw new PoobkemonException("Índice de movimiento inválido");
        }
        return movimientos.get(index);
    }

    /**
     * Método abstracto que debe ser implementado por las subclases para definir
     * un movimiento característico del Pokémon.
     *
     * Este método permite que cada tipo específico de Pokémon tenga un movimiento
     * único que lo represente, proporcionando una descripción del movimiento realizado.
     *
     * @return Una cadena que describe el movimiento característico del Pokémon.
     */
    public abstract String mover();

    
}
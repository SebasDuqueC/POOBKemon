package domain;

import java.io.Serializable;

/**
 * Representa un objeto o ítem abstracto que puede ser utilizado en el juego.
 * Esta clase implementa la interfaz Serializable para permitir la persistencia de los ítems.
 * Cada ítem tiene un nombre, tipo, cantidad disponible, descripción y potencia.
 * 
 * Esta clase está diseñada para ser extendida por tipos específicos de ítems.
 */
public abstract class Item implements Serializable {
    protected String nombre;
    protected TipoItem tipo;
    protected int cantidad;
    protected String descripcion;
    protected int potencia;

    /**
     * Constructor que crea un ítem con un nombre, tipo y cantidad,
     * asignando valores predeterminados para la descripción y potencia.
     *
     * @param nombre   El nombre del ítem.
     * @param tipo     El tipo del ítem (por ejemplo, poción, ataque, etc.).
     * @param cantidad La cantidad inicial disponible del ítem.
     */
    public Item(String nombre, TipoItem tipo, int cantidad) {
        this(nombre, tipo, cantidad, "", 0);
    }

    /**
     * Constructor que crea un ítem con un nombre, tipo, cantidad, descripción y potencia.
     *
     * @param nombre      El nombre del ítem.
     * @param tipo        El tipo del ítem (por ejemplo, poción, ataque, etc.).
     * @param cantidad    La cantidad inicial disponible del ítem.
     * @param descripcion Una descripción del ítem.
     * @param potencia    La potencia del ítem (por ejemplo, cuántos PS recupera).
     */
    public Item(String nombre, TipoItem tipo, int cantidad, String descripcion, int potencia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.potencia = potencia;
        inicializar();
    }
    
    /**
     * Método de inicialización que las subclases pueden sobrescribir para
     * realizar configuraciones adicionales durante la creación del ítem.
     */
    protected void inicializar() {
        // Por defecto no hace nada, las subclases pueden sobrescribir
    }

    // Getters
    /**
     * Obtiene el nombre del ítem.
     *
     * @return El nombre del ítem.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el tipo del ítem.
     *
     * @return El tipo del ítem.
     */
    public TipoItem getTipo() { return tipo; }

    /**
     * Obtiene la cantidad disponible del ítem.
     *
     * @return La cantidad actual disponible del ítem.
     */
    public int getCantidad() { return cantidad; }

    /**
     * Obtiene la descripción del ítem.
     *
     * @return La descripción del ítem.
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Obtiene la potencia del ítem.
     *
     * @return La potencia del ítem.
     */
    public int getPotencia() { return potencia; }

    /**
     * Verifica si el ítem está disponible para su uso.
     * Las subclases pueden sobrescribir este método para implementar
     * lógica adicional de disponibilidad.
     *
     * @return true si el ítem está disponible, false en caso contrario.
     */
    public boolean disponible() {
        return cantidad > 0 && verificarDisponibilidadAdicional();
    }

    /**
     * Método que las subclases pueden sobrescribir para agregar
     * condiciones adicionales de disponibilidad.T
     *
     * @return true por defecto, las subclases pueden cambiar este comportamiento
     */
    protected boolean verificarDisponibilidadAdicional() {
        return true;
    }

    /**
     * Aplica el efecto del ítem. Las subclases deben implementar este método
     * para definir el comportamiento específico del ítem.
     *
     * @return true si el efecto se aplicó correctamente, false en caso contrario
     */
    public abstract boolean aplicarEfecto();

    /**
     * Reduce la cantidad disponible del ítem en 1, si hay ítems disponibles.
     * Este método ahora es final para asegurar el comportamiento base,
     * pero permite personalización a través de hooks.
     */
    public final void usar() {
        if (disponible() && antesDeUsar()) {
            cantidad--;
            despuesDeUsar();
        }
    }

    /**
     * Hook que se ejecuta antes de usar el ítem.
     * Las subclases pueden sobrescribir este método para agregar comportamiento.
     *
     * @return true si el ítem puede ser usado, false en caso contrario
     */
    protected boolean antesDeUsar() {
        return true;
    }

    /**
     * Hook que se ejecuta después de usar el ítem.
     * Las subclases pueden sobrescribir este método para agregar comportamiento.
     */
    protected void despuesDeUsar() {
        // Por defecto no hace nada
    }

    /**
     * Devuelve una representación en forma de cadena del ítem.
     * Este método puede ser sobrescrito por las subclases para personalizar
     * la representación del ítem.
     *
     * @return Una cadena con el formato "nombre x cantidad".
     */
    @Override
    public String toString() {
        return nombre + " x" + cantidad;
    }
}
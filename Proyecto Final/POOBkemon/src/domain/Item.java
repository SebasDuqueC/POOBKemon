package domain;

import java.io.Serializable;

/**
 * Representa un objeto o ítem que puede ser utilizado en el juego.
 * Esta clase implementa la interfaz Serializable para permitir la persistencia de los ítems.
 * Cada ítem tiene un nombre, tipo, cantidad disponible, descripción y potencia.
 */
public class Item implements Serializable {
    private String nombre;
    private TipoItem tipo;
    private int cantidad;
    private String descripcion;
    private int potencia;

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
     * Un ítem se considera disponible si su cantidad es mayor a 0.
     *
     * @return true si el ítem está disponible, false en caso contrario.
     */
    public boolean disponible() {
        return cantidad > 0;
    }

    /**
     * Reduce la cantidad disponible del ítem en 1, si hay ítems disponibles.
     * Este método simula el uso de un ítem en el juego.
     */
    public void usar() {
        if (cantidad > 0) {
            cantidad--;
        }
    }

    /**
     * Devuelve una representación en forma de cadena del ítem.
     * El formato incluye el nombre del ítem seguido de la cantidad disponible.
     *
     * @return Una cadena con el formato "nombre x cantidad".
     */
    @Override
    public String toString() {
        return nombre + " x" + cantidad;
    }
}
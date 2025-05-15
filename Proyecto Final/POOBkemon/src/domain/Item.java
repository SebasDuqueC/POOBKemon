package domain;

import java.io.Serializable;

public class Item implements Serializable {
    private String nombre;
    private TipoItem tipo;
    private int cantidad;
    private String descripcion;
    private int potencia;
    
    public Item(String nombre, TipoItem tipo, int cantidad) {
        this(nombre, tipo, cantidad, "", 0);
    }
    
    public Item(String nombre, TipoItem tipo, int cantidad, String descripcion, int potencia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.potencia = potencia;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public TipoItem getTipo() { return tipo; }
    public int getCantidad() { return cantidad; }
    public String getDescripcion() { return descripcion; }
    public int getPotencia() { return potencia; }
    
    // Método para comprobar si hay unidades disponibles
    public boolean disponible() {
        return cantidad > 0;
    }
    
    // Método para usar el item y reducir cantidad
    public void usar() {
        if (cantidad > 0) {
            cantidad--;
        }
    }
    
    @Override
    public String toString() {
        return nombre + " x" + cantidad;
    }
}
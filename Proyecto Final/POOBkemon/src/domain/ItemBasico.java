package domain;

/**
 * Implementación básica de un Item que mantiene la funcionalidad original.
 */
public class ItemBasico extends Item {
    
    public ItemBasico(String nombre, TipoItem tipo, int cantidad) {
        super(nombre, tipo, cantidad);
    }

    public ItemBasico(String nombre, TipoItem tipo, int cantidad, String descripcion, int potencia) {
        super(nombre, tipo, cantidad, descripcion, potencia);
    }

    @Override
    public boolean aplicarEfecto() {
        // Implementación básica que simula el uso exitoso del ítem
        return true;
    }
} 
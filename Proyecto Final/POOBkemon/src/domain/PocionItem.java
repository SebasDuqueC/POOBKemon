package domain;

/**
 * Implementación específica para ítems de tipo poción que recuperan PS.
 */
public class PocionItem extends Item {
    
    public PocionItem(String nombre, TipoItem tipo, int cantidad, int potenciaRecuperacion) {
        super(nombre, tipo, cantidad, "Recupera " + potenciaRecuperacion + " PS", potenciaRecuperacion);
    }

    @Override
    public boolean aplicarEfecto() {
        if (!disponible()) {
            return false;
        }
        // La lógica de curación se implementará cuando se use el ítem
        usar();
        return true;
    }

    @Override
    protected void inicializar() {
        // Verificar que el tipo sea algún tipo de poción
        if (tipo != TipoItem.POCION && tipo != TipoItem.SUPERPOCION && tipo != TipoItem.HYPERPOCION) {
            throw new IllegalArgumentException("Tipo de ítem inválido para PocionItem: " + tipo);
        }
    }
} 
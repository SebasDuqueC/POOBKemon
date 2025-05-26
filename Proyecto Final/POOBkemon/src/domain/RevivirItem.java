package domain;

/**
 * Implementación específica para ítems de tipo revivir que recuperan Pokémon debilitados.
 */
public class RevivirItem extends Item {
    
    public RevivirItem(String nombre, int cantidad) {
        super(nombre, TipoItem.REVIVIR, cantidad, "Revive un Pokémon debilitado", 1);
    }

    @Override
    public boolean aplicarEfecto() {
        if (!disponible()) {
            return false;
        }
        // La lógica de revivir se implementará cuando se use el ítem
        usar();
        return true;
    }

    @Override
    protected void inicializar() {
        // Verificar que el tipo sea REVIVIR
        if (tipo != TipoItem.REVIVIR) {
            throw new IllegalArgumentException("Tipo de ítem inválido para RevivirItem: " + tipo);
        }
    }
} 
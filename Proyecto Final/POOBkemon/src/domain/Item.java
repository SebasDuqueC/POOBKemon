package domain;
import exceptions.PoobkemonException;

public class Item {
    private String nombre;
    private TipoItem tipo;
    private int cantidad;

    public Item(String nombre, TipoItem tipo, int cantidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public boolean disponible() {
        return cantidad > 0;
    }

    public void usar(Pokemon objetivo) throws PoobkemonException {
        if (!disponible()) {
            throw new PoobkemonException("No quedan unidades del ítem.");
        }

        switch (tipo) {
            case POTION:
                if (!objetivo.estaDebilitado()) {
                    objetivo.recuperarPS(20);
                    cantidad--;
                } else {
                    throw new PoobkemonException("No puedes usar una poción en un pokémon debilitado.");
                }
                break;

            case SUPERPOTION:
                if (!objetivo.estaDebilitado()) {
                    objetivo.recuperarPS(50);
                    cantidad--;
                } else {
                    throw new PoobkemonException("No puedes usar una superpoción en un pokémon debilitado.");
                }
                break;

            case HYPERPOTION:
                if (!objetivo.estaDebilitado()) {
                    objetivo.recuperarPS(200);
                    cantidad--;
                } else {
                    throw new PoobkemonException("No puedes usar una hiperpoción en un pokémon debilitado.");
                }
                break;

            case REVIVE:
                if (objetivo.estaDebilitado()) {
                    objetivo.revivir();
                    cantidad--;
                } else {
                    throw new PoobkemonException("Solo puedes usar Revive en un pokémon debilitado.");
                }
                break;
        }
    }

    public String getNombre() { return nombre; }
    public TipoItem getTipo() { return tipo; }
    public int getCantidad() { return cantidad; }
}

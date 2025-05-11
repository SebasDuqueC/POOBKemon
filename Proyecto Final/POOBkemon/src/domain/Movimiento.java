package domain;
import exceptions.PoobkemonException;
import java.io.Serializable;

public class Movimiento  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private Tipo tipo;
    private int potencia;
    private int precision;
    private int ppMaximo;
    private int ppActual;
    private CategoriaMovimiento categoria;

    public Movimiento(String nombre, Tipo tipo, int potencia, int precision, int ppMaximo, CategoriaMovimiento categoria) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.precision = precision;
        this.ppMaximo = ppMaximo;
        this.ppActual = ppMaximo;
        this.categoria = categoria;
    }

    public boolean puedeUsarse() {
        return ppActual > 0;
    }

    public void usar() throws PoobkemonException {
        if (!puedeUsarse()) {
            throw new PoobkemonException("El movimiento no tiene PP disponibles.");
        }
        ppActual--;
    }

    public void restaurarPP() {
        ppActual = ppMaximo;
    }

    // Getters
    public String getNombre() { return nombre; }
    public Tipo getTipo() { return tipo; }
    public int getPotencia() { return potencia; }
    public int getPrecision() { return precision; }
    public int getPpActual() { return ppActual; }
    public int getPpMaximo() { return ppMaximo; }
    public CategoriaMovimiento getCategoria() { return categoria; }

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

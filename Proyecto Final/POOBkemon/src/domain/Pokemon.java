package domain;
import exceptions.PoobkemonException;

import java.util.List;
import java.io.Serializable;

public class Pokemon  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object tipo = null;
    private String nombre;
    private Tipo tipoPrimario;
    private Tipo tipoSecundario; // Nuevo atributo
    private int psMaximo;
    private int psActual;
    private int ataque;
    private int defensa;
    private int velocidad;
    private int ataqueEspecial;
    private int defensaEspecial;
    private List<Movimiento> movimientos;

    public Pokemon(String nombre, Tipo tipoPrimario, Tipo tipoSecundario, int ps, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad, List<Movimiento> movimientos) {
        this.nombre = nombre;
        this.tipoPrimario = tipoPrimario; // Inicialización del nuevo atributo
        this.tipoSecundario = tipoSecundario; // Inicialización del nuevo atributo
        this.tipo = tipo;
        this.psMaximo = ps;
        this.psActual = ps;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.movimientos = movimientos;
    }

    public boolean estaDebilitado() {
        return psActual <= 0;
    }

    public void recibirDanio(int cantidad) {
        psActual -= cantidad;
        if (psActual < 0) {
            psActual = 0;
        }
    }

    public Movimiento getMovimiento(int index) throws PoobkemonException {
        if (index < 0 || index >= movimientos.size()) {
            throw new PoobkemonException("Índice de movimiento inválido.");
        }
        return movimientos.get(index);
    }

    public String getNombre() { return nombre; }
    public Tipo getTipoPrimario() { return tipoPrimario; } // Nuevo método
    public Tipo getTipoSecundario() { return tipoSecundario; } // Nuevo método
    public Tipo getTipo() { return (Tipo) tipo; }
    public int getPsActual() { return psActual; }
    public int getPsMaximo() { return psMaximo; }
    public int getVelocidad() { return velocidad; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAtaqueEspecial() { return ataqueEspecial; }
    public int getDefensaEspecial() { return defensaEspecial; }
    public List<Movimiento> getMovimientos() { return movimientos; }

    public void recuperarPS(int cantidad) {
        psActual += cantidad;
        if (psActual > psMaximo) {
            psActual = psMaximo;
        }
    }

    public void revivir() {
        psActual = psMaximo / 2;
    }

    public void agregarMovimiento(Movimiento m) {
        if (movimientos.size() < 4) {
            movimientos.add(m);
        }
    }
}
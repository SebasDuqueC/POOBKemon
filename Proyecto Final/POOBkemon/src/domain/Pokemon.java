package domain;

import exceptions.PoobkemonException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    public String getNombre() { return nombre; }
    public int getVida() { return psMaximo; }
    public int getPsActual() { return psActual; }
    public int getPsMaximo() { return psMaximo; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAtaqueEspecial() { return ataqueEspecial; }
    public int getDefensaEspecial() { return defensaEspecial; }
    public int getVelocidad() { return velocidad; }
    public List<Movimiento> getMovimientos() { return movimientos; }
    
    // Métodos necesarios para el funcionamiento del juego
    public boolean estaDebilitado() {
        return psActual <= 0;
    }
    
    public void recibirDanio(int danio) {
        psActual = Math.max(0, psActual - danio);
    }
    
    public void recuperarPS(int cantidad) {
        psActual = Math.min(psMaximo, psActual + cantidad);
    }
    
    public void revivir() {
        if (estaDebilitado()) {
            psActual = psMaximo / 2;
        }
    }
    
    public Movimiento getMovimiento(int index) throws PoobkemonException {
        if (index < 0 || index >= movimientos.size()) {
            throw new PoobkemonException("Índice de movimiento inválido");
        }
        return movimientos.get(index);
    }
    
    // Método abstracto para movimiento especial
    public abstract String mover();

    
}
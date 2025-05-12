package presentation;

import domain.*;
import exceptions.PoobkemonException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PokemonMoveSelector extends JDialog {
    
    private Pokemon pokemon;
    private List<JCheckBox> moveCheckboxes;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private List<Movimiento> movimientosSeleccionados;
    private boolean confirmed = false;
    
    public PokemonMoveSelector(JFrame parent, String pokemonName) {
        super(parent, "Selección de Movimientos para " + pokemonName, true);
        
        // Buscar el pokémon por nombre
        this.pokemon = PokemonLoader.buscarPokemonPorNombre(pokemonName);
        this.moveCheckboxes = new ArrayList<>();
        this.movimientosSeleccionados = new ArrayList<>();
        
        initComponents();
        
        // Configurar diálogo
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel de título con información del Pokémon
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitle = new JLabel("Selecciona los movimientos para " + pokemon.getNombre());
        lblTitle.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        panelInfo.add(lblTitle, BorderLayout.NORTH);
        
        // Datos del Pokémon
        JPanel panelStats = new JPanel(new GridLayout(3, 2, 10, 5));
        panelStats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelStats.add(new JLabel("Vida:"));
        panelStats.add(new JLabel(String.valueOf(pokemon.getVida())));
        panelStats.add(new JLabel("Ataque:"));
        panelStats.add(new JLabel(String.valueOf(pokemon.getAtaque())));
        panelStats.add(new JLabel("Defensa:"));
        panelStats.add(new JLabel(String.valueOf(pokemon.getDefensa())));
        
        panelInfo.add(panelStats, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.NORTH);
        
        // Panel de movimientos
        JPanel panelMovimientos = new JPanel(new GridLayout(0, 1, 0, 10));
        panelMovimientos.setBorder(BorderFactory.createTitledBorder("Movimientos Disponibles"));
        
        // Obtener movimientos disponibles para este tipo de Pokémon
        Tipo tipoPokemon = Tipo.valueOf(pokemon.getClass().getSimpleName().toUpperCase());
        Movimiento[] movimientosDisponibles = MovimientoFactory.obtenerCuatroMovimientos(tipoPokemon);
        
        // Crear checkboxes para cada movimiento
        for (Movimiento m : movimientosDisponibles) {
            String descripcion = m.getNombre() + " - Potencia: " + m.getPotencia() + 
                                " - Precisión: " + m.getPrecision() + " - PP: " + m.getPpMaximo();
            JCheckBox checkBox = new JCheckBox(descripcion);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
            panelMovimientos.add(checkBox);
            moveCheckboxes.add(checkBox);
        }
        
        JScrollPane scrollPane = new JScrollPane(panelMovimientos);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancelar = new JButton("Cancelar");
        btnConfirmar = new JButton("Confirmar");
        
        btnCancelar.addActionListener(e -> dispose());
        
        btnConfirmar.addActionListener(e -> {
            // Verificar que se hayan seleccionado exactamente 4 movimientos
            int count = 0;
            for (JCheckBox box : moveCheckboxes) {
                if (box.isSelected()) count++;
            }
            
            if (count != 4) {
                JOptionPane.showMessageDialog(this, 
                    "Debes seleccionar exactamente 4 movimientos", 
                    "Selección inválida", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Guardar movimientos seleccionados
            movimientosSeleccionados.clear();
            for (int i = 0; i < moveCheckboxes.size(); i++) {
                if (moveCheckboxes.get(i).isSelected()) {
                    movimientosSeleccionados.add(movimientosDisponibles[i]);
                }
            }
            
            confirmed = true;
            dispose();
        });
        
        panelBotones.add(btnCancelar);
        panelBotones.add(btnConfirmar);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public List<Movimiento> getMovimientosSeleccionados() {
        return movimientosSeleccionados;
    }
}
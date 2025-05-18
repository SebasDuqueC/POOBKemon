package presentation;

import domain.*;
import exceptions.PoobkemonException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PokemonMoveSelector extends JDialog {
    
    private Pokemon pokemon;
    private List<JCheckBox> moveCheckboxes;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private List<Movimiento> movimientosSeleccionados;
    private boolean confirmed = false;
    private JLabel lblImagenPokemon;


    public PokemonMoveSelector(JFrame parent, String pokemonName) {
        super(parent, "Selección de Movimientos para " + pokemonName, true);
        
        // Buscar el pokémon por nombre
        this.pokemon = PokemonLoader.buscarPokemonPorNombre(pokemonName);
        this.moveCheckboxes = new ArrayList<>();
        this.movimientosSeleccionados = new ArrayList<>();
        
        initComponents();
        
        // Configurar diálogo
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        // Panel de título con información del Pokémon
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInfo.setBackground(new Color(240, 240, 240));

        // Panel izquierdo con imagen y nombre
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(new Color(240, 240, 240));

        lblImagenPokemon = new JLabel();
        lblImagenPokemon.setPreferredSize(new Dimension(200, 200));
        lblImagenPokemon.setHorizontalAlignment(SwingConstants.CENTER);
        mostrarGifPokemon(pokemon.getNombre());

        JLabel lblNombre = new JLabel(pokemon.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("Press Start 2P", Font.BOLD, 20));

        panelIzquierdo.add(lblImagenPokemon, BorderLayout.CENTER);
        panelIzquierdo.add(lblNombre, BorderLayout.SOUTH);

        // Panel derecho con estadísticas
        JPanel panelDerecho = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDerecho.setBackground(new Color(240, 240, 240));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Estilo para las etiquetas de estadísticas
        Font statsFont = new Font("Press Start 2P", Font.BOLD, 14);

        JLabel lblVida = new JLabel("Vida:");
        JLabel lblVidaValor = new JLabel(String.valueOf(pokemon.getVida()));
        JLabel lblAtaque = new JLabel("Ataque:");
        JLabel lblAtaqueValor = new JLabel(String.valueOf(pokemon.getAtaque()));
        JLabel lblDefensa = new JLabel("Defensa:");
        JLabel lblDefensaValor = new JLabel(String.valueOf(pokemon.getDefensa()));
        JLabel lblVelocidad = new JLabel("Velocidad:");
        JLabel lblVelocidadValor = new JLabel(String.valueOf(pokemon.getVelocidad()));

        lblVida.setFont(statsFont);
        lblVidaValor.setFont(statsFont);
        lblAtaque.setFont(statsFont);
        lblAtaqueValor.setFont(statsFont);
        lblDefensa.setFont(statsFont);
        lblDefensaValor.setFont(statsFont);
        lblVelocidad.setFont(statsFont);
        lblVelocidadValor.setFont(statsFont);

        panelDerecho.add(lblVida);
        panelDerecho.add(lblVidaValor);
        panelDerecho.add(lblAtaque);
        panelDerecho.add(lblAtaqueValor);
        panelDerecho.add(lblDefensa);
        panelDerecho.add(lblDefensaValor);
        panelDerecho.add(lblVelocidad);
        panelDerecho.add(lblVelocidadValor);

        panelInfo.add(panelIzquierdo, BorderLayout.WEST);
        panelInfo.add(panelDerecho, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.NORTH);

        JPanel panelMovimientos = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                // Crear un degradado de blanco a azul claro
                GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, 0, height, new Color(135, 206, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panelMovimientos.setLayout(new BoxLayout(panelMovimientos, BoxLayout.Y_AXIS));
        panelMovimientos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100)),
                "Selecciona 4 movimientos",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Press Start 2P", Font.BOLD, 14)
        ));
        panelMovimientos.setOpaque(false); // Hacer el panel completamente transparente

        // Obtener todos los movimientos disponibles
        List<Movimiento> todosLosMovimientos = new ArrayList<>();

        // Añadir movimientos del catálogo
        todosLosMovimientos.add(MovimientoCatalogo.lanzallamas());
        todosLosMovimientos.add(MovimientoCatalogo.garraDragon());
        todosLosMovimientos.add(MovimientoCatalogo.hidrobomba());
        todosLosMovimientos.add(MovimientoCatalogo.cabezazo());
        todosLosMovimientos.add(MovimientoCatalogo.trueno());
        todosLosMovimientos.add(MovimientoCatalogo.terremoto());
        todosLosMovimientos.add(MovimientoCatalogo.golpeCuerpo());
        todosLosMovimientos.add(MovimientoCatalogo.psicoataque());

        // Añadir movimientos genéricos de cada tipo
        for (Tipo tipo : Tipo.values()) {
            Movimiento[] movimientosTipo = MovimientoFactory.obtenerCuatroMovimientos(tipo);
            Collections.addAll(todosLosMovimientos, movimientosTipo);
        }

        // Crear checkboxes para cada movimiento con diseño mejorado
        for (Movimiento m : todosLosMovimientos) {
            JPanel movimientoPanel = new JPanel(new BorderLayout());
            movimientoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JCheckBox checkBox = new JCheckBox();
            checkBox.setFont(new Font("Press Start 2P", Font.BOLD, 14));

            JLabel lblMovimiento = new JLabel(String.format(
                "<html><b>%s</b> - Tipo: %s<br>Potencia: %d - Precisión: %d - PP: %d<br>Categoría: %s</html>",
                m.getNombre(),
                m.getTipo(),
                m.getPotencia(),
                m.getPrecision(),
                m.getPpMaximo(),
                m.getCategoria()
            ));

            movimientoPanel.add(checkBox, BorderLayout.WEST);
            movimientoPanel.add(lblMovimiento, BorderLayout.CENTER);

            // Añadir efecto hover
            movimientoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    movimientoPanel.setBackground(new Color(230, 230, 230));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    movimientoPanel.setBackground(null);
                }
            });

            // Hacer que todo el panel sea clickeable
            movimientoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    checkBox.setSelected(!checkBox.isSelected());
                }
            });

            panelMovimientos.add(movimientoPanel);
            moveCheckboxes.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(panelMovimientos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones con diseño mejorado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(240, 240, 240));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnCancelar = new JButton("Cancelar");
        btnConfirmar = new JButton("Confirmar");

        // Estilo para los botones
        Font buttonFont = new Font("Press Start 2P", Font.PLAIN, 12);
        btnCancelar.setFont(buttonFont);
        btnConfirmar.setFont(buttonFont);

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
                    movimientosSeleccionados.add(todosLosMovimientos.get(i));
                }
            }

            confirmed = true;
            dispose();
        });

        panelBotones.add(btnCancelar);
        panelBotones.add(btnConfirmar);
        add(panelBotones, BorderLayout.SOUTH);


    }

    private void mostrarGifPokemon(String nombrePokemon) {
        try {
            String nombreArchivo = nombrePokemon.toLowerCase() + ".gif";
            ImageIcon gifPokemon = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("main/resources/images/" + nombreArchivo)));
            lblImagenPokemon.setIcon(gifPokemon);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el GIF para " + nombrePokemon + ": " + e.getMessage());
            lblImagenPokemon.setText("[ " + nombrePokemon + " ]");
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public List<Movimiento> getMovimientosSeleccionados() {
        return movimientosSeleccionados;
    }


}
package presentation;

import exceptions.PoobkemonException;
import domain.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jdk.xml.internal.SecuritySupport.getClassLoader;

public class PoobkemonGUI extends JFrame {
    
    private Batalla batalla;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    
    // Componentes de la UI
    private JPanel panelPrincipal;
    private JPanel panelPokemonRival;
    private JPanel panelPokemonJugador;
    private JPanel panelControles;
    private JPanel panelMovimientos;
    private JPanel panelPokemones;
    private JPanel panelItems;
    private JTextArea areaLog;
    
    private JLabel lblNombreRival;
    private JLabel lblPSRival;
    private JLabel lblNombreJugador;
    private JLabel lblPSJugador;
    private JLabel lblImagenPokemonRival; // Nueva etiqueta para imagen del rival
    private JLabel lblImagenPokemonJugador; // Nueva etiqueta para imagen del jugador
    
    private JButton[] btnMovimientos = new JButton[4];
    private JButton[] btnPokemones = new JButton[6];
    private JButton[] btnItems = new JButton[4];

    public PoobkemonGUI(Entrenador entrenador1, Entrenador entrenador2, String modo) {
        setTitle("POOBkemon - Combate");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setUndecorated(true); // Sin bordes
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.batalla = new Batalla(entrenador1, entrenador2);
        initGUI();
        actualizarInterfaz();

        // Listener para redimensionar
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentes(getContentPane(), getSize());
            }
        });
    }

    private void ajustarComponentes(Container container, Dimension ventana) {
        int baseWidth = 1000; // Ancho base de referencia
        int baseHeight = 700; // Altura base de referencia

        double scaleX = ventana.getWidth() / baseWidth;
        double scaleY = ventana.getHeight() / baseHeight;
        double scale = Math.min(scaleX, scaleY); // Escala uniforme

        for (Component component : container.getComponents()) {
            if (component instanceof JButton || component instanceof JLabel || component instanceof JTextArea) {
                component.setFont(component.getFont().deriveFont((float) (16 * scale))); // Ajustar fuente
            } else if (component instanceof JPanel) {
                ajustarComponentes((Container) component, ventana); // Recursión para paneles
            }
        }

        container.revalidate();
        container.repaint();
    }

    private void initGUI() {
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel superior: Pokémon del rival (ahora con imagen)
        panelPokemonRival = new JPanel(new BorderLayout());
        panelPokemonRival.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelInfoRival = new JPanel();
        panelInfoRival.setLayout(new BoxLayout(panelInfoRival, BoxLayout.Y_AXIS));
        
        lblNombreRival = new JLabel("Pokémon Rival");
        lblNombreRival.setFont(new Font("Arial", Font.BOLD, 16));
        lblPSRival = new JLabel("PS: 100/100");
        
        panelInfoRival.add(lblNombreRival);
        panelInfoRival.add(lblPSRival);
        
        // Imagen del Pokémon rival
        lblImagenPokemonRival = new JLabel();
        lblImagenPokemonRival.setPreferredSize(new Dimension(200, 200));
        lblImagenPokemonRival.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelPokemonRival.add(panelInfoRival, BorderLayout.WEST);
        panelPokemonRival.add(lblImagenPokemonRival, BorderLayout.CENTER);

        // Panel central: área de mensajes
        areaLog = new JTextArea(5, 40);
        areaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(areaLog);
        areaLog.append("¡Inicia la batalla!\n");

        // Panel inferior: Pokémon del jugador (ahora con imagen)
        panelPokemonJugador = new JPanel(new BorderLayout());
        panelPokemonJugador.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelInfoJugador = new JPanel();
        panelInfoJugador.setLayout(new BoxLayout(panelInfoJugador, BoxLayout.Y_AXIS));
        
        lblNombreJugador = new JLabel("Tu Pokémon");
        lblNombreJugador.setFont(new Font("Arial", Font.BOLD, 16));
        lblPSJugador = new JLabel("PS: 100/100");
        
        panelInfoJugador.add(lblNombreJugador);
        panelInfoJugador.add(lblPSJugador);
        
        // Imagen del Pokémon del jugador
        lblImagenPokemonJugador = new JLabel();
        lblImagenPokemonJugador.setPreferredSize(new Dimension(200, 200));
        lblImagenPokemonJugador.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelPokemonJugador.add(panelInfoJugador, BorderLayout.WEST);
        panelPokemonJugador.add(lblImagenPokemonJugador, BorderLayout.CENTER);

        // Controles: movimientos, cambio de pokémon, items
        panelControles = new JPanel(new CardLayout());

        // Panel de movimientos
        panelMovimientos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelMovimientos.setBorder(BorderFactory.createTitledBorder("Movimientos"));

        for (int i = 0; i < btnMovimientos.length; i++) {
            final int index = i;
            btnMovimientos[i] = new JButton("Movimiento " + (i + 1));
            btnMovimientos[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    realizarMovimiento(index);
                }
            });
            panelMovimientos.add(btnMovimientos[i]);
        }

        // Panel de cambio de Pokémon
        panelPokemones = new JPanel(new GridLayout(3, 2, 10, 10));
        panelPokemones.setBorder(BorderFactory.createTitledBorder("Cambiar Pokémon"));

        for (int i = 0; i < btnPokemones.length; i++) {
            final int index = i;
            btnPokemones[i] = new JButton("Pokémon " + (i + 1));
            btnPokemones[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cambiarPokemon(index);
                }
            });
            panelPokemones.add(btnPokemones[i]); // Asegurarse de agregar al panel
        }

        // Panel de items
        panelItems = new JPanel(new GridLayout(2, 2, 10, 10));
        panelItems.setBorder(BorderFactory.createTitledBorder("Usar Item"));

        for (int i = 0; i < btnItems.length; i++) {
            final int index = i;
            btnItems[i] = new JButton("Item " + (i + 1));
            btnItems[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    usarItem(index);
                }
            });
            panelItems.add(btnItems[i]);
        }

        // Botones de navegación entre paneles de control
        JPanel panelNavegacion = new JPanel(new GridLayout(1, 3));

        JButton btnVerMovimientos = new JButton("Movimientos");
        btnVerMovimientos.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelControles.getLayout();
            cl.show(panelControles, "movimientos");
        });

        JButton btnVerPokemones = new JButton("Pokémon");
        btnVerPokemones.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelControles.getLayout();
            cl.show(panelControles, "pokemones");
        });

        JButton btnVerItems = new JButton("Items");
        btnVerItems.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelControles.getLayout();
            cl.show(panelControles, "items");
        });

        panelNavegacion.add(btnVerMovimientos);
        panelNavegacion.add(btnVerPokemones);
        panelNavegacion.add(btnVerItems);

        panelControles.add(panelMovimientos, "movimientos");
        panelControles.add(panelPokemones, "pokemones");
        panelControles.add(panelItems, "items");

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelPokemonJugador, BorderLayout.NORTH);
        panelInferior.add(panelControles, BorderLayout.CENTER);
        panelInferior.add(panelNavegacion, BorderLayout.SOUTH);

        // Ensamblar todo
        panelPrincipal.add(panelPokemonRival, BorderLayout.NORTH);
        panelPrincipal.add(scrollLog, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Mostrar primero la pestaña de movimientos
        CardLayout cl = (CardLayout) panelControles.getLayout();
        cl.show(panelControles, "movimientos");
    }

    // Método para cargar imágenes de los Pokémon
    private void cargarImagenPokemon(JLabel label, String nombrePokemon) {
        try {
            String nombreArchivo = nombrePokemon.toLowerCase() + ".png";
            ImageIcon imagenPokemon = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("main/resources/images/" + nombreArchivo)));
            
            // Redimensionar si es necesario
            Image img = imagenPokemon.getImage();
            Image imgResized = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imgResized));
        } catch (Exception e) {
            try {
                // Intenta cargar una imagen predeterminada si no encuentra la específica
                String nombreArchivo = "pokemon_default.png";
                ImageIcon defaultImage = new ImageIcon(Objects.requireNonNull(
                        getClass().getClassLoader().getResource("out/production/POOBkemon/main/resource/simages" + nombreArchivo)));
                
                Image img = defaultImage.getImage();
                Image imgResized = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(imgResized));
                
                // También mostrar el nombre como texto
                label.setText(nombrePokemon);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
            } catch (Exception ex) {
                System.err.println("No se pudo cargar la imagen predeterminada: " + ex.getMessage());
                label.setText("[ " + nombrePokemon + " ]");
            }
        }
    }

    private void realizarMovimiento(int index) {
        try {
            if (batalla.getTurnoActual() != entrenador1) {
                areaLog.append("No es tu turno\n");
                return;
            }
            
            String resultado = batalla.ejecutarMovimiento(index);
            areaLog.append(resultado + "\n");
    
            if (batalla.hayGanador()) {
                Entrenador ganador = batalla.getGanador();
                areaLog.append("\n¡" + ganador.getNombre() + " ha ganado el combate!\n");
                deshabilitarControles();
                return;
            }
            
            // El método ejecutarMovimiento ya cambia el turno internamente, no lo repitamos aquí
            // batalla.cambiarTurno();
            
            // Si es el turno de la IA, ejecutar automáticamente
            if (!batalla.getTurnoActual().esHumano()) {
                ejecutarTurnoIA();
            }
            
            actualizarInterfaz();
        } catch (PoobkemonException e) {
            areaLog.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void ejecutarTurnoIA() {
        try {
            // Verificar que es realmente el turno de la IA
            if (batalla.getTurnoActual() != entrenador2) {
                return;
            }
            
            // La IA elige un movimiento aleatorio
            int movimientoIA = (int) (Math.random() * entrenador2.getActivo().getMovimientos().size());
            String resultadoIA = batalla.ejecutarMovimiento(movimientoIA);
            areaLog.append(resultadoIA + "\n");
            
            if (batalla.hayGanador()) {
                Entrenador ganador = batalla.getGanador();
                areaLog.append("\n¡" + ganador.getNombre() + " ha ganado el combate!\n");
                deshabilitarControles();
                return;
            }
            
            // Asegurar que el turno regrese al jugador después de la acción de la IA
            if (batalla.getTurnoActual() != entrenador1) {
                areaLog.append("Corrigiendo turno, ahora es tu turno\n");
                batalla.cambiarTurno();
            }
            
            actualizarInterfaz();
        } catch (PoobkemonException e) {
            areaLog.append("Error del oponente: " + e.getMessage() + "\n");
            // Asegurar que después de un error siempre vuelva al jugador
            if (batalla.getTurnoActual() != entrenador1) {
                batalla.cambiarTurno();
            }
            actualizarInterfaz();
        }
    }
    
        private void cambiarPokemon(int index) {
        try {
            if (batalla.getTurnoActual() != entrenador1) {
                areaLog.append("No es tu turno\n");
                return;
            }
            
            entrenador1.cambiarPokemon(index);
            areaLog.append(entrenador1.getNombre() + " cambió a " + entrenador1.getActivo().getNombre() + "\n");
            
            batalla.cambiarTurno(); // Cambiar turno después de la acción
            
            // Si ahora es el turno de la IA, ejecutar automáticamente
            if (!batalla.getTurnoActual().esHumano()) {
                ejecutarTurnoIA();
            }
            
            actualizarInterfaz();
        } catch (PoobkemonException e) {
            areaLog.append("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void usarItem(int index) {
        try {
            if (batalla.getTurnoActual() != entrenador1) {
                areaLog.append("No es tu turno\n");
                return;
            }
            
            if (index < entrenador1.getItems().size()) {
                entrenador1.usarItem(index, entrenador1.getActivo());
                Item item = entrenador1.getItems().get(index);
                areaLog.append(entrenador1.getNombre() + " usó " + item.getNombre() + 
                              " en " + entrenador1.getActivo().getNombre() + "\n");
                
                batalla.cambiarTurno(); // Cambiar turno después de la acción
                
                // Si ahora es el turno de la IA, ejecutar automáticamente 
                if (!batalla.getTurnoActual().esHumano()) {
                    ejecutarTurnoIA();
                }
                
                actualizarInterfaz();
            }
        } catch (PoobkemonException e) {
            areaLog.append("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void actualizarInterfaz() {
        // Actualizar información del Pokémon del jugador
        Pokemon pokemonJugador = entrenador1.getActivo();
        lblNombreJugador.setText(pokemonJugador.getNombre());
        lblPSJugador.setText("PS: " + pokemonJugador.getPsActual() + "/" + pokemonJugador.getPsMaximo());
        cargarImagenPokemon(lblImagenPokemonJugador, pokemonJugador.getNombre());
        
        // Actualizar información del Pokémon del rival
        Pokemon pokemonRival = entrenador2.getActivo();
        lblNombreRival.setText(pokemonRival.getNombre());
        lblPSRival.setText("PS: " + pokemonRival.getPsActual() + "/" + pokemonRival.getPsMaximo());
        cargarImagenPokemon(lblImagenPokemonRival, pokemonRival.getNombre());
        
        // Indicador visual de turno más prominente
        boolean esTurnoJugador = batalla.getTurnoActual() == entrenador1;
        
        if (esTurnoJugador) {
            lblImagenPokemonJugador.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            lblImagenPokemonRival.setBorder(null);
            // Solo agrega el mensaje si cambió de turno
            if (!areaLog.getText().endsWith("► Es tu turno\n")) {
                areaLog.append("► Es tu turno\n");
            }
        } else {
            lblImagenPokemonRival.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            lblImagenPokemonJugador.setBorder(null);
            // Solo agrega el mensaje si cambió de turno
            if (!areaLog.getText().endsWith("► Turno del rival\n")) {
                areaLog.append("► Turno del rival\n");
            }
        }
        
        // Actualizar botones de movimientos - SIEMPRE HABILITADOS SI PUEDEN USARSE
        List<Movimiento> movimientosJugador = pokemonJugador.getMovimientos();
        for (int i = 0; i < btnMovimientos.length; i++) {
            if (i < movimientosJugador.size()) {
                Movimiento m = movimientosJugador.get(i);
                btnMovimientos[i].setText(m.getNombre() + " (" + m.getPpActual() + "/" + m.getPpMaximo() + ")");
                btnMovimientos[i].setEnabled(m.puedeUsarse()); // Quitar la condición de turno
            } else {
                btnMovimientos[i].setText("-");
                btnMovimientos[i].setEnabled(false);
            }
        }
        
        // Actualizar botones de Pokémon - SIEMPRE HABILITADOS SI NO ESTÁN DEBILITADOS
        List<Pokemon> equipoJugador = entrenador1.getPokemones();
        for (int i = 0; i < btnPokemones.length; i++) {
            if (i < equipoJugador.size()) {
                Pokemon p = equipoJugador.get(i);
                btnPokemones[i].setText(p.getNombre() + " (" + p.getPsActual() + "/" + p.getPsMaximo() + ")");
                btnPokemones[i].setEnabled(i != entrenador1.getActivoIndex() && !p.estaDebilitado()); // Quitar la condición de turno
            } else {
                btnPokemones[i].setText("-");
                btnPokemones[i].setEnabled(false);
            }
        }
        
        // Actualizar botones de items - SIEMPRE HABILITADOS SI ESTÁN DISPONIBLES
        List<Item> itemsJugador = entrenador1.getItems();
        for (int i = 0; i < btnItems.length; i++) {
            if (i < itemsJugador.size()) {
                Item item = itemsJugador.get(i);
                btnItems[i].setText(item.getNombre() + " x" + item.getCantidad());
                btnItems[i].setEnabled(item.disponible()); // Quitar la condición de turno
            } else {
                btnItems[i].setText("-");
                btnItems[i].setEnabled(false);
            }
        }
        
        // Hacer scroll hacia abajo en el área de logs
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }
    
    private void deshabilitarControles() {
        for (JButton btn : btnMovimientos) {
            btn.setEnabled(false);
        }
        for (JButton btn : btnPokemones) {
            btn.setEnabled(false);
        }
        for (JButton btn : btnItems) {
            btn.setEnabled(false);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PoobkemonMainMenu menu = null;
            try {
                menu = new PoobkemonMainMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            }
            menu.setVisible(true);
        });
    }
}
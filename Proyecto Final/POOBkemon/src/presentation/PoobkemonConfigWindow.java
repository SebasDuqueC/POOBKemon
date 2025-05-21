package presentation;

import exceptions.PoobkemonException;
import domain.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PoobkemonConfigWindow extends JFrame {

    private JTextField txtJugador1, txtJugador2;
    private JComboBox<String> cbTipoJugador1, cbTipoJugador2;
    private JComboBox<String> cbModoJuego;
    private JButton btnIniciar, btnCancelar;
    private PoobkemonSelectionMenu seleccionEntrenador1, seleccionEntrenador2;
    private ItemSelectionMenu seleccionItems1, seleccionItems2; // Añadir aquí como campos de clase
    // NUEVO: Variables de instancia para los entrenadores
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    

    public PoobkemonConfigWindow() {
        setTitle("Configuración del Juego - POOBkemon 2025");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setUndecorated(true); // Sin bordes
        initGUI();
    }

    private void initGUI() {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("main/resources/font/PressStart2P.ttf"))).deriveFont(20f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Panel de fondo con imagen
        ImagePanel backgroundPanel = new ImagePanel("main/resources/images/background3.png") {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage bg = ImageIO.read(Objects.requireNonNull(
                            getClass().getClassLoader().getResourceAsStream("main/resources/images/background3.png")));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException | NullPointerException e) {
                    System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Panel transparente con layout flexible
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes uniformes
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los componentes
        gbc.weightx = 1.0; // Distribuir horizontalmente

        // Fuente personalizada
        Font fuenteGrande = new Font("Arial", Font.BOLD, 25);

        // Componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblModoJuego = new JLabel("Modo de Juego:");
        lblModoJuego.setFont(fuenteGrande);
        formPanel.add(lblModoJuego, gbc);

        gbc.gridx = 1;
        cbModoJuego = new FancyComboBox<>(new String[]{"Normal", "Supervivencia"});
        cbModoJuego.setFont(fuenteGrande);
        cbModoJuego.setPreferredSize(new Dimension(300, 40)); // Dimensiones iguales a los JTextField
        formPanel.add(cbModoJuego, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblJugador1 = new JLabel("Nombre Jugador 1:");
        lblJugador1.setFont(fuenteGrande);
        formPanel.add(lblJugador1, gbc);

        gbc.gridx = 1;
        txtJugador1 = new FancyTextField("Jugador");
        txtJugador1.setFont(fuenteGrande);
        txtJugador1.setPreferredSize(new Dimension(300, 40)); // Dimensiones más grandes
        formPanel.add(txtJugador1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblTipoJugador1 = new JLabel("Tipo Entrenador 1:");
        lblTipoJugador1.setFont(fuenteGrande);
        formPanel.add(lblTipoJugador1, gbc);

        gbc.gridx = 1;
        cbTipoJugador1 = new FancyComboBox<>(new String[]{"Humano", "IA - Ataque", "IA - Defensa", "IA - Experto"});
        cbTipoJugador1.setFont(fuenteGrande);
        formPanel.add(cbTipoJugador1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblJugador2 = new JLabel("Nombre Jugador 2:");
        lblJugador2.setFont(fuenteGrande);
        formPanel.add(lblJugador2, gbc);

        gbc.gridx = 1;
        txtJugador2 = new FancyTextField("Rival");
        txtJugador2.setFont(fuenteGrande);
        txtJugador2.setPreferredSize(new Dimension(300, 40)); // Dimensiones más grandes
        formPanel.add(txtJugador2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblTipoJugador2 = new JLabel("Tipo Entrenador 2:");
        lblTipoJugador2.setFont(fuenteGrande);
        formPanel.add(lblTipoJugador2, gbc);

        gbc.gridx = 1;
        cbTipoJugador2 = new FancyComboBox<>(new String[]{"Humano", "IA - Ataque", "IA - Defensa", "IA - Experto"});
        cbTipoJugador2.setFont(fuenteGrande);
        formPanel.add(cbTipoJugador2, gbc);

        // Botones
        gbc.gridy++;
        gbc.gridx = 0;
        btnCancelar = new FancyButton("Atrás");
        btnCancelar.setFont(fuenteGrande);
        btnCancelar.addActionListener(e -> {
            PoobkemonMainMenu mainMenu = null;
            try {
                mainMenu = new PoobkemonMainMenu();
            } catch (IOException | FontFormatException ex) {
                ex.printStackTrace();
            }
            if (mainMenu != null) {
                mainMenu.setVisible(true); // Mostrar el menú principal
            }
            dispose(); // Cerrar la ventana actual
        });
        formPanel.add(btnCancelar, gbc);

        gbc.gridx = 1;
        btnIniciar = new FancyButton("Iniciar Batalla");
        btnIniciar.setFont(fuenteGrande);
        btnIniciar.addActionListener(this::iniciarBatalla);
        formPanel.add(btnIniciar, gbc);

        // Añadir el formulario al panel central
        backgroundPanel.add(formPanel, gbc);
        add(backgroundPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getSize();
                ajustarComponentes(getContentPane(), size); // Ajusta componentes dinámicamente
            }
        });

        setFontToAllComponents(formPanel, customFont);
    }

        private void iniciarBatalla(ActionEvent e) {
        String jugador1 = txtJugador1.getText().trim();
        String jugador2 = txtJugador2.getText().trim();
        String modoJuego = (String) cbModoJuego.getSelectedItem();
    
        if (jugador1.isEmpty() || jugador2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ambos jugadores deben tener nombre.");
            return;
        }
    
        boolean esJugador1Humano = cbTipoJugador1.getSelectedIndex() == 0;
        boolean esJugador2Humano = cbTipoJugador2.getSelectedIndex() == 0;
    
        // Verificar si es modo supervivencia
        if ("Supervivencia".equals(modoJuego)) {
            // Solo permitir PvP para modo supervivencia
            if (!esJugador1Humano || !esJugador2Humano) {
                JOptionPane.showMessageDialog(this, 
                    "El modo Supervivencia solo está disponible para partidas entre jugadores humanos (PvP).",
                    "Modo no compatible", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                // Crear el modo supervivencia directamente
                ModoSupervivenciaPvsP modoSupervivencia = new ModoSupervivenciaPvsP(jugador1, jugador2);
                entrenador1 = modoSupervivencia.getEntrenador1();
                entrenador2 = modoSupervivencia.getEntrenador2();
                
                // Abrir la pantalla de batalla con los entrenadores generados
                PoobkemonGUI gui = new PoobkemonGUI(entrenador1, entrenador2, modoJuego);
                gui.setVisible(true);
                
                // Cerrar la ventana de configuración
                dispose();
                
            } catch (PoobkemonException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al iniciar modo supervivencia: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            return; // No continuar con el resto del método
        }
        // --- NUEVO BLOQUE PARA IA ---
        List<Pokemon> equipo1 = null;
        List<Pokemon> equipo2 = null;
        List<Item> items1 = null;
        List<Item> items2 = null;
        entrenador1 = null;
        entrenador2 = null;
        try {
            // Entrenador 1
            if (!esJugador1Humano) {
                if (cbTipoJugador1.getSelectedItem().equals("IA - Ataque")) {
                    equipo1 = EntrenadorIA.generarEquipoAleatorio(6);
                    items1 = EntrenadorIA.generarItemsAleatorios(4);
                    entrenador1 = new EntrenadorIAAtaque(jugador1, equipo1, items1);
                } else if (cbTipoJugador1.getSelectedItem().equals("IA - Defensa")) {
                    equipo1 = EntrenadorIA.generarEquipoAleatorio(6);
                    items1 = EntrenadorIA.generarItemsAleatorios(4);
                    entrenador1 = new EntrenadorIADefensa(jugador1, equipo1, items1);
                } else if (cbTipoJugador1.getSelectedItem().equals("IA - Experto")) {
                    equipo1 = EntrenadorIA.generarEquipoAleatorio(6);
                    items1 = EntrenadorIA.generarItemsAleatorios(4);
                    entrenador1 = new EntrenadorIAExperto(jugador1, equipo1, items1);
                }
            }
            // Entrenador 2
            if (!esJugador2Humano) {
                if (cbTipoJugador2.getSelectedItem().equals("IA - Ataque")) {
                    equipo2 = EntrenadorIA.generarEquipoAleatorio(6);
                    items2 = EntrenadorIA.generarItemsAleatorios(4);
                    entrenador2 = new EntrenadorIAAtaque(jugador2, equipo2, items2);
                } else if (cbTipoJugador2.getSelectedItem().equals("IA - Defensa")) {
                    equipo2 = EntrenadorIA.generarEquipoAleatorio(6);
                    items2 = EntrenadorIA.generarItemsAleatorios(4);
                    entrenador2 = new EntrenadorIADefensa(jugador2, equipo2, items2);
                } else if (cbTipoJugador2.getSelectedItem().equals("IA - Experto")) {
                    equipo2 = EntrenadorIA.generarEquipoAleatorio(6);
                    items2 = EntrenadorIA.generarItemsAleatorios(4);
                    entrenador2 = new EntrenadorIAExperto(jugador2, equipo2, items2);
                }
            }
            // Si ambos son IA, lanzar la batalla directamente
            if (!esJugador1Humano && !esJugador2Humano) {
                PoobkemonGUI gui = new PoobkemonGUI(entrenador1, entrenador2, modoJuego);
                gui.setVisible(true);
                dispose();
                return;
            }
        } catch (PoobkemonException ex) {
            JOptionPane.showMessageDialog(this, "Error al crear los entrenadores IA: " + ex.getMessage());
            return;
        }
        // --- FIN BLOQUE IA ---
        // Si uno es humano, seguir con la selección manual para ese jugador
        // Lista de Pokémon disponibles
        List<String> pokemonesDisponibles = PokemonLoader.obtenerNombresDePokemones();
    
        // Selección para el Entrenador 1
        if (esJugador1Humano) {
            seleccionEntrenador1 = new PoobkemonSelectionMenu(jugador1, pokemonesDisponibles, e1 -> {
                List<String> pokemonesEntrenador1 = seleccionEntrenador1.getPokemonesSeleccionados();
                if (pokemonesEntrenador1.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El Entrenador 1 debe seleccionar al menos un Pokémon.");
                    return;
                }
    
                JFrame frameSeleccion1 = (JFrame) SwingUtilities.getWindowAncestor(seleccionEntrenador1);
                frameSeleccion1.dispose();
    
                // Selección para el Entrenador 2
                if (esJugador2Humano) {
                    seleccionEntrenador2 = new PoobkemonSelectionMenu(jugador2, pokemonesDisponibles, e2 -> {
                        List<String> pokemonesEntrenador2 = seleccionEntrenador2.getPokemonesSeleccionados();
                        if (pokemonesEntrenador2.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "El Entrenador 2 debe seleccionar al menos un Pokémon.");
                            return;
                        }
    
                        JFrame frameSeleccion2 = (JFrame) SwingUtilities.getWindowAncestor(seleccionEntrenador2);
                        frameSeleccion2.dispose();
    
                        // Selección de items para ambos humanos
                        seleccionItems1 = new ItemSelectionMenu(jugador1, e3 -> {
                            List<Item> itemsEntrenador1 = seleccionItems1.getItemsSeleccionados();
                            JFrame frameItems1 = (JFrame) SwingUtilities.getWindowAncestor(seleccionItems1);
                            frameItems1.dispose();
    
                            seleccionItems2 = new ItemSelectionMenu(jugador2, e4 -> {
                                List<Item> itemsEntrenador2 = seleccionItems2.getItemsSeleccionados();
                                JFrame frameItems2 = (JFrame) SwingUtilities.getWindowAncestor(seleccionItems2);
                                frameItems2.dispose();
    
                                List<Pokemon> equipoJugador = seleccionEntrenador1.getPokemonesComoObjetos();
                                List<Pokemon> equipoRival = seleccionEntrenador2.getPokemonesComoObjetos();
    
                                Entrenador ent1;
                                Entrenador ent2;
                                try {
                                    ent1 = new Entrenador(jugador1, true, equipoJugador, itemsEntrenador1);
                                    ent2 = new Entrenador(jugador2, true, equipoRival, itemsEntrenador2);
                                } catch (PoobkemonException ex) {
                                    JOptionPane.showMessageDialog(this, "Error al crear los entrenadores: " + ex.getMessage());
                                    return;
                                }
    
                                PoobkemonGUI gui = new PoobkemonGUI(ent1, ent2, modoJuego);
                                gui.setVisible(true);
                                dispose();
                            });
    
                            JFrame frameItems2 = new JFrame("Selecciona Items - " + jugador2);
                            frameItems2.setContentPane(seleccionItems2);
                            frameItems2.setExtendedState(JFrame.MAXIMIZED_BOTH);
                            frameItems2.setUndecorated(true);
                            frameItems2.setVisible(true);
                        });
    
                        JFrame frameItems1 = new JFrame("Selecciona Items - " + jugador1);
                        frameItems1.setContentPane(seleccionItems1);
                        frameItems1.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        frameItems1.setUndecorated(true);
                        frameItems1.setVisible(true);
                    });
    
                    JFrame frame2 = new JFrame("Selecciona Pokémon - " + jugador2);
                    frame2.setContentPane(seleccionEntrenador2);
                    frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame2.setUndecorated(true);
                    frame2.setVisible(true);
                } else {
                    // Jugador 2 es IA: Selección de items solo para el humano
                    seleccionItems1 = new ItemSelectionMenu(jugador1, e3 -> {
                        List<Item> itemsEntrenador1 = seleccionItems1.getItemsSeleccionados();
                        JFrame frameItems1 = (JFrame) SwingUtilities.getWindowAncestor(seleccionItems1);
                        frameItems1.dispose();
                        // Crear el entrenador humano y usar el ya creado para la IA
                        List<Pokemon> equipoJugador = seleccionEntrenador1.getPokemonesComoObjetos();
                        Entrenador ent1;
                        try {
                            ent1 = new Entrenador(jugador1, true, equipoJugador, itemsEntrenador1);
                        } catch (PoobkemonException ex) {
                            JOptionPane.showMessageDialog(this, "Error al crear el entrenador: " + ex.getMessage());
                            return;
                        }
                        PoobkemonGUI gui = new PoobkemonGUI(ent1, entrenador2, modoJuego);
                        gui.setVisible(true);
                        dispose();
                    });
                        JFrame frameItems1 = new JFrame("Selecciona Items - " + jugador1);
                        frameItems1.setContentPane(seleccionItems1);
                        frameItems1.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        frameItems1.setUndecorated(true);
                        frameItems1.setVisible(true);
                }
            });
            JFrame frame1 = new JFrame("Selecciona Pokémon - " + jugador1);
            frame1.setContentPane(seleccionEntrenador1);
            frame1.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame1.setUndecorated(true);
            frame1.setVisible(true);
        } else if (esJugador2Humano) {
            // Jugador 1 es IA, Jugador 2 es humano
            seleccionEntrenador2 = new PoobkemonSelectionMenu(jugador2, pokemonesDisponibles, e2 -> {
                List<String> pokemonesEntrenador2 = seleccionEntrenador2.getPokemonesSeleccionados();
                if (pokemonesEntrenador2.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El Entrenador 2 debe seleccionar al menos un Pokémon.");
                    return;
                }
                JFrame frameSeleccion2 = (JFrame) SwingUtilities.getWindowAncestor(seleccionEntrenador2);
                frameSeleccion2.dispose();
                seleccionItems2 = new ItemSelectionMenu(jugador2, e4 -> {
                    List<Item> itemsEntrenador2 = seleccionItems2.getItemsSeleccionados();
                    JFrame frameItems2 = (JFrame) SwingUtilities.getWindowAncestor(seleccionItems2);
                    frameItems2.dispose();
                    List<Pokemon> equipoRival = seleccionEntrenador2.getPokemonesComoObjetos();
                    Entrenador ent2;
                    try {
                        ent2 = new Entrenador(jugador2, true, equipoRival, itemsEntrenador2);
                    } catch (PoobkemonException ex) {
                        JOptionPane.showMessageDialog(this, "Error al crear el entrenador: " + ex.getMessage());
                        return;
                    }
                    PoobkemonGUI gui = new PoobkemonGUI(entrenador1, ent2, modoJuego);
                    gui.setVisible(true);
                    dispose();
                });
                JFrame frameItems2 = new JFrame("Selecciona Items - " + jugador2);
                frameItems2.setContentPane(seleccionItems2);
                frameItems2.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frameItems2.setUndecorated(true);
                frameItems2.setVisible(true);
            });
            JFrame frame2 = new JFrame("Selecciona Pokémon - " + jugador2);
            frame2.setContentPane(seleccionEntrenador2);
            frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame2.setUndecorated(true);
            frame2.setVisible(true);
        }
    }


    // Clase FancyButton
    class FancyButton extends JButton {
        private boolean hover;

        public FancyButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setBorderPainted(false);
            setForeground(Color.WHITE); // Color del texto
            setFont(new Font("Arial", Font.BOLD, 16)); // Fuente personalizada

            // Listener para detectar hover
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Degradado de fondo
            GradientPaint gradient = new GradientPaint(0, 0, hover ? new Color(72, 145, 220) : new Color(101, 205, 81),
                    0, getHeight(), hover ? new Color(58, 117, 176) : new Color(72, 145, 220));
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            super.paintComponent(g);
            g2.dispose();
        }
    }

    // Clase FancyTextField
    class FancyTextField extends JTextField {
        private Color backgroundColor = new Color(165, 175, 176 );
        private Color borderColor = new Color(0, 0, 0, 150);
        private Color shadowColor = new Color(255, 255, 255);

        public FancyTextField(String text) {
            super(text);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
            setForeground(Color.BLACK); // Color del texto
            setFont(new Font("Arial", Font.BOLD, 16)); // Fuente personalizada
        }
    }

    // Clase FancyComboBox
    class FancyComboBox<E> extends JComboBox<E> {
        private Color backgroundColor = new Color(173, 204, 206);
        private Color hoverColor = new Color(255, 255, 255);
        private Color textColor = Color.BLACK;

        public FancyComboBox(E[] items) {
            super(items);
            setOpaque(false);
            setForeground(textColor);
            setFont(new Font("Press Start 2P", Font.PLAIN, 14));
            setRenderer(new CustomRenderer());
            setBorder(null); // Eliminar el borde predeterminado
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            // No se dibuja ningún borde aquí

            super.paintComponent(g);
            g2.dispose();
        }

        private class CustomRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);
                label.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                label.setBackground(isSelected ? hoverColor : backgroundColor);
                label.setForeground(textColor);
                return label;
            }
        }

    }


    private void ajustarComponentes(Container container, Dimension ventana) {
        int baseWidth = 600; // Ancho base de referencia
        int baseHeight = 400; // Altura base de referencia

        double scaleX = ventana.getWidth() / baseWidth;
        double scaleY = ventana.getHeight() / baseHeight;

        for (Component component : container.getComponents()) {
            if (component instanceof JLabel || component instanceof JTextField || component instanceof JButton || component instanceof JComboBox) {
                // Ajustar solo la posición relativa, no el tamaño
                component.setFont(component.getFont().deriveFont((float) 20)); // Mantener tamaño de fuente original
            } else if (component instanceof Container) {
                ajustarComponentes((Container) component, ventana); // Recursión para contenedores anidados
            }
        }

        container.revalidate();
        container.repaint();
    }

    private void setFontToAllComponents(Container container, Font font) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel || component instanceof JTextField || component instanceof JButton || component instanceof JComboBox) {
                component.setFont(font);
            } else if (component instanceof Container) {
                setFontToAllComponents((Container) component, font); // Llamada recursiva para contenedores anidados
            }
        }
    }

    
}
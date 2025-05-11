package presentation;

import exceptions.PoobkemonException;
import domain.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private JButton btnGuardar;
    private JButton btnCargar;

    private String modoJuego;

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
        this.modoJuego = modo;

        // Ensure player 1 always goes first
        if (batalla.getTurnoActual() != entrenador1) {
        batalla.cambiarTurno();
    }
        initGUI();
        actualizarInterfaz();
        
    // Añadir mensaje informativo para el modo supervivencia
    if ("Supervivencia".equals(modoJuego)) {
        areaLog.setText(""); // Limpiar log anterior
        areaLog.append("¡Bienvenido al MODO SUPERVIVENCIA!\n");
        areaLog.append("--------------------------------\n");
        areaLog.append("• Cada entrenador recibe 6 Pokémon aleatorios al nivel 100\n");
        areaLog.append("• No hay ítems disponibles\n");
        areaLog.append("• ¡Solo uno de ustedes saldrá victorioso!\n\n");
        areaLog.append("¡Inicia la batalla!\n");
    }
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
    
        // Añadir paneles de botones al panel de controles
        panelControles.add(panelMovimientos, "movimientos");
        panelControles.add(panelPokemones, "pokemones");
        panelControles.add(panelItems, "items");
    
        // NUEVO: Panel para botones de guardar y cargar partida
        JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 10, 0));
        
        btnGuardar = new JButton("Guardar Partida");
        btnGuardar.addActionListener(e -> guardarPartida());
        panelAcciones.add(btnGuardar);
        
        btnCargar = new JButton("Cargar Partida");
        btnCargar.addActionListener(e -> cargarPartida());
        panelAcciones.add(btnCargar);
    
        // Combinar paneles de navegación y acciones
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(panelNavegacion, BorderLayout.NORTH);
        panelBotones.add(panelAcciones, BorderLayout.SOUTH);
    
        // Panel inferior combinado
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelPokemonJugador, BorderLayout.NORTH);
        panelInferior.add(panelControles, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);  // Ahora contiene navegación y acciones
    
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
            Entrenador turnoActual = batalla.getTurnoActual();
            
            String resultado = batalla.ejecutarMovimiento(index);
            areaLog.append(resultado + "\n");
    
            if (batalla.hayGanador()) {
                Entrenador ganador = batalla.getGanador();
                areaLog.append("\n¡" + ganador.getNombre() + " ha ganado el combate!\n");
                deshabilitarControles();
                return;
            }
            
            // Si es el turno de una IA, ejecutar automáticamente
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
            Entrenador turnoActual = batalla.getTurnoActual();
            
            // Cambiar el Pokémon del entrenador actual
            turnoActual.cambiarPokemon(index);
            areaLog.append(turnoActual.getNombre() + " cambió a " + turnoActual.getActivo().getNombre() + "\n");
            
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
            Entrenador turnoActual = batalla.getTurnoActual();
            List<Item> items = turnoActual.getItems();
            
            if (index < items.size()) {
                turnoActual.usarItem(index, turnoActual.getActivo());
                Item item = items.get(index);
                areaLog.append(turnoActual.getNombre() + " usó " + item.getNombre() + 
                              " en " + turnoActual.getActivo().getNombre() + "\n");
                
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
        
        // Determinamos de quién es el turno actual
        Entrenador turnoActual = batalla.getTurnoActual();
        boolean esTurnoJugador1 = (turnoActual == entrenador1);
        
        // Indicador visual de turno más prominente
        if (esTurnoJugador1) {
            lblImagenPokemonJugador.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            lblImagenPokemonRival.setBorder(null);
            // Solo agrega el mensaje si cambió de turno
            if (!areaLog.getText().endsWith("► Es turno de " + entrenador1.getNombre() + "\n")) {
                areaLog.append("► Es turno de " + entrenador1.getNombre() + "\n");
            }
        } else {
            lblImagenPokemonRival.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            lblImagenPokemonJugador.setBorder(null);
            // Solo agrega el mensaje si cambió de turno
            if (!areaLog.getText().endsWith("► Es turno de " + entrenador2.getNombre() + "\n")) {
                areaLog.append("► Es turno de " + entrenador2.getNombre() + "\n");
            }
        }
        
        // Si es turno del jugador 2, mostrar sus movimientos/pokemones/items
        Pokemon pokemonActivo;
        List<Pokemon> equipoActivo;
        List<Item> itemsActivos;
        Entrenador entrenadorActivo;
        
        if (esTurnoJugador1) {
            pokemonActivo = entrenador1.getActivo();
            equipoActivo = entrenador1.getPokemones();
            itemsActivos = entrenador1.getItems();
            entrenadorActivo = entrenador1;
        } else {
            pokemonActivo = entrenador2.getActivo();
            equipoActivo = entrenador2.getPokemones();
            itemsActivos = entrenador2.getItems();
            entrenadorActivo = entrenador2;
        }
        
        // Actualizar botones de movimientos
        List<Movimiento> movimientosActivos = pokemonActivo.getMovimientos();
        for (int i = 0; i < btnMovimientos.length; i++) {
            if (i < movimientosActivos.size()) {
                Movimiento m = movimientosActivos.get(i);
                btnMovimientos[i].setText(m.getNombre() + " (" + m.getPpActual() + "/" + m.getPpMaximo() + ")");
                btnMovimientos[i].setEnabled(m.puedeUsarse());
            } else {
                btnMovimientos[i].setText("-");
                btnMovimientos[i].setEnabled(false);
            }
        }
        
        // Actualizar botones de Pokémon
        for (int i = 0; i < btnPokemones.length; i++) {
            if (i < equipoActivo.size()) {
                Pokemon p = equipoActivo.get(i);
                btnPokemones[i].setText(p.getNombre() + " (" + p.getPsActual() + "/" + p.getPsMaximo() + ")");
                btnPokemones[i].setEnabled(i != entrenadorActivo.getActivoIndex() && !p.estaDebilitado());
            } else {
                btnPokemones[i].setText("-");
                btnPokemones[i].setEnabled(false);
            }
        }
        
        // Actualizar botones de items
        for (int i = 0; i < btnItems.length; i++) {
            if (i < itemsActivos.size()) {
                Item item = itemsActivos.get(i);
                btnItems[i].setText(item.getNombre() + " x" + item.getCantidad());
                btnItems[i].setEnabled(item.disponible());
            } else {
                btnItems[i].setText("-");
                btnItems[i].setEnabled(false);
            }
        }

        boolean esModoSupervivencia = "Supervivencia".equals(modoJuego);
        
        // Actualizar botones de items - deshabilitar en modo supervivencia
        for (int i = 0; i < btnItems.length; i++) {
            if (esModoSupervivencia) {
                // En modo supervivencia, no hay ítems
                btnItems[i].setText("Sin ítems en modo supervivencia");
                btnItems[i].setEnabled(false);
            } else if (i < itemsActivos.size()) {
                Item item = itemsActivos.get(i);
                btnItems[i].setText(item.getNombre() + " x" + item.getCantidad());
                btnItems[i].setEnabled(item.disponible());
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
    // Método para guardar partida
private void guardarPartida() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Partida");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Partidas POOBkemon (*.pkm)", "pkm"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Asegurar que el archivo tenga la extensión .pkm
            if (!fileToSave.getName().toLowerCase().endsWith(".pkm")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pkm");
            }
            
            // Crear objeto GameState con el estado actual del juego
            GameState gameState = new GameState(
                entrenador1, 
                entrenador2, 
                batalla.getTurnoActual() == entrenador1, modoJuego
                 // Puedes guardar el modo de juego como un campo si tienes diferentes modos
            );
            
            // Guardar en archivo
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(fileToSave))) {
                out.writeObject(gameState);
            }
            
            areaLog.append("Partida guardada con éxito en: " + fileToSave.getName() + "\n");
        }
    } catch (IOException ex) {
        areaLog.append("Error al guardar la partida: " + ex.getMessage() + "\n");
        JOptionPane.showMessageDialog(this, 
            "No se pudo guardar la partida: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Método para cargar partida
private void cargarPartida() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cargar Partida");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Partidas POOBkemon (*.pkm)", "pkm"));
        
        int userSelection = fileChooser.showOpenDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            
            // Cargar desde archivo
            try (ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(fileToLoad))) {
                GameState gameState = (GameState) in.readObject();
                
                // Restaurar estado del juego
                this.entrenador1 = gameState.getEntrenador1();
                this.entrenador2 = gameState.getEntrenador2();
                this.batalla = new Batalla(entrenador1, entrenador2);
                
                // Establecer el turno correcto
                if (gameState.isTurnoEntrenador1() != (batalla.getTurnoActual() == entrenador1)) {
                    batalla.cambiarTurno();
                }
                
                actualizarInterfaz();
                areaLog.setText(""); // Limpiar log
                areaLog.append("Partida cargada con éxito desde: " + fileToLoad.getName() + "\n");
                areaLog.append("¡Continúa la batalla!\n");
            }
        }
    } catch (IOException | ClassNotFoundException ex) {
        areaLog.append("Error al cargar la partida: " + ex.getMessage() + "\n");
        JOptionPane.showMessageDialog(this, 
            "No se pudo cargar la partida: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}
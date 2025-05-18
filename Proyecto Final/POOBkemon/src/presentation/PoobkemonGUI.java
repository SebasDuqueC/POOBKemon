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
import java.io.*;
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
    private JLabel lblImagenPokemonRival;
    private JLabel lblImagenPokemonJugador;
    private JLabel lblTiempoRestante;

    private JButton[] btnMovimientos = new JButton[4];
    private JButton[] btnPokemones = new JButton[6];
    private JButton[] btnItems = new JButton[4];

    private JButton btnGuardar;
    private JButton btnCargar;
    private Timer timerTurno;
    private static final int TIEMPO_TURNO = 20; // 20 segundos por turno

    public String modoJuego;
    // Nueva variable para el modo de juego
    private OpcionesPanelEsmeralda panelOpciones;
    private InfoPanelEsmeralda infoRival;
    private InfoPanelEsmeralda infoJugador;
    private JPanel panelRival;
    private JPanel panelJugador;
    private JPanel panelCentral;

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

        initGUI();
        mostrarLanzamientoMoneda();

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
        panelPokemonRival.setBorder(BorderFactory.createEmptyBorder());
        panelPokemonRival.setOpaque(true); // Asegurarse de que el fondo sea visible


// Configurar el panel de información del rival
        JPanel panelInfoRival = new JPanel();
        panelInfoRival.setLayout(new BoxLayout(panelInfoRival, BoxLayout.Y_AXIS));
        panelInfoRival.setBackground(new Color(175, 239, 142)); // Verde pasto

        lblNombreRival = new JLabel("Pokémon Rival");
        lblNombreRival.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        lblNombreRival.setForeground(Color.BLACK); // Letras negras

        lblPSRival = new JLabel("PS: 100/100");
        lblPSRival.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        lblPSRival.setForeground(Color.BLACK); // Letras negras
        lblTiempoRestante = new JLabel("Tiempo: 20s");
        lblTiempoRestante.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        lblTiempoRestante.setForeground(Color.RED);

        panelInfoRival.add(lblNombreRival);
        panelInfoRival.add(lblPSRival);



// Crear un JPanel con fondo para el JLabel
        JPanel panelConFondo = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    // Cargar la imagen de fondo
                    backgroundImage = new ImageIcon(Objects.requireNonNull(
                            getClass().getClassLoader().getResource("main/resources/images/pantallaBatallaArriba.png"))).getImage();
                } catch (Exception e) {
                    System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelConFondo.setLayout(new BorderLayout());
        panelConFondo.setBackground(new Color(124, 252, 0)); // Verde pasto
        panelConFondo.setOpaque(true); // Asegurarse de que el fondo sea visible

// Imagen del Pokémon rival
        lblImagenPokemonRival = new JLabel();
        lblImagenPokemonRival.setPreferredSize(new Dimension(200, 200));
        lblImagenPokemonRival.setBounds(50, 100, 200, 200); // Mover más a la izquierda//
        lblImagenPokemonRival.setHorizontalAlignment(SwingConstants.CENTER);


// Crear un JPanel para ajustar la posición del GIF
        JPanel panelAjuste = new JPanel(new BorderLayout());
        panelAjuste.setOpaque(false); // Hacer el panel transparente
        panelAjuste.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Margen izquierdo de 20 píxeles

// Añadir el JLabel al panel de ajuste
        panelAjuste.add(lblImagenPokemonRival, BorderLayout.CENTER);

// Añadir el panel de ajuste al panel con fondo
        panelConFondo.add(panelAjuste, BorderLayout.EAST);

// Añadir el panel con fondo al panel principal
        panelPokemonRival.add(panelInfoRival, BorderLayout.EAST);
        panelPokemonRival.add(panelConFondo, BorderLayout.CENTER);

        // Panel central: área de mensajes
        areaLog = new JTextArea(5, 40);
        areaLog.setEditable(false);
        areaLog.setBackground(new Color(90, 207, 113)); // Verde pasto
        areaLog.setOpaque(true); // Asegurarse de que el fondo sea visible
        // Cargar y aplicar la fuente personalizada
        try {
            // Cargar la fuente desde los recursos
            // Cargar la fuente desde los recursos
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("main/resources/font/PressStart2P.ttf");
            if (fontStream == null) {
                throw new IOException("No se encontró el archivo de fuente: PressStart2P.ttf");
            }
            Font pressStartFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            pressStartFont = pressStartFont.deriveFont(Font.PLAIN, 12); // Tamaño de fuente 12
            areaLog.setFont(pressStartFont);
        } catch (FontFormatException | IOException e) {
            System.err.println("Error al cargar la fuente PressStart2P: " + e.getMessage());
        }
        JScrollPane scrollLog = new JScrollPane(areaLog);
        areaLog.append("¡Inicia la batalla!\n");

        // Panel inferior: Pokémon del jugador (ahora con imagen)
        panelPokemonJugador = new JPanel(new BorderLayout());
        panelPokemonJugador.setBorder(BorderFactory.createEmptyBorder());
        panelPokemonJugador.setOpaque(true); // Asegurarse de que el fondo sea visible

        JPanel panelInfoJugador = new JPanel();
        panelInfoJugador.setLayout(new BoxLayout(panelInfoJugador, BoxLayout.Y_AXIS));
        panelInfoJugador.setBackground(new Color(90, 207, 113));



        lblNombreJugador = new JLabel("Tu Pokémon");
        lblNombreJugador.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        lblPSJugador = new JLabel("PS: 100/100");
        lblPSJugador.setFont(new Font("Press Start 2P", Font.BOLD, 16));

        lblPSJugador.setForeground(Color.BLACK); // Letras negras


        panelInfoJugador.add(lblNombreJugador);
        panelInfoJugador.add(lblPSJugador);

        // Crear un JPanel con fondo para el JLabel
        JPanel panelConFondoJugador = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    // Cargar la imagen de fondo
                    backgroundImage = new ImageIcon(Objects.requireNonNull(
                            getClass().getClassLoader().getResource("main/resources/images/pantallaBatallaAbajo.png"))).getImage();
                } catch (Exception e) {
                    System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelConFondo.setBackground(new Color(90, 207, 113)); // Verde pasto
        panelConFondo.setOpaque(true); // Asegurarse de que el fondo sea visible

// Imagen del Pokémon del jugador
        lblImagenPokemonJugador = new JLabel();
        lblImagenPokemonJugador.setPreferredSize(new Dimension(200, 200));
        lblImagenPokemonJugador.setHorizontalAlignment(SwingConstants.CENTER);

// Crear un JPanel para ajustar la posición del GIF
        JPanel panelAjusteJugador = new JPanel(new BorderLayout());
        panelAjusteJugador.setOpaque(false); // Hacer el panel transparente
        panelAjusteJugador.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Margen izquierdo de 20 píxeles

// Añadir el JLabel al panel de ajuste en la posición izquierda
        panelAjusteJugador.add(lblImagenPokemonJugador, BorderLayout.WEST);

// Añadir el panel de ajuste al panel con fondo
        panelConFondoJugador.add(panelAjusteJugador, BorderLayout.WEST);

// Añadir el panel con fondo al panel principal
        panelPokemonJugador.add(panelInfoJugador, BorderLayout.WEST);
        panelPokemonJugador.add(panelConFondoJugador, BorderLayout.CENTER);

        panelConFondo.setBackground(new Color(90, 207, 113)); // Verde pasto
        panelConFondo.setOpaque(true); // Asegurarse de que el fondo sea visible

        // Controles: movimientos, cambio de pokémon, items
        panelControles = new JPanel(new CardLayout());

        // Panel de movimientos
        panelMovimientos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelMovimientos.setBorder(BorderFactory.createTitledBorder("Movimientos"));

        for (int i = 0; i < btnMovimientos.length; i++) {
            final int index = i;
            btnMovimientos[i] = new JButton("Movimiento " + (i + 1));
            btnMovimientos[i].setBackground(new Color(245, 245, 220)); // Color hueso
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
            btnPokemones[i].setBackground(new Color(245, 245, 220)); // Color hueso
            btnPokemones[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cambiarPokemon(index);
                }
            });
            panelPokemones.add(btnPokemones[i]);
        }

        // Panel de items
        panelItems = new JPanel(new GridLayout(2, 2, 10, 10));
        panelItems.setBorder(BorderFactory.createTitledBorder("Usar Item"));

        for (int i = 0; i < btnItems.length; i++) {
            final int index = i;
            btnItems[i] = new JButton("Item " + (i + 1));
            btnItems[i].setBackground(new Color(245, 245, 220)); // Color hueso
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
        btnVerMovimientos.setBackground(new Color(245, 245, 220)); // Color hueso
        btnVerMovimientos.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelControles.getLayout();
            cl.show(panelControles, "movimientos");
        });

        JButton btnVerPokemones = new JButton("Pokémon");
        btnVerPokemones.setBackground(new Color(245, 245, 220)); // Color hueso
        btnVerPokemones.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelControles.getLayout();
            cl.show(panelControles, "pokemones");
        });

        JButton btnVerItems = new JButton("Items");
        btnVerItems.setBackground(new Color(245, 245, 220)); // Color hueso

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

        // Panel para botones de guardar y cargar partida
        JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 10, 0));

        btnGuardar = new JButton("Guardar Partida");
        btnGuardar.setBackground(new Color(245, 245, 220)); // Color hueso
        btnGuardar.setForeground(Color.BLACK); // Texto en negro
        btnGuardar.addActionListener(e -> guardarPartida());
        panelAcciones.add(btnGuardar);

        btnCargar = new JButton("Cargar Partida");
        btnCargar.setBackground(new Color(245, 245, 220)); // Color hueso
        btnCargar.setForeground(Color.BLACK); // Texto en negro
        btnCargar.addActionListener(e -> cargarPartida());
        panelAcciones.add(btnCargar);

        // Aplicar la fuente personalizada
        try {
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("main/resources/font/PressStart2P.ttf");
            if (fontStream == null) {
                throw new IOException("No se encontró el archivo de fuente: PressStart2P.ttf");
            }
            Font pressStartFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            pressStartFont = pressStartFont.deriveFont(Font.PLAIN, 12);

            for (JButton btnMovimiento : btnMovimientos) {
                btnMovimiento.setFont(pressStartFont);
            }
            for (JButton btnPokemon : btnPokemones) {
                btnPokemon.setFont(pressStartFont);
            }
            for (JButton btnItem : btnItems) {
                btnItem.setFont(pressStartFont);
            }
            btnGuardar.setFont(pressStartFont);
            btnCargar.setFont(pressStartFont);

        } catch (FontFormatException | IOException e) {
            System.err.println("Error al cargar la fuente Press Start 2P: " + e.getMessage());
        }

        // Combinar paneles de navegación y acciones
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(panelNavegacion, BorderLayout.NORTH);
        panelBotones.add(panelAcciones, BorderLayout.SOUTH);

        // Panel inferior combinado
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelPokemonJugador, BorderLayout.NORTH);
        panelInferior.add(panelControles, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        // Ensamblar todo
        panelPrincipal.add(panelPokemonRival, BorderLayout.NORTH);
        panelPrincipal.add(scrollLog, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Mostrar primero la pestaña de movimientos
        CardLayout cl = (CardLayout) panelControles.getLayout();
        cl.show(panelControles, "movimientos");
    }

    private void mostrarPanelAccion(String accion) {
        CardLayout cl = (CardLayout) panelCentral.getLayout();
        cl.show(panelCentral, accion);
    }

    private JPanel crearPanelMovimientos() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setOpaque(false);
        List<Movimiento> movimientos = entrenador1.getActivo().getMovimientos();
        for (int i = 0; i < movimientos.size(); i++) {
            final int idx = i;
            JButton btn = new JButton(movimientos.get(i).getNombre());
            btn.setFont(new Font("Press Start 2P", Font.BOLD, 14));
            btn.addActionListener(e -> realizarMovimiento(idx));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelPokemones() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setOpaque(false);
        List<Pokemon> equipo = entrenador1.getPokemones();
        for (int i = 0; i < equipo.size(); i++) {
            final int idx = i;
            JButton btn = new JButton(equipo.get(i).getNombre());
            btn.setFont(new Font("Press Start 2P", Font.BOLD, 14));
            btn.addActionListener(e -> cambiarPokemon(idx));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel crearPanelItems() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setOpaque(false);
        List<Item> items = entrenador1.getItems();
        for (int i = 0; i < items.size(); i++) {
            final int idx = i;
            JButton btn = new JButton(items.get(i).getNombre());
            btn.setFont(new Font("Press Start 2P", Font.BOLD, 14));
            btn.addActionListener(e -> usarItem(idx));
            panel.add(btn);
        }
        return panel;
    }

    // Método para cargar y mostrar el GIF del Pokémon activo
    private void mostrarGifPokemonActivo(JLabel label, String nombrePokemon) {
        try {
            // Construir la ruta del archivo GIF
            String nombreArchivo = nombrePokemon.toLowerCase() + ".gif";
            ImageIcon gifPokemon = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("main/resources/images/" + nombreArchivo)));

            // Establecer el GIF en el JLabel
            label.setIcon(gifPokemon);
            label.setText(""); // Limpiar texto si lo hubiera
        } catch (Exception e) {
            System.err.println("No se pudo cargar el GIF para " + nombrePokemon + ": " + e.getMessage());
            label.setIcon(null);
            label.setText("[ " + nombrePokemon + " ]"); // Mostrar texto si no se encuentra el GIF
        }
    }

    // Actualizar el GIF del Pokémon activo
    private void actualizarPokemonActivo() {
        Entrenador entrenadorActivo = batalla.getTurnoActual();
        Pokemon pokemonActivo = entrenadorActivo.getActivo();
        if (pokemonActivo != null) {
            mostrarGifPokemonActivo(lblImagenPokemonJugador, pokemonActivo.getNombre());        }
    }

    private void iniciarTimerTurno() {
        if (timerTurno != null) {
            timerTurno.stop(); // Detener cualquier timer previo
        }

        // Reiniciar el texto del temporizador
        lblTiempoRestante.setText("Tiempo: " + TIEMPO_TURNO + "s");

        // Crear un nuevo Timer
        timerTurno = new Timer(1000, new ActionListener() {
            int tiempoRestante = TIEMPO_TURNO;

            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                lblTiempoRestante.setText("Tiempo: " + tiempoRestante + "s");

                if (tiempoRestante <= 0) {
                    timerTurno.stop();
                    areaLog.append("¡Se acabó el tiempo! Cambio de turno.\n");

                    // Cambiar turno automáticamente
                    batalla.cambiarTurno();
                    actualizarInterfaz();
                    iniciarTimerTurno(); // Reiniciar el timer para el nuevo turno
                }
            }
        });

        timerTurno.start(); // Iniciar el Timer
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
                timerTurno.stop();
                return;
            }

            // Verificar si el Pokémon actual quedó deshabilitado
            verificarPokemonDeshabilitado(turnoActual);

            // Si es el turno de una IA, ejecutar automáticamente
            if (!batalla.getTurnoActual().esHumano()) {
                ejecutarTurnoIA();
            }

            actualizarInterfaz();
            iniciarTimerTurno();
        } catch (PoobkemonException e) {
            areaLog.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void verificarPokemonDeshabilitado(Entrenador entrenador) {
        Pokemon pokemonActual = entrenador.getActivo();
        if (pokemonActual.estaDebilitado()) {
            areaLog.append(pokemonActual.getNombre() + " ha quedado deshabilitado.\n");

            // Buscar el siguiente Pokémon disponible
            List<Pokemon> equipo = entrenador.getPokemones();
            int siguienteIndex = -1;

            for (int i = 0; i < equipo.size(); i++) {
                if (!equipo.get(i).estaDebilitado() && i != entrenador.getActivoIndex()) {
                    siguienteIndex = i;
                    break;
                }
            }

            if (siguienteIndex != -1) {
                try {
                    entrenador.cambiarPokemon(siguienteIndex);
                    areaLog.append(entrenador.getNombre() + " cambió a " + entrenador.getActivo().getNombre() + "\n");
                } catch (PoobkemonException e) {
                    areaLog.append("Error al cambiar de Pokémon: " + e.getMessage() + "\n");
                }
            } else {
                // Si no hay más Pokémon disponibles, el otro entrenador gana
                areaLog.append("¡" + entrenador.getNombre() + " no tiene más Pokémon disponibles!\n");
                if (entrenador == entrenador1) {
                    areaLog.append("¡" + entrenador2.getNombre() + " ha ganado el combate!\n");
                } else {
                    areaLog.append("¡" + entrenador1.getNombre() + " ha ganado el combate!\n");
                }
                deshabilitarControles();
                timerTurno.stop();
            }
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
                timerTurno.stop();
                return;
            }

            // Verificar si el Pokémon de la IA quedó deshabilitado
            verificarPokemonDeshabilitado(entrenador2);

            // Asegurar que el turno regrese al jugador después de la acción de la IA
            if (batalla.getTurnoActual() != entrenador1) {
                areaLog.append("Corrigiendo turno, ahora es tu turno\n");
                batalla.cambiarTurno();
            }

            actualizarInterfaz();
            iniciarTimerTurno();
        } catch (PoobkemonException e) {
            areaLog.append("Error del oponente: " + e.getMessage() + "\n");
            // Asegurar que después de un error siempre vuelva al jugador
            if (batalla.getTurnoActual() != entrenador1) {
                batalla.cambiarTurno();
            }
            actualizarInterfaz();
            iniciarTimerTurno();
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
            iniciarTimerTurno();
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
                iniciarTimerTurno();
            }
        } catch (PoobkemonException e) {
            areaLog.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void mostrarLanzamientoMoneda() {
        // Deshabilitar todos los controles durante el lanzamiento
        deshabilitarControles();
        // Limpiar el área de log
        areaLog.setText("");
        areaLog.append("¡Lanzando moneda para decidir quién empieza!\n");
        areaLog.append("Presiona el botón para lanzar la moneda...\n");

        // Crear botón de lanzamiento
        JButton btnLanzar = new JButton("¡Lanzar Moneda!");
        btnLanzar.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        btnLanzar.setPreferredSize(new Dimension(200, 50));
        btnLanzar.setBackground(new Color(245, 245, 220)); // Color hueso
        btnLanzar.setForeground(Color.BLACK); // Texto en negro

        // Reemplazar temporalmente los controles con el botón de lanzamiento
        panelControles.removeAll();
        panelControles.add(btnLanzar);
        panelControles.revalidate();
        panelControles.repaint();

        // Acción del botón
        btnLanzar.addActionListener(e -> {
            // Simular lanzamiento de moneda
            boolean empiezaJugador1 = Math.random() < 0.5;
            // Actualizar el turno inicial
            if (!empiezaJugador1) {
                batalla.cambiarTurno();
            }
            // Mostrar resultado
            String ganador = empiezaJugador1 ? entrenador1.getNombre() : entrenador2.getNombre();
            areaLog.append("¡" + ganador + " empieza la batalla!\n");

            // Restaurar los controles normales
            panelControles.removeAll();
            CardLayout cl = (CardLayout) panelControles.getLayout();
            panelControles.add(panelMovimientos, "movimientos");
            panelControles.add(panelPokemones, "pokemones");
            panelControles.add(panelItems, "items");
            cl.show(panelControles, "movimientos");

            // Actualizar la interfaz y comenzar el timer
            actualizarInterfaz();
            iniciarTimerTurno();
        });
    }

    private void deshabilitarControles() {
        // Deshabilitar botones del panel de opciones si existen
        if (panelOpciones != null) {
            panelOpciones.btnLucha.setEnabled(false);
            panelOpciones.btnMochila.setEnabled(false);
            panelOpciones.btnPokemon.setEnabled(false);
            panelOpciones.btnHuir.setEnabled(false);
        }
        // Deshabilitar botones de los paneles de acción si existen
        deshabilitarBotonesEnPanel(panelMovimientos);
        deshabilitarBotonesEnPanel(panelPokemones);
        deshabilitarBotonesEnPanel(panelItems);
    }

    private void habilitarControles() {
        // Habilitar botones del panel de opciones si existen
        if (panelOpciones != null) {
            panelOpciones.btnLucha.setEnabled(true);
            panelOpciones.btnMochila.setEnabled(true);
            panelOpciones.btnPokemon.setEnabled(true);
            panelOpciones.btnHuir.setEnabled(true);
        }
        // Habilitar botones de los paneles de acción si existen
        habilitarBotonesEnPanel(panelMovimientos);
        habilitarBotonesEnPanel(panelPokemones);
        habilitarBotonesEnPanel(panelItems);
    }

    private void deshabilitarBotonesEnPanel(JPanel panel) {
        if (panel != null) {
            for (Component c : panel.getComponents()) {
                if (c instanceof JButton) {
                    c.setEnabled(false);
                }
            }
        }
    }

    private void habilitarBotonesEnPanel(JPanel panel) {
        if (panel != null) {
            for (Component c : panel.getComponents()) {
                if (c instanceof JButton) {
                    c.setEnabled(true);
                }
            }
        }
    }

    private void actualizarInterfaz() {
        // Actualizar información del Pokémon del jugador
        Pokemon pokemonJugador = entrenador1.getActivo();
        lblNombreJugador.setText(pokemonJugador.getNombre());
        lblPSJugador.setText("PS: " + pokemonJugador.getPsActual() + "/" + pokemonJugador.getPsMaximo());
        mostrarGifPokemonActivo(lblImagenPokemonJugador, pokemonJugador.getNombre());

        // Actualizar información del Pokémon del rival
        Pokemon pokemonRival = entrenador2.getActivo();
        lblNombreRival.setText(pokemonRival.getNombre());
        lblPSRival.setText("PS: " + pokemonRival.getPsActual() + "/" + pokemonRival.getPsMaximo());
        mostrarGifPokemonActivo(lblImagenPokemonRival, pokemonRival.getNombre());

        // Determinar de quién es el turno actual
        Entrenador turnoActual = batalla.getTurnoActual();
        boolean esTurnoJugador1 = (turnoActual == entrenador1);

        // Remover el timer de ambos paneles si ya existe
        JPanel panelInfoJugador = (JPanel) lblNombreJugador.getParent();
        JPanel panelInfoRival = (JPanel) lblNombreRival.getParent();
        if (lblTiempoRestante.getParent() != null) {
            ((JPanel) lblTiempoRestante.getParent()).remove(lblTiempoRestante);
        }

        // Agregar el timer al panel del jugador activo
        if (esTurnoJugador1) {
            panelInfoJugador.add(lblTiempoRestante);
            lblImagenPokemonJugador.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            lblImagenPokemonRival.setBorder(null);
        } else {
            panelInfoRival.add(lblTiempoRestante);
            lblImagenPokemonRival.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            lblImagenPokemonJugador.setBorder(null);
        }

        // Redibujar los paneles
        panelInfoJugador.revalidate();
        panelInfoJugador.repaint();
        panelInfoRival.revalidate();
        panelInfoRival.repaint();

        // Habilitar controles solo si es el turno del jugador humano
        if (esTurnoJugador1 && entrenador1.esHumano()) {
            habilitarControles();
        } else if (!esTurnoJugador1 && entrenador2.esHumano()) {
            habilitarControles();
        } else {
            deshabilitarControles();
        }

        // Actualizar botones de movimientos
        List<Movimiento> movimientosActivos = pokemonJugador.getMovimientos();
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
        List<Pokemon> equipoActivo = turnoActual.getPokemones();
        for (int i = 0; i < btnPokemones.length; i++) {
            if (i < equipoActivo.size()) {
                Pokemon p = equipoActivo.get(i);
                btnPokemones[i].setText(p.getNombre() + " (" + p.getPsActual() + "/" + p.getPsMaximo() + ")");
                btnPokemones[i].setEnabled(i != turnoActual.getActivoIndex() && !p.estaDebilitado());
            } else {
                btnPokemones[i].setText("-");
                btnPokemones[i].setEnabled(false);
            }
        }

        // Actualizar botones de items
        List<Item> itemsActivos = turnoActual.getItems();
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

        // Hacer scroll hacia abajo en el área de logs
        areaLog.setCaretPosition(areaLog.getDocument().getLength());

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

// Clase interna para el fondo de batalla estilo Pokémon Esmeralda
class BattleBackgroundPanel extends JPanel {
    private Image backgroundImage;
    private JLabel pokemonLabel;

    public BattleBackgroundPanel(ImageIcon pokemonImage) {
        try {
            backgroundImage = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("main/resources/images/fondo_batalla.jpeg"))).getImage();
            System.out.println("Imagen cargada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            backgroundImage = null;
        }
        setLayout(new BorderLayout());
        setBackground(Color.BLACK); // Fondo negro


        // Configurar el JLabel para el Pokémon
        pokemonLabel = new JLabel(pokemonImage);
        pokemonLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha
        pokemonLabel.setVerticalAlignment(SwingConstants.TOP); // Alinear en la parte superior

        // Añadir el JLabel al panel
        add(pokemonLabel, BorderLayout.EAST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}


}

// Panel de información estilo Esmeralda para un Pokémon
class InfoPanelEsmeralda extends JPanel {
    private JLabel lblNombre;
    private JLabel lblNivel;
    private JProgressBar barraVida;
    private JProgressBar barraExp;
    private JLabel lblPS;
    private boolean mostrarPS;

    public InfoPanelEsmeralda(String nombre, int nivel, int psActual, int psMax, int expActual, int expMax, boolean mostrarPS) {
        setLayout(null);
        setPreferredSize(new Dimension(300, 60));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.mostrarPS = mostrarPS;

        lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        lblNombre.setBounds(10, 5, 120, 20);
        add(lblNombre);

        lblNivel = new JLabel("Nv. " + nivel);
        lblNivel.setFont(new Font("Press Start 2P", Font.BOLD, 14));
        lblNivel.setBounds(200, 5, 80, 20);
        add(lblNivel);

        barraVida = new JProgressBar(0, psMax);
        barraVida.setValue(psActual);
        barraVida.setBounds(10, 30, 180, 15);
        barraVida.setForeground(new Color(0, 200, 0));
        barraVida.setBackground(Color.LIGHT_GRAY);
        barraVida.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        barraVida.setStringPainted(false);
        add(barraVida);

        if (mostrarPS) {
            lblPS = new JLabel(psActual + "/" + psMax);
            lblPS.setFont(new Font("Press Start 2P", Font.BOLD, 12));
            lblPS.setBounds(200, 28, 80, 20);
            add(lblPS);
        }

        barraExp = new JProgressBar(0, expMax);
        barraExp.setValue(expActual);
        barraExp.setBounds(10, 48, 180, 7);
        barraExp.setForeground(new Color(0, 120, 255));
        barraExp.setBackground(Color.LIGHT_GRAY);
        barraExp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        barraExp.setStringPainted(false);
        add(barraExp);
    }

    public void actualizar(String nombre, int nivel, int psActual, int psMax, int expActual, int expMax) {
        lblNombre.setText(nombre);
        lblNivel.setText("Nv. " + nivel);
        barraVida.setMaximum(psMax);
        barraVida.setValue(psActual);
        if (mostrarPS) {
            lblPS.setText(psActual + "/" + psMax);
        }
        barraExp.setMaximum(expMax);
        barraExp.setValue(expActual);
    }
}

// Panel de opciones estilo Esmeralda
class OpcionesPanelEsmeralda extends JPanel {
    public JButton btnLucha, btnMochila, btnPokemon, btnHuir;
    private JLabel lblPregunta;

    public OpcionesPanelEsmeralda(String nombrePokemon) {
        setLayout(null);
        setPreferredSize(new Dimension(600, 120));
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        lblPregunta = new JLabel("¿Qué hará " + nombrePokemon + "?");
        lblPregunta.setFont(new Font("Press Start 2P", Font.BOLD, 18));
        lblPregunta.setBounds(20, 10, 400, 30);
        add(lblPregunta);

        btnLucha = crearBoton("LUCHA", new Color(220, 60, 60), 20, 50);
        btnMochila = crearBoton("MOCHILA", new Color(255, 220, 60), 160, 50);
        btnPokemon = crearBoton("POKÉMON", new Color(60, 220, 100), 300, 50);
        btnHuir = crearBoton("HUIR", new Color(60, 120, 220), 440, 50);

        add(btnLucha);
        add(btnMochila);
        add(btnPokemon);
        add(btnHuir);
    }

    private JButton crearBoton(String texto, Color color, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        btn.setBounds(x, y, 120, 40);
        btn.setBackground(color);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn.setFocusPainted(false);
        return btn;
    }

    public void setNombrePokemon(String nombre) {
        lblPregunta.setText("¿Qué hará " + nombre + "?");
    }
}

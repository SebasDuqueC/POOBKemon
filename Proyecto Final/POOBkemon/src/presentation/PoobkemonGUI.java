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

/**
 * Clase principal de la interfaz gráfica de usuario (GUI) para el juego POOBkemon.
 * Esta clase extiende JFrame y se encarga de gestionar la visualización y la interacción
 * del usuario con los elementos del juego, como los Pokémon, movimientos, ítems y la batalla.

 * Funcionalidades principales:
 * - Mostrar información de los Pokémon del jugador y del rival.
 * - Permitir al jugador realizar movimientos, cambiar Pokémon o usar ítems.
 * - Gestionar el flujo de la batalla, incluyendo turnos y temporizador.
 * - Guardar y cargar partidas.
 * - Mostrar el resultado de la batalla.
 */

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
    private OpcionesPanelEsmeralda panelOpciones;
    private InfoPanelEsmeralda infoRival;
    private InfoPanelEsmeralda infoJugador;
    private JPanel panelRival;
    private JPanel panelJugador;
    private JPanel panelCentral;

    /**
     * Constructor de la clase `PoobkemonGUI`.
     * Inicializa la interfaz gráfica para el combate de POOBkemon.
     *
     * @param entrenador1 El primer entrenador participante en la batalla.
     * @param entrenador2 El segundo entrenador participante en la batalla.
     * @param modo        El modo de juego seleccionado (Normal o Supervivencia).
     */

    public PoobkemonGUI(Entrenador entrenador1, Entrenador entrenador2, String modo) {
        setTitle("POOBkemon - Combate");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.batalla = new Batalla(entrenador1, entrenador2);
        this.modoJuego = modo;

        initGUI();
        mostrarLanzamientoMoneda();

        if ("Supervivencia".equals(modoJuego)) {
            areaLog.setText("");
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

    /**
     * Ajusta los componentes de un contenedor en función del tamaño de la ventana.
     * Este método escala uniformemente los componentes dentro del contenedor
     * para adaptarse al tamaño actual de la ventana, manteniendo una proporción
     * basada en un ancho y alto de referencia.
     *
     * @param container El contenedor cuyos componentes se ajustarán.
     * @param ventana   Las dimensiones actuales de la ventana.
     */
    private void ajustarComponentes(Container container, Dimension ventana) {
        int baseWidth = 1000;
        int baseHeight = 700;

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


    /**
     * Inicializa la interfaz gráfica de usuario (GUI) para el combate de POOBkemon.
     * Este método configura los paneles principales, los controles, y los elementos
     * visuales necesarios para la interacción del usuario durante la batalla.
     */
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
        panelConFondo.setBackground(new Color(124, 252, 0));
        panelConFondo.setOpaque(true); // Asegurarse de que el fondo sea visible

        // Imagen del Pokémon rival
        lblImagenPokemonRival = new JLabel();
        lblImagenPokemonRival.setPreferredSize(new Dimension(200, 200));
        lblImagenPokemonRival.setBounds(50, 100, 200, 200);
        lblImagenPokemonRival.setHorizontalAlignment(SwingConstants.CENTER);


        // Crear un JPanel para ajustar la posición del GIF
        JPanel panelAjuste = new JPanel(new BorderLayout());
        panelAjuste.setOpaque(false); // Hacer el panel transparente
        panelAjuste.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // Añadir el JLabel al panel de ajuste
        lblImagenPokemonRival.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        panelAjuste.add(lblImagenPokemonRival, BorderLayout.CENTER);

        // Añadir el panel de ajuste al panel con fondo
        panelConFondo.add(panelAjuste, BorderLayout.CENTER);

        // Añadir el panel con fondo al panel principal
        panelPokemonRival.add(panelInfoRival, BorderLayout.EAST);
        panelPokemonRival.add(panelConFondo, BorderLayout.CENTER);

        // Panel central: área de mensajes
        areaLog = new JTextArea(5, 40);
        areaLog.setEditable(false);
        areaLog.setBackground(new Color(90, 207, 113));
        areaLog.setOpaque(true);
        try {
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("main/resources/font/PressStart2P.ttf");
            if (fontStream == null) {
                throw new IOException("No se encontró el archivo de fuente: PressStart2P.ttf");
            }
            Font pressStartFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            pressStartFont = pressStartFont.deriveFont(Font.PLAIN, 12);
            areaLog.setFont(pressStartFont);
        } catch (FontFormatException | IOException e) {
            System.err.println("Error al cargar la fuente PressStart2P: " + e.getMessage());
        }
        JScrollPane scrollLog = new JScrollPane(areaLog);
        areaLog.append("¡Inicia la batalla!\n");

        // Panel inferior: Pokémon del jugador (ahora con imagen)
        panelPokemonJugador = new JPanel(new BorderLayout());
        panelPokemonJugador.setBorder(BorderFactory.createEmptyBorder());
        panelPokemonJugador.setOpaque(true);

        JPanel panelInfoJugador = new JPanel();
        panelInfoJugador.setLayout(new BoxLayout(panelInfoJugador, BoxLayout.Y_AXIS));
        panelInfoJugador.setBackground(new Color(90, 207, 113));



        lblNombreJugador = new JLabel("Tu Pokémon");
        lblNombreJugador.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        lblPSJugador = new JLabel("PS: 100/100");
        lblPSJugador.setFont(new Font("Press Start 2P", Font.BOLD, 16));

        lblPSJugador.setForeground(Color.BLACK);


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
        panelConFondo.setBackground(new Color(90, 207, 113));
        panelConFondo.setOpaque(true);

        // Imagen del Pokémon del jugador
        lblImagenPokemonJugador = new JLabel();
        lblImagenPokemonJugador.setPreferredSize(new Dimension(200, 200));
        lblImagenPokemonJugador.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear un JPanel para ajustar la posición del GIF
        JPanel panelAjusteJugador = new JPanel(new BorderLayout());
        panelAjusteJugador.setOpaque(false); // Hacer el panel transparente
        panelAjusteJugador.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 120));

        // Añadir el JLabel al panel de ajuste en la posición izquierda
        lblImagenPokemonJugador.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 120));
        panelAjusteJugador.add(lblImagenPokemonJugador, BorderLayout.CENTER);

        // Añadir el panel de ajuste al panel con fondo
        panelConFondoJugador.add(panelAjusteJugador, BorderLayout.CENTER);

        // Añadir el panel con fondo al panel principal
        panelPokemonJugador.add(panelInfoJugador, BorderLayout.WEST);
        panelPokemonJugador.add(panelConFondoJugador, BorderLayout.CENTER);

        panelConFondo.setBackground(new Color(90, 207, 113));
        panelConFondo.setOpaque(true);

        // Controles: movimientos, cambio de pokémon, items
        panelControles = new JPanel(new CardLayout());

        // Panel de movimientos
        panelMovimientos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelMovimientos.setBorder(BorderFactory.createTitledBorder("Movimientos"));

        for (int i = 0; i < btnMovimientos.length; i++) {
            final int index = i;
            btnMovimientos[i] = new JButton("Movimiento " + (i + 1));
            btnMovimientos[i].setBackground(new Color(245, 245, 220));
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
        btnVerMovimientos.setBackground(new Color(245, 245, 220));
        btnVerMovimientos.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelControles.getLayout();
            cl.show(panelControles, "movimientos");
        });

        JButton btnVerPokemones = new JButton("Pokémon");
        btnVerPokemones.setBackground(new Color(245, 245, 220));
        btnVerPokemones.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelControles.getLayout();
            cl.show(panelControles, "pokemones");
        });

        JButton btnVerItems = new JButton("Items");
        btnVerItems.setBackground(new Color(245, 245, 220));

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
        JPanel panelAcciones = new JPanel(new GridLayout(1, 3, 10, 0));

        btnGuardar = new JButton("Guardar Partida");
        btnGuardar.setBackground(new Color(245, 245, 220));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.addActionListener(e -> guardarPartida());
        panelAcciones.add(btnGuardar);

        btnCargar = new JButton("Cargar Partida");
        btnCargar.setBackground(new Color(245, 245, 220)); // Color hueso
        btnCargar.setForeground(Color.BLACK);
        btnCargar.addActionListener(e -> cargarPartida());
        panelAcciones.add(btnCargar);

        //botón Huir
        JButton btnHuir = new JButton("Huir");
        btnHuir.setBackground(new Color(245, 245, 220));
        btnHuir.setForeground(Color.BLACK);
        btnHuir.addActionListener(e -> {
            // Cerrar la ventana actual y abrir la ventana de configuración
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    PoobkemonConfigWindow configWindow = new PoobkemonConfigWindow();
                    configWindow.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        panelAcciones.add(btnHuir);

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

    /**
     * Muestra un panel específico dentro del contenedor central utilizando un `CardLayout`.
     * Este método permite cambiar entre diferentes vistas o paneles de acción
     * en la interfaz gráfica, según el identificador proporcionado.
     *
     * @param accion El nombre del panel que se desea mostrar. Este nombre debe coincidir
     * con el identificador registrado en el `CardLayout` del contenedor central.
     */

    private void mostrarPanelAccion(String accion) {
        CardLayout cl = (CardLayout) panelCentral.getLayout();
        cl.show(panelCentral, accion);
    }

    /**
     * Crea un panel que contiene los botones de los movimientos disponibles
     * para el Pokémon activo del jugador.
     *
     * El panel utiliza un diseño de cuadrícula (GridLayout) con 2 filas y 2 columnas,
     * y un espacio de 10 píxeles entre los componentes, tanto horizontal como verticalmente.
     *
     * @return Un `JPanel` configurado con los botones de los movimientos.
     */

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

    /**
     * Crea un panel que contiene los botones de los Pokémon disponibles
     * para el jugador. Cada botón representa un Pokémon del equipo del jugador.
     *
     * El panel utiliza un diseño de cuadrícula (GridLayout) con 3 filas y 2 columnas,
     * y un espacio de 10 píxeles entre los componentes, tanto horizontal como verticalmente.
     *
     * @return Un `JPanel` configurado con los botones de los Pokémon.
     */

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


    /**
     * Crea un panel que contiene los botones de los ítems disponibles
     * para el jugador. Cada botón representa un ítem del inventario del jugador.
     *
     * El panel utiliza un diseño de cuadrícula (GridLayout) con 2 filas y 2 columnas,
     * y un espacio de 10 píxeles entre los componentes, tanto horizontal como verticalmente.
     *
     * @return Un `JPanel` configurado con los botones de los ítems.
     */

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


    /**
     * Muestra un GIF animado correspondiente al Pokémon activo en un JLabel.
     * Este método busca el archivo GIF del Pokémon por su nombre, lo carga
     * y lo establece como el contenido del JLabel proporcionado.
     *
     * @param label         El JLabel donde se mostrará el GIF del Pokémon.
     * @param nombrePokemon El nombre del Pokémon cuyo GIF se desea mostrar.
     */

    private void mostrarGifPokemonActivo(JLabel label, String nombrePokemon) {
        try {
            String nombreArchivo = nombrePokemon.toLowerCase() + ".gif";
            ImageIcon gifPokemon = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("main/resources/images/" + nombreArchivo)));

            label.setIcon(gifPokemon);
            label.setText("");
        } catch (Exception e) {
            System.err.println("No se pudo cargar el GIF para " + nombrePokemon + ": " + e.getMessage());
            label.setIcon(null);
            label.setText("[ " + nombrePokemon + " ]");
        }
    }


    /**
     * Actualiza el Pokémon activo del entrenador que tiene el turno actual en la batalla.
     * Este método obtiene el entrenador activo de la batalla y actualiza la información
     * del Pokémon que está actualmente en combate.
     */

    private void actualizarPokemonActivo() {
        Entrenador entrenadorActivo = batalla.getTurnoActual();
        Pokemon pokemonActivo = entrenadorActivo.getActivo();
        if (pokemonActivo != null) {
            mostrarGifPokemonActivo(lblImagenPokemonJugador, pokemonActivo.getNombre());        }
    }

    /**
     * Inicia el temporizador para el turno actual.
     * Si ya existe un temporizador en ejecución, lo detiene antes de iniciar uno nuevo.
     */

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

    /**
     * Realiza un movimiento seleccionado por el jugador o la IA durante la batalla.
     * Este método ejecuta el movimiento correspondiente al índice proporcionado,
     * actualiza el estado de la batalla y verifica si hay un ganador.
     *
     * @param index El índice del movimiento a realizar (0 a 3).
     */

    private void realizarMovimiento(int index) {
        try {
            Entrenador turnoActual = batalla.getTurnoActual();

            String resultado = batalla.ejecutarMovimiento(index);
            areaLog.append(resultado + "\n");

            if (batalla.hayGanador()) {
                Entrenador ganador = batalla.getGanador();
                mostrarVentanaGanador(ganador);
                deshabilitarControles();
                timerTurno.stop();
                return;
            }

            verificarPokemonDeshabilitado(turnoActual);

            if (!batalla.getTurnoActual().esHumano()) {
                ejecutarTurnoIA();
            }

            actualizarInterfaz();
            iniciarTimerTurno();
        } catch (PoobkemonException e) {
            areaLog.append("Error: " + e.getMessage() + "\n");
        }
    }

    /**
     * Verifica si el Pokémon activo de un entrenador está debilitado.
     * Si el Pokémon está debilitado, se puede realizar una acción adicional
     * como buscar un reemplazo en el equipo del entrenador.
     *
     * @param entrenador El entrenador cuyo Pokémon activo será verificado.
     */

    private void verificarPokemonDeshabilitado(Entrenador entrenador) {
        Pokemon pokemonActual = entrenador.getActivo();
        if (pokemonActual.estaDebilitado()) {
            areaLog.append(pokemonActual.getNombre() + " ha quedado deshabilitado.\n");

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
                areaLog.append("¡" + entrenador.getNombre() + " no tiene más Pokémon disponibles!\n");
                if (entrenador == entrenador1) {
                    areaLog.append("¡" + entrenador2.getNombre() + " ha ganado el combate!\n");
                } else {
                    areaLog.append("¡" + entrenador1.getNombre() + " ha ganado el combate!\n");
                }
                mostrarVentanaGanador(entrenador);
                deshabilitarControles();
                timerTurno.stop();
            }
        }
    }

    /**
     * Ejecuta el turno de la IA (Inteligencia Artificial) durante la batalla.
     * Este método verifica si el entrenador actual es una instancia de `EntrenadorIA`.
     * Si es así, la IA decide una acción (movimiento o uso de ítem) y la ejecuta.
     *
     * Acciones posibles:
     * - Si la acción es un movimiento, se ejecuta el movimiento correspondiente.
     * - Si la acción es el uso de un ítem, se aplica el ítem al Pokémon activo.
     *
     * Después de ejecutar la acción, se actualiza la interfaz y se inicia el temporizador
     * para el siguiente turno.
     *
     * @throws PoobkemonException Si ocurre un error al ejecutar el movimiento o usar un ítem.
     */

    private void ejecutarTurnoIA() {
        try {
            Entrenador actual = batalla.getTurnoActual();
            if (!(actual instanceof EntrenadorIA)) {
                return;
            }
            EntrenadorIA ia = (EntrenadorIA) actual;
            int accion = ia.decidirAccion(batalla);
            if (accion >= 0) {
                // Movimiento
                String resultado = batalla.ejecutarMovimiento(accion);
                areaLog.append("[IA] " + actual.getNombre() + " usó movimiento: " + actual.getActivo().getMovimientos().get(accion).getNombre() + "\n");
                areaLog.append(resultado + "\n");
            } else if (accion <= -100 && accion > -200) {
                // Usar ítem
                int itemIndex = -100 - accion;
                actual.usarItem(itemIndex, actual.getActivo());
                Item item = actual.getItems().get(itemIndex);
                areaLog.append("[IA] " + actual.getNombre() + " usó ítem: " + item.getNombre() + " en " + actual.getActivo().getNombre() + "\n");
                batalla.cambiarTurno();
            } else if (accion <= -200) {
                // Cambiar Pokémon
                int pokeIndex = -200 - accion;
                actual.cambiarPokemon(pokeIndex);
                areaLog.append("[IA] " + actual.getNombre() + " cambió a " + actual.getActivo().getNombre() + "\n");
                batalla.cambiarTurno();
            }

            if (batalla.hayGanador()) {
                Entrenador ganador = batalla.getGanador();
                mostrarVentanaGanador(ganador);
                deshabilitarControles();
                if (timerTurno != null) timerTurno.stop();
                actualizarInterfaz();
                return;
            }

            // Verificar si el Pokémon de la IA quedó deshabilitado
            verificarPokemonDeshabilitado(actual);

            actualizarInterfaz();

            // Si ambos son IA, continuar automáticamente
            if (!batalla.getTurnoActual().esHumano() && entrenador1 instanceof EntrenadorIA && entrenador2 instanceof EntrenadorIA) {
                // Espera breve para que el usuario vea el log
                Timer autoTimer = new Timer(500, evt -> {
                    ((Timer) evt.getSource()).stop();
                    ejecutarTurnoIA();
                });
                autoTimer.setRepeats(false);
                autoTimer.start();
            } else if (!batalla.getTurnoActual().esHumano()) {
                // Si solo el rival es IA, iniciar timer normal
                iniciarTimerTurno();
            }
        } catch (PoobkemonException e) {
            areaLog.append("Error de IA: " + e.getMessage() + "\n");
            if (batalla.getTurnoActual().esHumano()) {
                actualizarInterfaz();
                iniciarTimerTurno();
            } else {
                ejecutarTurnoIA();
            }
        }
    }

    /**
     * Cambia el Pokémon activo del entrenador que tiene el turno actual.
     * Este método permite al entrenador seleccionar un nuevo Pokémon de su equipo
     * para que sea el Pokémon activo en la batalla.
     *
     * @param index El índice del Pokémon en el equipo del entrenador que se desea activar.
     * @throws PoobkemonException Si ocurre un error al intentar cambiar el Pokémon,
     *                             como seleccionar un Pokémon debilitado o un índice inválido.
     */

    private void cambiarPokemon(int index) {
        try {
            Entrenador turnoActual = batalla.getTurnoActual();

            turnoActual.cambiarPokemon(index);
            areaLog.append(turnoActual.getNombre() + " cambió a " + turnoActual.getActivo().getNombre() + "\n");

            batalla.cambiarTurno();

            if (!batalla.getTurnoActual().esHumano()) {
                ejecutarTurnoIA();
            }

            actualizarInterfaz();
            iniciarTimerTurno();
        } catch (PoobkemonException e) {
            areaLog.append("Error: " + e.getMessage() + "\n");
        }
    }

    /**
     * Usa un ítem del inventario del entrenador que tiene el turno actual.
     * Este método permite aplicar el efecto de un ítem al Pokémon activo del entrenador.
     *
     * @param index El índice del ítem en el inventario del entrenador que se desea usar.
     * @throws PoobkemonException Si ocurre un error al usar el ítem, como que no esté disponible
     *                            o que el índice sea inválido.
     */

    private void usarItem(int index) {
        try {
            Entrenador turnoActual = batalla.getTurnoActual();
            List<Item> items = turnoActual.getItems();

            if (index < items.size()) {
                turnoActual.usarItem(index, turnoActual.getActivo());
                Item item = items.get(index);
                areaLog.append(turnoActual.getNombre() + " usó " + item.getNombre() +
                              " en " + turnoActual.getActivo().getNombre() + "\n");

                batalla.cambiarTurno();

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

    /**
     * Muestra el lanzamiento de una moneda para decidir quién comienza el combate.
     * Durante el lanzamiento, todos los controles de la interfaz se deshabilitan.
     * El resultado del lanzamiento se mostrará en el área de logs.
     */

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

            // Si el primer turno es de una IA, que juegue automáticamente
            if (!batalla.getTurnoActual().esHumano()) {
                ejecutarTurnoIA();
            }
        });
    }

    /**
     * Deshabilita los controles de la interfaz gráfica.
     * Este método desactiva los botones de los paneles de opciones y de acción
     * (movimientos, cambio de Pokémon e ítems) si estos existen.
     *
     * Es útil para evitar que el usuario interactúe con la interfaz en momentos
     * donde no se permite realizar acciones, como durante el lanzamiento de moneda
     * o entre turnos.
     */

    private void deshabilitarControles() {
        if (panelOpciones != null) {
            panelOpciones.btnLucha.setEnabled(false);
            panelOpciones.btnMochila.setEnabled(false);
            panelOpciones.btnPokemon.setEnabled(false);
            panelOpciones.btnHuir.setEnabled(false);
        }
        deshabilitarBotonesEnPanel(panelMovimientos);
        deshabilitarBotonesEnPanel(panelPokemones);
        deshabilitarBotonesEnPanel(panelItems);
    }


    /**
     * Habilita los controles de la interfaz gráfica.
     * Este método activa los botones de los paneles de opciones y de acción
     * (movimientos, cambio de Pokémon e ítems) si estos existen.
     *
     * Es útil para permitir que el usuario interactúe con la interfaz
     * después de momentos en los que los controles estaban deshabilitados,
     * como al inicio de un turno o después de una acción específica.
     */

    private void habilitarControles() {
        if (panelOpciones != null) {
            panelOpciones.btnLucha.setEnabled(true);
            panelOpciones.btnMochila.setEnabled(true);
            panelOpciones.btnPokemon.setEnabled(true);
            panelOpciones.btnHuir.setEnabled(true);
        }
        habilitarBotonesEnPanel(panelMovimientos);
        habilitarBotonesEnPanel(panelPokemones);
        habilitarBotonesEnPanel(panelItems);
    }

    /**
     * Deshabilita todos los botones dentro de un panel dado.
     * Este método recorre los componentes del panel y, si encuentra botones,
     * los desactiva para evitar que el usuario interactúe con ellos.
     *
     * @param panel El panel cuyos botones se desean deshabilitar. Si el panel es nulo, no se realiza ninguna acción.
     */

    private void deshabilitarBotonesEnPanel(JPanel panel) {
        if (panel != null) {
            for (Component c : panel.getComponents()) {
                if (c instanceof JButton) {
                    c.setEnabled(false);
                }
            }
        }
    }

    /**
     * Habilita todos los botones dentro de un panel dado.
     * Este método recorre los componentes del panel y, si encuentra botones,
     * los activa para permitir la interacción del usuario.
     *
     * @param panel El panel cuyos botones se desean habilitar. Si el panel es nulo, no se realiza ninguna acción.
     */

    private void habilitarBotonesEnPanel(JPanel panel) {
        if (panel != null) {
            for (Component c : panel.getComponents()) {
                if (c instanceof JButton) {
                    c.setEnabled(true);
                }
            }
        }
    }

    /**
     * Actualiza la interfaz gráfica de usuario con la información más reciente
     * de los Pokémon del jugador y del rival. Este método también actualiza
     * los GIFs correspondientes y muestra los puntos de salud (PS) actuales
     * de cada Pokémon.
     */

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

    /**
     * Método principal de la aplicación.
     * Este método inicializa el menú principal del juego POOBkemon.
     * Se ejecuta en el hilo de eventos de Swing para garantizar
     * que la interfaz gráfica se configure correctamente.
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta aplicación).
     */

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

    /**
     * Guarda el estado actual de la partida en un archivo.
     * Este método abre un cuadro de diálogo para que el usuario seleccione
     * la ubicación y el nombre del archivo donde se guardará la partida.
     * El archivo se guarda con la extensión `.pkm`.
     *
     * Si ocurre un error durante el proceso de guardado, se muestra un mensaje
     * de error al usuario.
     */

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

    /**
     * Carga el estado de una partida previamente guardada desde un archivo.
     * Este método abre un cuadro de diálogo para que el usuario seleccione
     * el archivo de partida con extensión `.pkm` que desea cargar.
     *
     * Si el archivo es válido, se restaura el estado del juego, incluyendo
     * los entrenadores, la batalla y el turno actual.
     *
     * Si ocurre un error durante el proceso de carga, se muestra un mensaje
     * de error al usuario.
     */

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

    /**
     * Clase `BattleBackgroundPanel` que extiende `JPanel`.
     * Esta clase permite personalizar el fondo del panel con una imagen.
     * Es utilizada para mostrar un fondo gráfico en la interfaz de batalla.
     */

    class BattleBackgroundPanel extends JPanel {
    private Image backgroundImage;
    private JLabel pokemonLabel;

    /**
     * Constructor de la clase `BattleBackgroundPanel`.
     * Este constructor inicializa un panel con un fondo personalizado y una imagen de un Pokémon.
     *
     * @param pokemonImage La imagen del Pokémon que se mostrará en el panel.
     */
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


        pokemonLabel = new JLabel(pokemonImage);
        pokemonLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha
        pokemonLabel.setVerticalAlignment(SwingConstants.TOP); // Alinear en la parte superior

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

    /**
     * Muestra una ventana emergente con información del ganador de la batalla.
     * Este método crea un panel personalizado para mostrar el nombre del entrenador ganador
     * y cualquier otra información relevante sobre el resultado de la batalla.
     *
     * @param ganador El entrenador que ganó la batalla.
     */

    private void mostrarVentanaGanador(Entrenador ganador) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(350, 600));
        panel.setBackground(new Color(255, 223, 0)); // Fondo dorado

        Font fuentePersonalizada = new Font("Press Start 2P", Font.BOLD, 14);

        JLabel titulo = new JLabel("¡GANADOR DE LA BATALLA!");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(fuentePersonalizada.deriveFont(18f));
        titulo.setForeground(Color.BLACK); // Texto en negro
        panel.add(titulo);

        JLabel nombre = new JLabel(ganador.getNombre());
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        nombre.setFont(fuentePersonalizada.deriveFont(16f));
        nombre.setForeground(Color.BLACK); // Texto en negro
        panel.add(nombre);

        panel.add(Box.createVerticalStrut(10));
        JLabel subtitulo = new JLabel("Pokémon sobrevivientes:");
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setFont(fuentePersonalizada);
        subtitulo.setForeground(Color.BLACK); // Texto en negro
        panel.add(subtitulo);

        boolean haySobrevivientes = false;
        for (Pokemon p : ganador.getPokemones()) {
            if (!p.estaDebilitado()) {
                haySobrevivientes = true;
                JPanel pokePanel = new JPanel();
                pokePanel.setLayout(new BoxLayout(pokePanel, BoxLayout.Y_AXIS));
                pokePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                pokePanel.setOpaque(false);

                // Imagen centrada
                try {
                    String nombreArchivo = p.getNombre().toLowerCase() + ".gif";
                    ImageIcon icon = new ImageIcon(Objects.requireNonNull(
                            getClass().getClassLoader().getResource("main/resources/images/" + nombreArchivo)));
                    Image img = icon.getImage().getScaledInstance(96, 96, Image.SCALE_DEFAULT);
                    icon = new ImageIcon(img);
                    JLabel imgLabel = new JLabel(icon);
                    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    pokePanel.add(imgLabel);
                } catch (Exception e) {
                    JLabel imgLabel = new JLabel("[?]");
                    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    imgLabel.setFont(fuentePersonalizada);
                    pokePanel.add(imgLabel);
                }

                JLabel nombreLabel = new JLabel(p.getNombre());
                nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                nombreLabel.setFont(fuentePersonalizada.deriveFont(16f));
                nombreLabel.setForeground(Color.BLACK); // Texto en negro
                pokePanel.add(nombreLabel);

                panel.add(pokePanel);
                panel.add(Box.createVerticalStrut(10));
            }
        }

        if (!haySobrevivientes) {
            JLabel ninguno = new JLabel("Ningún Pokémon sobrevivió.");
            ninguno.setAlignmentX(Component.CENTER_ALIGNMENT);
            ninguno.setFont(fuentePersonalizada);
            ninguno.setForeground(Color.BLACK); // Texto en negro
            panel.add(ninguno);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(500, 900));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 5)); // Borde dorado
        JOptionPane.showMessageDialog(this, scrollPane, "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Clase `InfoPanelEsmeralda` que extiende `JPanel`.
     * Esta clase representa un panel de información con un diseño personalizado.
     * Es utilizada para mostrar información específica en la interfaz gráfica.
     */

    class InfoPanelEsmeralda extends JPanel {
    private JLabel lblNombre;
    private JLabel lblNivel;
    private JProgressBar barraVida;
    private JProgressBar barraExp;
    private JLabel lblPS;
    private boolean mostrarPS;

    /**
     * Constructor de la clase `InfoPanelEsmeralda`.
     * Este constructor inicializa un panel de información personalizado con detalles
     * sobre un Pokémon, como su nombre, nivel, puntos de salud (PS), experiencia, y
     * si se deben mostrar los PS en la interfaz.
     *
     * @param nombre    El nombre del Pokémon.
     * @param nivel     El nivel del Pokémon.
     * @param psActual  Los puntos de salud actuales del Pokémon.
     * @param psMax     Los puntos de salud máximos del Pokémon.
     * @param expActual La experiencia actual del Pokémon.
     * @param expMax    La experiencia máxima necesaria para subir de nivel.
     * @param mostrarPS Indica si se deben mostrar los puntos de salud en la interfaz.
     */
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


    /**
     * Actualiza la información mostrada en el panel con los nuevos datos proporcionados.
     * Este método permite cambiar el nombre, nivel, puntos de salud (PS), y experiencia
     * del Pokémon mostrado en el panel, actualizando los componentes gráficos correspondientes.
     *
     * @param nombre    El nuevo nombre del Pokémon.
     * @param nivel     El nuevo nivel del Pokémon.
     * @param psActual  Los puntos de salud actuales del Pokémon.
     * @param psMax     Los puntos de salud máximos del Pokémon.
     * @param expActual La experiencia actual del Pokémon.
     * @param expMax    La experiencia máxima necesaria para subir de nivel.
     */

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

    /**
     * Clase `OpcionesPanelEsmeralda` que extiende `JPanel`.
     * Esta clase representa un panel de opciones en la interfaz gráfica,
     * que incluye botones para realizar diferentes acciones durante el juego.
     */

    class OpcionesPanelEsmeralda extends JPanel {
    public JButton btnLucha = new JButton();
    public JButton btnMochila = new JButton();
    public JButton btnPokemon = new JButton();
    public JButton btnHuir = new JButton();

    public OpcionesPanelEsmeralda(String nombrePokemon) {}
    public void setNombrePokemon(String nombre) {}
    }
}











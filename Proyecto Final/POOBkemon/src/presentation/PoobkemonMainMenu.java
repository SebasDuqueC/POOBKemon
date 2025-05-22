package presentation;

import domain.GameState;
import domain.Entrenador;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Date;

/**
 * La clase `PoobkemonMainMenu` representa el menú principal de la aplicación POOBkemon.
 * Proporciona opciones para iniciar una nueva partida, cargar una partida existente,
 * acceder a las opciones (aún no implementadas) y salir del juego.
 */

public class PoobkemonMainMenu extends JFrame {

    private Font customFont;

    private JButton btnNuevaPartida, btnCargarPartida, btnOpciones, btnSalir;

    public PoobkemonMainMenu() throws IOException, FontFormatException {
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setUndecorated(true); // Sin bordes
        initGUI();

        try {
            // Cargar la fuente desde el archivo
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("main/resources/font/PressStart2P.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 14); // Fuente de respaldo
        }
    }

    /**
     * Inicializa la interfaz gráfica de usuario (GUI) del menú principal.
     * Este método configura un panel de fondo con una imagen personalizada y
     * agrega los botones principales del menú, como "Nueva Partida", "Cargar Partida",
     * "Opciones" y "Salir". También define el diseño y comportamiento visual de los botones,
     * incluyendo efectos de hover.
     *
     * El panel utiliza un diseño `GridBagLayout` para posicionar los botones
     * de manera centralizada y con espaciado uniforme.
     *
     * @throws IOException Si ocurre un error al cargar la imagen de fondo.
     */

    private void initGUI() {
        ImagePanel panel = new ImagePanel("main/resources/images/background.png");
        panel.setLayout(new GridBagLayout());

        btnNuevaPartida = new JButton("Nueva Partida");
        btnCargarPartida = new JButton("Cargar Partida");
        btnOpciones = new JButton("Opciones"); // Nuevo botón
        btnSalir = new JButton("Salir");

        Font botonFont = new Font("Press Start 2P", Font.BOLD, 14);

        for (JButton btn : new JButton[]{btnNuevaPartida, btnCargarPartida, btnOpciones, btnSalir}) {
            btn.setBackground(new Color(127, 255, 212));
            btn.setForeground(Color.BLACK);
            btn.setFont(botonFont);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);

            // Agregar efecto de hover
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(72, 145, 220));
                    btn.setContentAreaFilled(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(127, 255, 212)); // Color original
                    btn.setContentAreaFilled(false);
                }
            });
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(140, 0, 10, 0);
        gbc.anchor = GridBagConstraints.NORTH;

        panel.add(btnNuevaPartida, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy++;
        panel.add(btnCargarPartida, gbc);

        gbc.gridy++;
        panel.add(btnSalir, gbc);

        add(panel);

        btnNuevaPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirNuevaPartida();
            }
        });

        btnCargarPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Cargar Partida");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Partidas POOBkemon (*.pkm)", "pkm"));
                    
                    int userSelection = fileChooser.showOpenDialog(PoobkemonMainMenu.this);
                    
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToLoad = fileChooser.getSelectedFile();
                        
                        // Cargar desde archivo
                        try (ObjectInputStream in = new ObjectInputStream(
                                new FileInputStream(fileToLoad))) {
                            
                            GameState gameState = (GameState) in.readObject();
                            
                            // Crear GUI con el estado cargado
                            PoobkemonGUI gui = new PoobkemonGUI(
                                gameState.getEntrenador1(),
                                gameState.getEntrenador2(),
                                gameState.getModoJuego()
                            );
                            gui.setVisible(true);
                            dispose(); // Cerrar este menú
                        }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(PoobkemonMainMenu.this,
                        "No se pudo cargar la partida: " + ex.getMessage(),
                        "Error al cargar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Abre la ventana de configuración para iniciar una nueva partida.
     * Este método crea una instancia de `PoobkemonConfigWindow`, la hace visible
     * y cierra el menú principal actual.
     */

    private void abrirNuevaPartida() {
        // Aquí abrimos la GUI del juego
        PoobkemonConfigWindow config = new PoobkemonConfigWindow();
        config.setVisible(true);
        this.dispose(); // Cerramos el menú principal
    }
}
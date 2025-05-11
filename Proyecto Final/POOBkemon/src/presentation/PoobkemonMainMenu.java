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
                    btn.setBackground(new Color(72, 145, 220)); // Color al pasar el mouse
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
        gbc.insets = new Insets(140, 0, 10, 0); // <-- ajusta este valor para subir o bajar los botones
        gbc.anchor = GridBagConstraints.NORTH;

        panel.add(btnNuevaPartida, gbc);

        gbc.insets = new Insets(10, 0, 10, 0); // espaciado estándar entre botones
        gbc.gridy++;
        panel.add(btnCargarPartida, gbc);

        gbc.gridy++;
        panel.add(btnOpciones, gbc); // Agregar el botón "Opciones"

        gbc.gridy++;
        panel.add(btnSalir, gbc);

        add(panel);

        btnNuevaPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirNuevaPartida();
            }
        });

        // En el ActionListener del botón cargarPartida:
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

        btnOpciones.addActionListener(new ActionListener() { // Acción para el botón "Opciones"
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PoobkemonMainMenu.this,
                        "Opciones aún no implementadas",
                        "Opciones",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void abrirNuevaPartida() {
        // Aquí abrimos la GUI del juego
        PoobkemonConfigWindow config = new PoobkemonConfigWindow();
        config.setVisible(true);
        this.dispose(); // Cerramos el menú principal
    }
}
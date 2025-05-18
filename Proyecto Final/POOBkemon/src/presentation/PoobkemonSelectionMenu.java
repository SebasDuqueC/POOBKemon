package presentation;

import javax.swing.*;

import domain.Movimiento;
import domain.Pokemon;
import domain.PokemonLoader;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class PoobkemonSelectionMenu extends JPanel {

    private static final int MIN_SELECCION = 6; // Mínimo de Pokémon a seleccionar
    private List<String> pokemonesSeleccionados;
    private JPanel panelPokemones;
    private JButton btnConfirmar;
    private int maxSeleccion;
    private Map<String, List<Movimiento>> movimientosPorPokemon = new HashMap<>();
    private JPanel panelPrincipal;



    public PoobkemonSelectionMenu(String nombreEntrenador, List<String> pokemonesDisponibles, ActionListener confirmarListener) {
            // Inicializar variables
            this.pokemonesSeleccionados = new ArrayList<>();
            this.maxSeleccion = Integer.MAX_VALUE;

            // Configurar layout principal
            setLayout(new BorderLayout());


// Título con el nombre del entrenador
        JLabel lblTitulo = new JLabel("Selecciona tus Pokémon - " + nombreEntrenador, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Press Start 2P", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE); // Cambiar el color del texto a blanco
        lblTitulo.setOpaque(true); // Hacer que el fondo sea visible
        lblTitulo.setBackground(Color.BLACK); // Cambiar el fondo a negro
        add(lblTitulo, BorderLayout.NORTH);

            // Panel de botones para los Pokémon
            panelPokemones = new JPanel(new GridLayout(0, 2, 10, 10)); // Ajuste dinámico de filas
            panelPokemones.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Reducir márgenes
            panelPokemones.setBackground(Color.BLACK); // Establecer fondo negro


        for (String pokemon : pokemonesDisponibles) {
                JButton btnPokemon = new JButton();
                btnPokemon.setLayout(new BorderLayout()); // Usar BorderLayout en el botón
                btnPokemon.setPreferredSize(new Dimension(100, 80)); // Reducir tamaño del botón


            // Texto del Pokémon
            JLabel lblTexto = new JLabel(pokemon, SwingConstants.CENTER);
            lblTexto.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
            btnPokemon.add(lblTexto, BorderLayout.CENTER); // Añadir texto al centro
                if (pokemon.equals("Charizard")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon charizardGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/charizard.gif")));
                        JLabel gifLabel = new JLabel(charizardGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Charizard", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(255, 140, 0)); // Negro a naranja
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Snorlax")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon snorlaxGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/snorlax.gif")));
                        JLabel gifLabel = new JLabel(snorlaxGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Snorlax", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(173, 216, 230)); // Negro a azul claro
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Blastoise")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon blastoiseGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/blastoise.gif")));
                        JLabel gifLabel = new JLabel(blastoiseGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Blastoise", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(0, 0, 255)); // Negro a azul
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Metagross")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon metagrossGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/metagross.gif")));
                        JLabel gifLabel = new JLabel(metagrossGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Metagross", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(101, 67, 33)); // Negro a café
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Venusaur")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon venusaurGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/venusaur.gif")));
                        JLabel gifLabel = new JLabel(venusaurGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Venusaur", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(0, 128, 0)); // Negro a verde
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Donphan")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon donphanGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/donphan.gif")));
                        JLabel gifLabel = new JLabel(donphanGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Donphan", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(245, 245, 220)); // Negro a hueso
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }



                if (pokemon.equals("Gengar")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon gengarGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/gengar.gif")));
                        JLabel gifLabel = new JLabel(gengarGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Gengar", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(128, 0, 128)); // Negro a morado
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Machamp")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon machampGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/machamp.gif")));
                        JLabel gifLabel = new JLabel(machampGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Machamp", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(212, 175, 55)); // Negro a dorado
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }




                if (pokemon.equals("Dragonite")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon dragoniteGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/dragonite.gif")));
                        JLabel gifLabel = new JLabel(dragoniteGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Dragonite", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(255, 255, 0)); // Negro a amarillo
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Delibird")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon delibirdGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/delibird.gif")));
                        JLabel gifLabel = new JLabel(delibirdGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Delibird", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, Color.RED); // Negro a rojo
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Togetic")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon togeticGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/togetic.gif")));
                        JLabel gifLabel = new JLabel(togeticGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Togetic", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(255, 182, 193)); // Negro a rosado
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Raichu")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon raichuGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/raichu.gif")));
                        JLabel gifLabel = new JLabel(raichuGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Raichu", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(0, 191, 255)); // Negro a azul electrificante
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }



                if (pokemon.equals("Tyranitar")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon tyranitarGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/tyranitar.gif")));
                        JLabel gifLabel = new JLabel(tyranitarGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Tyranitar", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, Color.GRAY); // Negro a gris
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Gardevoir")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon gardevoirGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/gardevoir.gif")));
                        JLabel gifLabel = new JLabel(gardevoirGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Gardevoir", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        // Crear un JPanel personalizado para el fondo degradado
                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(200, 162, 200)); // Negro a lila
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        // Añadir el GIF y el texto al panel de fondo
                        backgroundPanel.add(gifLabel, BorderLayout.EAST);          // GIF a la derecha
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);      // Texto centrado

                        // Configurar el botón con el panel de fondo
                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        // Hacer invisible el fondo y los bordes del botón
                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }



            btnPokemon.addActionListener(e -> manejarSeleccionPokemon(pokemon, btnPokemon));
            panelPokemones.add(btnPokemon);



        }
            // Agregar barra de desplazamiento
            JScrollPane scrollPane = new JScrollPane(panelPokemones);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar borde del JScrollPane
            add(scrollPane, BorderLayout.CENTER);

        // Botón confirmar
        btnConfirmar = new JButton("Confirmar Selección");
        btnConfirmar.setFont(new Font("Press Start 2P", Font.BOLD, 14));
        btnConfirmar.setEnabled(false); // Deshabilitado inicialmente
        btnConfirmar.setBackground(new Color(50, 205, 50)); // Verde agradable (Lime Green)
        btnConfirmar.setForeground(Color.BLACK); // Letras negras
        btnConfirmar.addActionListener(e -> {
            if (pokemonesSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debes seleccionar al menos 1 Pokémon.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                confirmarListener.actionPerformed(null);
            }
        });
        add(btnConfirmar, BorderLayout.SOUTH);
    }

    public void manejarSeleccionPokemon(String pokemon, JButton btnPokemon) {
        // Si ya estaba seleccionado, simplemente lo deseleccionamos
        if (pokemonesSeleccionados.contains(pokemon)) {
            pokemonesSeleccionados.remove(pokemon);
            btnPokemon.setBorder(null); // Quitar el borde verde
            movimientosPorPokemon.remove(pokemon); // Eliminar movimientos asociados
        } else {
            // Si no está seleccionado y hay espacio, mostramos el selector de movimientos
            if (pokemonesSeleccionados.size() < maxSeleccion) {
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
                PokemonMoveSelector selector = new PokemonMoveSelector(parent, pokemon);
                selector.setVisible(true);

                if (selector.isConfirmed()) {
                    pokemonesSeleccionados.add(pokemon);
                    btnPokemon.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3)); // Borde verde
                    movimientosPorPokemon.put(pokemon, selector.getMovimientosSeleccionados());
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "No puedes seleccionar más de " + maxSeleccion + " Pokémon.",
                        "Límite alcanzado",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        btnConfirmar.setEnabled(pokemonesSeleccionados.size() >= MIN_SELECCION); // Habilitar si cumple el mínimo
    }


    public List<String> getPokemonesSeleccionados() {
        return pokemonesSeleccionados;
    }

    // Añadir este método para convertir nombres a objetos Pokémon
    public List<Pokemon> getPokemonesComoObjetos() {
        List<Pokemon> pokemones = new ArrayList<>();
        
        for (String nombre : pokemonesSeleccionados) {
            List<Movimiento> movimientosPersonalizados = movimientosPorPokemon.get(nombre);
            // Crear el pokémon con los movimientos seleccionados
            Pokemon pokemon = PokemonLoader.crearPokemonConMovimientos(nombre, movimientosPersonalizados);
            if (pokemon != null) {
                pokemones.add(pokemon);
            }
        }
        
        return pokemones;
    }
    // Añadir un método getter para recuperar los movimientos:
    public List<Movimiento> getMovimientosPorPokemon(String pokemon) {
        return movimientosPorPokemon.getOrDefault(pokemon, null);
    }

    private void configurarFondoNegro() {
        // Sobrescribir el método paintComponent del panel principal
        panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK); // Establecer el color negro
                g.fillRect(0, 0, getWidth(), getHeight()); // Rellenar todo el panel
            }
        };

        // Configurar transparencia de los componentes
        panelPrincipal.setOpaque(false);
        for (Component c : panelPrincipal.getComponents()) {
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
            }
        }
    }






}
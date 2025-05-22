package presentation;

import javax.swing.*;

import domain.Movimiento;
import domain.Pokemon;
import domain.PokemonLoader;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Clase que representa el menú de selección de Pokémon en la interfaz gráfica.
 * Permite a los usuarios seleccionar Pokémon de una lista disponible, personalizar sus movimientos
 * y confirmar la selección para continuar con el juego.
 */

public class PoobkemonSelectionMenu extends JPanel {

    private static final int MIN_SELECCION = 6; // Mínimo de Pokémon a seleccionar
    private List<String> pokemonesSeleccionados;
    private JPanel panelPokemones;
    private JButton btnConfirmar;
    private int maxSeleccion;
    private Map<String, List<Movimiento>> movimientosPorPokemon = new HashMap<>();
    private JPanel panelPrincipal;

    /**
     * Constructor de la clase `PoobkemonSelectionMenu`.
     * Este método inicializa el menú de selección de Pokémon, configurando la interfaz gráfica
     * para que el usuario pueda seleccionar Pokémon disponibles, personalizar sus movimientos
     * y confirmar la selección.
     *
     * @param nombreEntrenador     El nombre del entrenador que se mostrará en el título del menú.
     * @param pokemonesDisponibles Una lista de nombres de los Pokémon disponibles para seleccionar.
     * @param confirmarListener    Un `ActionListener` que se ejecutará al confirmar la selección.
     */

    public PoobkemonSelectionMenu(String nombreEntrenador, List<String> pokemonesDisponibles, ActionListener confirmarListener) {
        this.pokemonesSeleccionados = new ArrayList<>();
        this.maxSeleccion = Integer.MAX_VALUE;

        setLayout(new BorderLayout());


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
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(255, 140, 0));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

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
                        ImageIcon snorlaxGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/snorlax.gif")));
                        JLabel gifLabel = new JLabel(snorlaxGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Snorlax", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

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

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Blastoise")) {
                    try {
                        ImageIcon blastoiseGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/blastoise.gif")));
                        JLabel gifLabel = new JLabel(blastoiseGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Blastoise", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(0, 0, 255));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Metagross")) {
                    try {
                        ImageIcon metagrossGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/metagross.gif")));
                        JLabel gifLabel = new JLabel(metagrossGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Metagross", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(101, 67, 33));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Venusaur")) {
                    try {
                        ImageIcon venusaurGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/venusaur.gif")));
                        JLabel gifLabel = new JLabel(venusaurGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Venusaur", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(0, 128, 0));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Donphan")) {
                    try {
                        ImageIcon donphanGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/donphan.gif")));
                        JLabel gifLabel = new JLabel(donphanGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Donphan", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(245, 245, 220));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }



                if (pokemon.equals("Gengar")) {
                    try {
                        ImageIcon gengarGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/gengar.gif")));
                        JLabel gifLabel = new JLabel(gengarGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Gengar", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(128, 0, 128));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Machamp")) {
                    try {
                        ImageIcon machampGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/machamp.gif")));
                        JLabel gifLabel = new JLabel(machampGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Machamp", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

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

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }




                if (pokemon.equals("Dragonite")) {
                    try {
                        ImageIcon dragoniteGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/dragonite.gif")));
                        JLabel gifLabel = new JLabel(dragoniteGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Dragonite", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(255, 255, 0));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Delibird")) {
                    try {
                        ImageIcon delibirdGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/delibird.gif")));
                        JLabel gifLabel = new JLabel(delibirdGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Delibird", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, Color.RED);
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Togetic")) {
                    try {
                        ImageIcon togeticGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/togetic.gif")));
                        JLabel gifLabel = new JLabel(togeticGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Togetic", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(255, 182, 193));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Raichu")) {
                    try {
                        ImageIcon raichuGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/raichu.gif")));
                        JLabel gifLabel = new JLabel(raichuGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Raichu", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(0, 191, 255));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }



                if (pokemon.equals("Tyranitar")) {
                    try {
                        ImageIcon tyranitarGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/tyranitar.gif")));
                        JLabel gifLabel = new JLabel(tyranitarGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Tyranitar", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

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

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Gardevoir")) {
                    try {
                        ImageIcon gardevoirGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/gardevoir.gif")));
                        JLabel gifLabel = new JLabel(gardevoirGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                        JLabel textoLabel = new JLabel("Gardevoir", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
                        textoLabel.setForeground(Color.WHITE);

                        JPanel backgroundPanel = new JPanel() {
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                Graphics2D g2d = (Graphics2D) g;
                                int width = getWidth();
                                int height = getHeight();
                                GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, width, height, new Color(200, 162, 200));
                                g2d.setPaint(gradient);
                                g2d.fillRect(0, 0, width, height);
                            }
                        };
                        backgroundPanel.setLayout(new BorderLayout());

                        backgroundPanel.add(gifLabel, BorderLayout.EAST);
                        backgroundPanel.add(textoLabel, BorderLayout.CENTER);

                        btnPokemon.setLayout(new BorderLayout());
                        btnPokemon.add(backgroundPanel, BorderLayout.CENTER);

                        btnPokemon.setContentAreaFilled(false);
                        btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        btnPokemon.setBorderPainted(true);
                        btnPokemon.setFocusPainted(false);
                        btnPokemon.setOpaque(false);

                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar la imagen: " + e.getMessage());
                    }
                }



            btnPokemon.addActionListener(e -> manejarSeleccionPokemon(pokemon, btnPokemon));
            panelPokemones.add(btnPokemon);



        }
            JScrollPane scrollPane = new JScrollPane(panelPokemones);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar borde del JScrollPane
            add(scrollPane, BorderLayout.CENTER);

        btnConfirmar = new JButton("Confirmar Selección");
        btnConfirmar.setFont(new Font("Press Start 2P", Font.BOLD, 14));
        btnConfirmar.setEnabled(false);
        btnConfirmar.setBackground(new Color(50, 205, 50));
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

    /**
     * Maneja la selección o deselección de un Pokémon en el menú de selección.
     * Si el Pokémon ya estaba seleccionado, lo deselecciona y elimina sus movimientos asociados.
     * Si no estaba seleccionado, verifica si hay espacio disponible para seleccionarlo,
     * muestra un selector de movimientos y lo agrega a la lista de seleccionados si se confirma.
     *
     * @param pokemon    El nombre del Pokémon que se está seleccionando o deseleccionando.
     * @param btnPokemon El botón asociado al Pokémon que se está interactuando.
     */

    public void manejarSeleccionPokemon(String pokemon, JButton btnPokemon) {
        if (pokemonesSeleccionados.contains(pokemon)) {
            pokemonesSeleccionados.remove(pokemon);
            btnPokemon.setBorder(null);
            movimientosPorPokemon.remove(pokemon);
        } else {
            if (pokemonesSeleccionados.size() < maxSeleccion) {
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
                PokemonMoveSelector selector = new PokemonMoveSelector(parent, pokemon);
                selector.setVisible(true);

                if (selector.isConfirmed()) {
                    pokemonesSeleccionados.add(pokemon);
                    btnPokemon.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    movimientosPorPokemon.put(pokemon, selector.getMovimientosSeleccionados());
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "No puedes seleccionar más de " + maxSeleccion + " Pokémon.",
                        "Límite alcanzado",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        btnConfirmar.setEnabled(pokemonesSeleccionados.size() >= MIN_SELECCION);
    }

    /**
     * Devuelve la lista de nombres de los Pokémon seleccionados por el usuario.
     *
     * @return Una lista de cadenas que contiene los nombres de los Pokémon seleccionados.
     */

    public List<String> getPokemonesSeleccionados() {
        return pokemonesSeleccionados;
    }

    /**
     * Convierte la lista de nombres de Pokémon seleccionados en una lista de objetos `Pokemon`.
     * Para cada nombre de Pokémon seleccionado, se crea un objeto `Pokemon` con los movimientos
     * personalizados asociados a ese Pokémon, si están disponibles.
     *
     * @return Una lista de objetos `Pokemon` que representan los Pokémon seleccionados con sus movimientos personalizados.
     */

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

    /**
     * Obtiene la lista de movimientos asociados a un Pokémon específico.
     * Si no hay movimientos registrados para el Pokémon proporcionado, devuelve `null`.
     *
     * @param pokemon El nombre del Pokémon del cual se desean obtener los movimientos.
     * @return Una lista de objetos `Movimiento` asociados al Pokémon, o `null` si no hay movimientos registrados.
     */

    public List<Movimiento> getMovimientosPorPokemon(String pokemon) {
        return movimientosPorPokemon.getOrDefault(pokemon, null);
    }

    /**
     * Configura el fondo del panel principal con un color negro.
     * Este método sobrescribe el método `paintComponent` del panel principal
     * para rellenar todo el fondo con el color negro, asegurando que los
     * componentes sean transparentes si es necesario.
     */

    private void configurarFondoNegro() {
        panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK); // Establecer el color negro
                g.fillRect(0, 0, getWidth(), getHeight()); // Rellenar todo el panel
            }
        };

        panelPrincipal.setOpaque(false);
        for (Component c : panelPrincipal.getComponents()) {
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
            }
        }
    }






}
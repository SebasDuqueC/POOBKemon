package presentation;

import javax.swing.*;

import domain.Pokemon;
import domain.PokemonLoader;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PoobkemonSelectionMenu extends JPanel {

    private static final int MIN_SELECCION = 6; // Mínimo de Pokémon a seleccionar
    private List<String> pokemonesSeleccionados;
    private JPanel panelPokemones;
    private JButton btnConfirmar;
    private int maxSeleccion;


        public PoobkemonSelectionMenu(String nombreEntrenador, List<String> pokemonesDisponibles, ActionListener confirmarListener) {
            // Inicializar variables
            this.pokemonesSeleccionados = new ArrayList<>();
            this.maxSeleccion = Integer.MAX_VALUE;

            // Configurar layout principal
            setLayout(new BorderLayout());

            // Título con el nombre del entrenador
            JLabel lblTitulo = new JLabel("Selecciona tus Pokémon - " + nombreEntrenador, SwingConstants.CENTER);
            lblTitulo.setFont(new Font("Press Start 2P", Font.BOLD, 18));
            add(lblTitulo, BorderLayout.NORTH);

// Panel de botones para los Pokémon
            panelPokemones = new JPanel(new GridLayout(0, 2, 10, 10)); // Ajuste dinámico de filas
            panelPokemones.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Reducir márgenes

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

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(charizardGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Charizard", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Snorlax")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon snorlaxGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/snorlax.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(snorlaxGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Snorlax", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Blastoise")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon blastoiseGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/blastoise.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(blastoiseGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Blastoise", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Metagross")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon metagrossGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/metagross.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(metagrossGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Metagross", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Venusaur")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon venusaurGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/venuasur.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(venusaurGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Venusaur", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }
                if (pokemon.equals("Donphan")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon donphanGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/donphan.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(donphanGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Donphan", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Gengar")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon gengarGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/gengar.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(gengarGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Gengar", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Machamp")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon machampGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/machamp.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(machampGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Machamp", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }



                if (pokemon.equals("Dragonite")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon dragoniteGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/dragonite.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(dragoniteGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Dragonite", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Delibird")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon delibirdGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/delibird.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(delibirdGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Delibird", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Togetic")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon togeticGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/togetic.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(togeticGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Togetic", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }

                if (pokemon.equals("Raichu")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon raichuGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/raichu.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(raichuGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Raichu", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Tyranitar")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon tyranitarGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/tyranitar.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(tyranitarGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Tyranitar", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
                    }
                }


                if (pokemon.equals("Gardevoir")) {
                    try {
                        // Cargar el GIF como ImageIcon
                        ImageIcon gardevoirGif = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("main/resources/images/gardevoir.gif")));

                        // Crear un JLabel para contener el GIF
                        JLabel gifLabel = new JLabel(gardevoirGif);
                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                        // Crear un JLabel para el texto
                        JLabel textoLabel = new JLabel("Gardevoir", SwingConstants.CENTER);
                        textoLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                        // Añadir el texto a la izquierda y el GIF a la derecha
                        btnPokemon.add(gifLabel, BorderLayout.EAST);

                        // Actualizar el botón
                        btnPokemon.revalidate();
                        btnPokemon.repaint();
                    } catch (NullPointerException e) {
                        System.err.println("No se pudo cargar el GIF: " + e.getMessage());
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
        if (pokemonesSeleccionados.contains(pokemon)) {
            pokemonesSeleccionados.remove(pokemon);
            btnPokemon.setBackground(null); // Restaurar color original
        } else {
            if (pokemonesSeleccionados.size() < maxSeleccion) {
                pokemonesSeleccionados.add(pokemon);
                btnPokemon.setBackground(Color.GREEN); // Indicar selección
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
        List<Pokemon> todosLosPokemones = PokemonLoader.obtenerPokemonesDisponibles();
        
        for (String nombre : pokemonesSeleccionados) {
            for (Pokemon p : todosLosPokemones) {
                if (p.getNombre().equals(nombre)) {
                    pokemones.add(p);
                    break;
                }
            }
        }
        
        return pokemones;
}
}
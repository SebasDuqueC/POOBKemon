package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSelectionMenu extends JPanel {

    private String nombreEntrenador;
    private List<Item> itemsSeleccionados;
    private JButton btnConfirmar;
    private Map<String, JSpinner> spinnersPorItem;
    private final int MAX_ITEMS = 8;
    private JLabel lblItemsRestantes;

    public ItemSelectionMenu(String nombreEntrenador, ActionListener confirmarListener) {
        this.nombreEntrenador = nombreEntrenador;
        this.itemsSeleccionados = new ArrayList<>();
        this.spinnersPorItem = new HashMap<>();
        
        setLayout(new BorderLayout());

        // Panel de items con degradado
        JPanel panelItems = new GradientPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
        panelItems.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.WHITE); // Fondo blanco
        panelTitulo.setOpaque(true); // Asegurarse de que el fondo sea visible
        JLabel lblTitulo = new JLabel("Selecciona tus Items - " + nombreEntrenador, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        
        // Contador de items
        JPanel panelContador = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelContador.setBackground(Color.WHITE); // Fondo blanco
        lblItemsRestantes = new JLabel("Items restantes: " + MAX_ITEMS);
        lblItemsRestantes.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
        panelContador.add(lblItemsRestantes);
        panelItems.add(panelContador);
        
        // Agregar items
        agregarItem(panelItems, "Poción", "Recupera 20 PS", TipoItem.POCION);
        agregarItem(panelItems, "Super Poción", "Recupera 50 PS", TipoItem.SUPERPOCION);
        agregarItem(panelItems, "Hiper Poción", "Recupera 200 PS", TipoItem.HYPERPOCION);
        agregarItem(panelItems, "Revivir", "Revive un Pokémon debilitado", TipoItem.REVIVIR);
        
        // Agregar scroll
        JScrollPane scrollPane = new JScrollPane(panelItems);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        // Botón confirmar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(135, 206, 250)); // Fondo azul claro
        panelBotones.setOpaque(true); // Asegurarse de que el fondo sea visible
        btnConfirmar = new JButton("Confirmar Selección");
        btnConfirmar.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        btnConfirmar.addActionListener(e -> {
            generarListaItems();
            confirmarListener.actionPerformed(e);
        });
        panelBotones.add(btnConfirmar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarItem(JPanel panel, String nombre, String descripcion, TipoItem tipo) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false); // Hacer transparente el panel del ítem
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Panel info
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false); // Hacer transparente el panel de información
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Press Start 2P", Font.BOLD, 14));
        lblNombre.setForeground(Color.BLACK);

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Press Start 2P", Font.ITALIC, 12));
        lblDescripcion.setForeground(Color.BLACK);

        infoPanel.add(lblNombre);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(lblDescripcion);

        // Panel spinner
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        spinnerPanel.setOpaque(false); // Hacer transparente el panel del spinner
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, MAX_ITEMS, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(new Dimension(80, 40)); // Aumentar tamaño del spinner

        spinner.addChangeListener(e -> actualizarContadorItems());

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
        lblCantidad.setForeground(Color.BLACK);
        spinnerPanel.add(lblCantidad);
        spinnerPanel.add(spinner);

        spinnersPorItem.put(nombre + ":" + tipo.toString(), spinner);

        itemPanel.add(infoPanel, BorderLayout.WEST);
        itemPanel.add(spinnerPanel, BorderLayout.EAST);

        panel.add(itemPanel);
    }

    private void actualizarContadorItems() {
        int itemsSeleccionados = 0;
        for (JSpinner spinner : spinnersPorItem.values()) {
            itemsSeleccionados += (int) spinner.getValue();
        }
        
        int itemsRestantes = MAX_ITEMS - itemsSeleccionados;
        lblItemsRestantes.setText("Items restantes: " + itemsRestantes);
        
        if (itemsRestantes < 0) {
            for (JSpinner spinner : spinnersPorItem.values()) {
                if (spinner.isFocusOwner()) {
                    spinner.setValue((int)spinner.getValue() - 1);
                    break;
                }
            }
        }
    }

    private void generarListaItems() {
        itemsSeleccionados.clear();
        
        for (Map.Entry<String, JSpinner> entry : spinnersPorItem.entrySet()) {
            int cantidad = (int) entry.getValue().getValue();
            if (cantidad > 0) {
                String[] info = entry.getKey().split(":");
                String nombre = info[0];
                TipoItem tipo = TipoItem.valueOf(info[1]);
                
                Item item = new Item(nombre, tipo, cantidad);
                itemsSeleccionados.add(item);
            }
        }
    }

    public List<Item> getItemsSeleccionados() {
        return itemsSeleccionados;
    }

    // Clase personalizada para el panel con degradado
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            // Crear un degradado de negro a verde
            GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, 0, height, new Color(135, 206, 250));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
        }
    }
}
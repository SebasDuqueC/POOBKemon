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
        
        // Título
        JLabel lblTitulo = new JLabel("Selecciona tus Items - " + nombreEntrenador, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de items
        JPanel panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
        panelItems.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Contador de items
        JPanel panelContador = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblItemsRestantes = new JLabel("Items restantes: " + MAX_ITEMS);
        lblItemsRestantes.setFont(new Font("Arial", Font.PLAIN, 14));
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
        btnConfirmar = new JButton("Confirmar Selección");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
        btnConfirmar.addActionListener(e -> {
            generarListaItems();
            confirmarListener.actionPerformed(e);
        });
        panelBotones.add(btnConfirmar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarItem(JPanel panel, String nombre, String descripcion, TipoItem tipo) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Panel info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Arial", Font.ITALIC, 12));
        
        infoPanel.add(lblNombre);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(lblDescripcion);
        
        // Panel spinner
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, MAX_ITEMS, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(new Dimension(60, 30));
        
        spinner.addChangeListener(e -> actualizarContadorItems());
        
        spinnerPanel.add(new JLabel("Cantidad: "));
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
}
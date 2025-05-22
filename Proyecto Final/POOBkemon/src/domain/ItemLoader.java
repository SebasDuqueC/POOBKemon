package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de cargar los items desde un archivo
 */
public class ItemLoader {
    
    private static final String RUTA_ITEMS = System.getProperty("user.dir") + "/resources/items.txt";
    private static Map<String, Item> itemsPorNombre = new HashMap<>();
    
    /**
     * Carga todos los items disponibles desde el archivo items.txt
     * @return Lista de items con cantidad 1
     */
    public static List<Item> cargarItems() {
        return cargarItems(RUTA_ITEMS);
    }
    
    /**
     * Carga todos los items disponibles desde un archivo específico
     * @param rutaArchivo Ruta al archivo items.txt
     * @return Lista de items con cantidad 1
     */
    public static List<Item> cargarItems(String rutaArchivo) {
        List<Item> items = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("Proyecto Final/resources/items.txt"))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                // Saltarse la primera línea (encabezados)
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    String nombre = datos[0];
                    String tipoStr = datos[1];
                    String descripcion = datos[2];
                    int potencia = 0;
                    
                    if (datos.length > 3) {
                        try {
                            potencia = Integer.parseInt(datos[3]);
                        } catch (NumberFormatException e) {
                            System.err.println("Valor de potencia inválido para item " + nombre);
                        }
                    }
                    
                    try {
                        TipoItem tipo = TipoItem.valueOf(tipoStr);
                        Item item = new Item(nombre, tipo, 1, descripcion, potencia);
                        items.add(item);
                        itemsPorNombre.put(nombre, item);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Tipo de item inválido: " + tipoStr);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de items: " + e.getMessage());
        }
        
        return items;
    }
    
    /**
     * Busca un item por su nombre
     * @param nombre Nombre del item a buscar
     * @return Item encontrado o null si no existe
     */
    public static Item buscarItemPorNombre(String nombre) {
        // Si el mapa está vacío, cargar los items
        if (itemsPorNombre.isEmpty()) {
            cargarItems();
        }
        
        // Devolver una copia del item con cantidad 1
        Item original = itemsPorNombre.get(nombre);
        if (original != null) {
            return new Item(original.getNombre(), original.getTipo(), 1, original.getDescripcion(), original.getPotencia());
        }
        return null;
    }
    
    /**
     * Obtiene una lista de los nombres de todos los items disponibles
     * @return Lista de nombres de items
     */
    public static List<String> obtenerNombresDeItems() {
        if (itemsPorNombre.isEmpty()) {
            cargarItems();
        }
        return new ArrayList<>(itemsPorNombre.keySet());
    }
    
    /**
     * Crea un item con cantidad específica
     * @param nombre Nombre del item
     * @param cantidad Cantidad del item
     * @return Item con la cantidad especificada
     */
    public static Item crearItemConCantidad(String nombre, int cantidad) {
        Item base = buscarItemPorNombre(nombre);
        if (base != null) {
            return new Item(base.getNombre(), base.getTipo(), cantidad, base.getDescripcion(), base.getPotencia());
        }
        return null;
    }
}
package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class PokemonLoader {
    
    public static List<Pokemon> cargarPokemons(String rutaArchivo) {
        List<Pokemon> pokemons = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            // Saltar la primera línea (encabezado)
            br.readLine();
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String nombre = datos[0];
                    String tipo = datos[1];
                    int vida = Integer.parseInt(datos[2]);
                    int ataque = Integer.parseInt(datos[3]);
                    int defensa = Integer.parseInt(datos[4]);
                    
                    // Convertir tipo a formato de nombre de clase (primera letra mayúscula, resto minúsculas)
                    String nombreClase = tipo.charAt(0) + tipo.substring(1).toLowerCase();
                    
                    try {
                        // Usar reflexión para instanciar la clase correspondiente
                        Class<?> clasePokemont = Class.forName("domain." + nombreClase);
                        Constructor<?> constructor = clasePokemont.getConstructor(String.class, int.class, int.class, int.class);
                        Pokemon pokemon = (Pokemon) constructor.newInstance(nombre, vida, ataque, defensa);
                        pokemons.add(pokemon);
                    } catch (Exception e) {
                        System.err.println("Error al crear pokémon de tipo " + tipo + ": " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        
        return pokemons;
    }
    
    public static List<Pokemon> obtenerPokemonesDisponibles() {
    // Obtener directorio actual
    File currentDir = new File(System.getProperty("user.dir"));
    System.out.println("Directorio actual: " + currentDir.getAbsolutePath());
    
    // Buscar en diferentes ubicaciones posibles
    String[] posiblesRutas = {
        "resources/pokemons.txt",
        "POOBkemon/resources/pokemons.txt",
        "/home/sebastian/POOBKemon/Proyecto Final/resources/pokemons.txt"
    };
    
    for (String ruta : posiblesRutas) {
        File archivo = new File(ruta);
        System.out.println("Intentando cargar desde: " + archivo.getAbsolutePath());
        if (archivo.exists()) {
            System.out.println("¡Archivo encontrado en: " + archivo.getAbsolutePath());
            return cargarPokemons(ruta);
        }
    }
    
    System.err.println("No se encontró el archivo pokemons.txt en ninguna ubicación conocida");
    return new ArrayList<>(); // Lista vacía si no se encuentra
}
    
    // Método de ejemplo para probar
    public static void main(String[] args) {
        // Actualizado para usar la carpeta resources
        List<Pokemon> pokemons = cargarPokemons("POOBkemon/resources/pokemons.txt");
        for (Pokemon p : pokemons) {
            System.out.println(p.getNombre() + " (" + p.getClass().getSimpleName() + ")");
            System.out.println("Vida: " + p.getVida());
            System.out.println("Ataque: " + p.getAtaque());
            System.out.println("Defensa: " + p.getDefensa());
            System.out.println(p.mover());
            System.out.println();
        }
    }

    public static Pokemon buscarPokemonPorNombre(String nombre) {
        List<Pokemon> pokemons = obtenerPokemonesDisponibles();
        for (Pokemon p : pokemons) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null; // O lanzar una excepción
    }

    public static List<String> obtenerNombresDePokemones() {
        List<Pokemon> pokemons = obtenerPokemonesDisponibles();
        List<String> nombres = new ArrayList<>();
        for (Pokemon p : pokemons) {
            nombres.add(p.getNombre());
        }
        return nombres;
    }
}
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
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                // Saltarse la primera línea (encabezados)
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                
                String[] datos = linea.split(",");
                if (datos.length == 8) { // Verificar que tenga los 8 campos
                    String nombre = datos[0];
                    String tipoStr = datos[1];
                    int ps = Integer.parseInt(datos[2]);
                    int ataque = Integer.parseInt(datos[3]);
                    int defensa = Integer.parseInt(datos[4]);
                    int velocidad = Integer.parseInt(datos[5]);
                    int ataqueEspecial = Integer.parseInt(datos[6]);
                    int defensaEspecial = Integer.parseInt(datos[7]);
                    
                    // Mapear el tipo a la clase correspondiente
                    Tipo tipo = Tipo.valueOf(tipoStr);
                    String nombreClase = tipo.name().substring(0, 1) + tipo.name().substring(1).toLowerCase();
                    
                    try {
                        // Usar reflexión para instanciar la clase correspondiente con los nuevos parámetros
                        Class<?> clasePokemon = Class.forName("domain." + nombreClase);
                        Constructor<?> constructor = clasePokemon.getConstructor(String.class, int.class, int.class, int.class, int.class, int.class, int.class);
                        Pokemon pokemon = (Pokemon) constructor.newInstance(nombre, ps, ataque, defensa, velocidad, ataqueEspecial, defensaEspecial);
                        pokemons.add(pokemon);
                    } catch (Exception e) {
                        System.err.println("Error al crear pokémon de tipo " + tipoStr + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        
        return pokemons;
    }

    public static List<Pokemon> obtenerPokemonesDisponibles() {
        // Ruta fija
        String ruta = "Proyecto Final/resources/pokemons.txt";
        File archivo = new File(ruta);

        if (archivo.exists()) {
            System.out.println("¡Archivo encontrado en: " +  ruta + "!");
            return cargarPokemons(ruta);
        }

        System.err.println("No se encontró el archivo pokemons.txt en la ubicación: " + ruta);
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

    public static Pokemon crearPokemonConMovimientos(String nombre, List<Movimiento> movimientosPersonalizados) {
        // Buscar el pokemon base por nombre
        Pokemon pokemon = buscarPokemonPorNombre(nombre);
        
        if (pokemon == null) {
            System.err.println("No se encontró el Pokémon: " + nombre);
            return null;
        }
        
        // Limpiar los movimientos actuales y agregar los nuevos
        pokemon.getMovimientos().clear();
        
        // Verificar que no tenemos más de 4 movimientos
        int movimientosAñadir = Math.min(movimientosPersonalizados.size(), 4);
        
        for (int i = 0; i < movimientosAñadir; i++) {
            pokemon.getMovimientos().add(movimientosPersonalizados.get(i));
        }
        
        // Si no tenemos 4 movimientos, completar con movimientos genéricos del tipo
        if (movimientosAñadir < 4) {
            Tipo tipoPokemon = Tipo.valueOf(pokemon.getClass().getSimpleName().toUpperCase());
            Movimiento[] movimientosAdicionales = MovimientoFactory.obtenerCuatroMovimientos(tipoPokemon);
            
            for (int i = movimientosAñadir; i < 4; i++) {
                // Evitar duplicados verificando si el movimiento ya existe
                boolean movimientoExistente = false;
                for (Movimiento m : pokemon.getMovimientos()) {
                    if (m.getNombre().equals(movimientosAdicionales[i % movimientosAdicionales.length].getNombre())) {
                        movimientoExistente = true;
                        break;
                    }
                }
                
                if (!movimientoExistente) {
                    pokemon.getMovimientos().add(movimientosAdicionales[i % movimientosAdicionales.length]);
                    if (pokemon.getMovimientos().size() >= 4) break;
                }
            }
        }
        
        return pokemon;
    }
    
}
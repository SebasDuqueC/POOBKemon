package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de cargar y gestionar los datos de los Pokémon desde un archivo.
 *
 * Esta clase proporciona métodos para leer un archivo de texto que contiene información
 * sobre los Pokémon, instanciar objetos de tipo `Pokemon` basados en los datos leídos,
 * y realizar operaciones relacionadas, como buscar Pokémon por nombre o listar los nombres
 * de los Pokémon disponibles.
 *
 * También incluye métodos auxiliares para manejar rutas de archivos y probar la funcionalidad
 * de carga de Pokémon.
 */
public class PokemonLoader {

    /**
     * Obtiene la ruta del archivo que contiene los datos de los Pokémon.
     *
     * Este método recorre las rutas predefinidas en el arreglo `RUTAS_POKEMONS` y verifica
     * si el archivo existe en alguna de ellas. Si encuentra el archivo, devuelve la ruta
     * correspondiente. En caso contrario, imprime un mensaje de error y devuelve `null`.
     *
     * @return La ruta del archivo `pokemons.txt` si se encuentra, o `null` si no se encuentra.
     */
    private static final String[] RUTAS_POKEMONS = {
        "Proyecto Final/resources/pokemons.txt"
    };

    /**
     * Busca y obtiene la ruta del archivo que contiene los datos de los Pokémon.
     *
     * Este método recorre las rutas predefinidas en el arreglo `RUTAS_POKEMONS` y verifica
     * si el archivo existe en alguna de ellas. Si encuentra el archivo, devuelve la ruta
     * correspondiente. En caso contrario, imprime un mensaje de error y devuelve `null`.
     *
     * @return La ruta del archivo `pokemons.txt` si se encuentra, o `null` si no se encuentra.
     */
    private static String obtenerRutaPokemons() {
        for (String ruta : RUTAS_POKEMONS) {
            File archivo = new File(ruta);
            if (archivo.exists()) {
                System.out.println("¡Archivo encontrado en: " + ruta + "!");
                return ruta;
            }
        }
        System.err.println("No se encontró el archivo pokemons.txt en las ubicaciones conocidas.");
        return null;
    }

    /**
     * Carga una lista de Pokémon desde un archivo de texto.
     *
     * Este método lee un archivo de texto que contiene información sobre los Pokémon,
     * crea instancias de objetos `Pokemon` basados en los datos leídos y los agrega a una lista.
     * Cada línea del archivo debe contener los datos de un Pokémon separados por comas.
     *
     * @param rutaArchivo La ruta del archivo que contiene los datos de los Pokémon.
     * @return Una lista de objetos `Pokemon` creados a partir de los datos del archivo.
     */

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

    /**
     * Obtiene una lista de todos los Pokémon disponibles.
     *
     * Este método busca el archivo que contiene los datos de los Pokémon utilizando el método
     * `obtenerRutaPokemons`. Si el archivo es encontrado, carga los Pokémon desde el archivo
     * y devuelve una lista de objetos `Pokemon`. Si no se encuentra el archivo, devuelve una
     * lista vacía.
     *
     * @return Una lista de objetos `Pokemon` disponibles, o una lista vacía si no se encuentra el archivo.
     */
    public static List<Pokemon> obtenerPokemonesDisponibles() {
        String ruta = obtenerRutaPokemons();
        if (ruta != null) {
            return cargarPokemons(ruta);
        }
        return new ArrayList<>(); // Lista vacía si no se encuentra
    }


    /**
     * Método principal para probar la funcionalidad de carga de Pokémon.
     *
     * Este método busca el archivo que contiene los datos de los Pokémon utilizando el método
     * `obtenerRutaPokemons`. Si el archivo es encontrado, carga los Pokémon desde el archivo
     * y muestra información detallada de cada uno, incluyendo su nombre, clase, estadísticas
     * y movimiento característico.
     *
     * Si no se encuentra el archivo, imprime un mensaje de error y finaliza la ejecución.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este método).
     */
    public static void main(String[] args) {
        String ruta = obtenerRutaPokemons();
        if (ruta == null) {
            System.err.println("No se puede ejecutar la prueba porque no se encontró el archivo pokemons.txt");
            return;
        }
        List<Pokemon> pokemons = cargarPokemons(ruta);
        for (Pokemon p : pokemons) {
            System.out.println(p.getNombre() + " (" + p.getClass().getSimpleName() + ")");
            System.out.println("Vida: " + p.getVida());
            System.out.println("Ataque: " + p.getAtaque());
            System.out.println("Defensa: " + p.getDefensa());
            System.out.println(p.mover());
            System.out.println();
        }
    }


    /**
     * Busca un Pokémon por su nombre.
     *
     * Este método recorre la lista de Pokémon disponibles y devuelve el Pokémon
     * cuyo nombre coincide con el proporcionado. Si no se encuentra ningún Pokémon
     * con el nombre especificado, devuelve `null`.
     *
     * @param nombre El nombre del Pokémon a buscar.
     * @return El objeto `Pokemon` correspondiente al nombre proporcionado, o `null` si no se encuentra.
     */
    public static Pokemon buscarPokemonPorNombre(String nombre) {
        List<Pokemon> pokemons = obtenerPokemonesDisponibles();
        for (Pokemon p : pokemons) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null; // O lanzar una excepción
    }


    /**
     * Obtiene una lista con los nombres de todos los Pokémon disponibles.
     *
     * Este método utiliza el método `obtenerPokemonesDisponibles` para cargar
     * la lista de Pokémon y extrae únicamente los nombres de cada uno de ellos.
     * Si no hay Pokémon disponibles, devuelve una lista vacía.
     *
     * @return Una lista de cadenas que representan los nombres de los Pokémon disponibles.
     */
    public static List<String> obtenerNombresDePokemones() {
        List<Pokemon> pokemons = obtenerPokemonesDisponibles();
        List<String> nombres = new ArrayList<>();
        for (Pokemon p : pokemons) {
            nombres.add(p.getNombre());
        }
        return nombres;
    }

    /**
     * Crea un Pokémon con una lista personalizada de movimientos.
     *
     * Este método busca un Pokémon base por su nombre, limpia su lista de movimientos
     * actual y la reemplaza con una lista personalizada de movimientos proporcionada.
     * Si la lista personalizada tiene menos de 4 movimientos, se completará con movimientos
     * genéricos del tipo del Pokémon hasta alcanzar un máximo de 4 movimientos.
     *
     * @param nombre El nombre del Pokémon base a buscar.
     * @param movimientosPersonalizados Una lista de movimientos personalizados para asignar al Pokémon.
     * @return El objeto `Pokemon` con los movimientos personalizados asignados, o `null` si no se encuentra el Pokémon.
     */
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
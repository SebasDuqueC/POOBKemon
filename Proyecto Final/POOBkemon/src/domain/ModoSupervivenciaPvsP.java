package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import exceptions.PoobkemonException;

/**
 * Representa el modo de juego "Supervivencia PvP" (Jugador contra Jugador).
 * En este modo, dos entrenadores compiten utilizando equipos de Pokémon generados aleatoriamente.
 * No se permite el uso de ítems durante las batallas.
 */
public class ModoSupervivenciaPvsP {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private static final Random random = new Random();

    /**
     * Constructor para el modo de juego "Supervivencia PvP".
     * Este constructor inicializa a los dos entrenadores con equipos de Pokémon generados aleatoriamente
     * y sin ítems, ya que no se permite su uso en este modo.
     *
     * @param nombre1 El nombre del primer entrenador.
     * @param nombre2 El nombre del segundo entrenador.
     * @throws PoobkemonException Si ocurre un error al generar los Pokémon o inicializar los entrenadores.
     */

    public ModoSupervivenciaPvsP(String nombre1, String nombre2) throws PoobkemonException {
        List<Pokemon> pokemons1 = generarPokemonesAleatorios(6);
        List<Pokemon> pokemons2 = generarPokemonesAleatorios(6);

        List<Item> sinItems = new ArrayList<>(); // Sin items en modo supervivencia

        entrenador1 = new Entrenador(nombre1, true, pokemons1, sinItems);
        entrenador2 = new Entrenador(nombre2, true, pokemons2, sinItems);
    }

    /**
     * Obtiene el primer entrenador participante en el modo de juego.
     *
     * @return El primer entrenador (entrenador1).
     */
    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    /**
     * Obtiene el segundo entrenador participante en el modo de juego.
     *
     * @return El segundo entrenador (entrenador2).
     */
    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    /**
     * Genera una lista de Pokémon seleccionados aleatoriamente.
     * Si no hay suficientes Pokémon disponibles, se duplican los existentes
     * hasta alcanzar la cantidad requerida.
     *
     * @param cantidad La cantidad de Pokémon que se desea generar.
     * @return Una lista de Pokémon seleccionados aleatoriamente.
     */

    private List<Pokemon> generarPokemonesAleatorios(int cantidad) {
        List<Pokemon> todosLosPokemones = PokemonLoader.obtenerPokemonesDisponibles();
        List<Pokemon> pokemonesSeleccionados = new ArrayList<>();
        
        // Verificar si hay suficientes Pokémon
        if (todosLosPokemones.size() < cantidad) {
            // Si no hay suficientes, duplicamos la lista hasta tener suficientes
            List<Pokemon> pokemonesOriginales = new ArrayList<>(todosLosPokemones);
            while (todosLosPokemones.size() < cantidad) {
                todosLosPokemones.addAll(pokemonesOriginales);
            }
        }
        
        // Seleccionar Pokémon aleatoriamente
        for (int i = 0; i < cantidad; i++) {
            int indiceAleatorio = random.nextInt(todosLosPokemones.size());
            pokemonesSeleccionados.add(todosLosPokemones.get(indiceAleatorio));
            todosLosPokemones.remove(indiceAleatorio);
        }
        
        return pokemonesSeleccionados;
    }

    /**
     * Inicia una batalla entre los dos entrenadores en el modo de juego.
     * Este método crea una instancia de la clase `Batalla` utilizando los entrenadores
     * previamente generados y configurados.
     *
     * @throws PoobkemonException Si ocurre un error durante la inicialización de la batalla.
     */

    public void iniciarBatalla() throws PoobkemonException {
        Batalla batalla = new Batalla(entrenador1, entrenador2);
    }
}
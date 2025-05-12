package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import exceptions.PoobkemonException;

public class ModoSupervivenciaPvsP {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private static final Random random = new Random();

    public ModoSupervivenciaPvsP(String nombre1, String nombre2) throws PoobkemonException {
        List<Pokemon> pokemons1 = generarPokemonesAleatorios(6);
        List<Pokemon> pokemons2 = generarPokemonesAleatorios(6);

        List<Item> sinItems = new ArrayList<>(); // Sin items en modo supervivencia

        entrenador1 = new Entrenador(nombre1, true, pokemons1, sinItems);
        entrenador2 = new Entrenador(nombre2, true, pokemons2, sinItems);
    }
    
    public Entrenador getEntrenador1() {
        return entrenador1;
    }
    
    public Entrenador getEntrenador2() {
        return entrenador2;
    }

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

    public void iniciarBatalla() throws PoobkemonException {
        Batalla batalla = new Batalla(entrenador1, entrenador2);
    }
}
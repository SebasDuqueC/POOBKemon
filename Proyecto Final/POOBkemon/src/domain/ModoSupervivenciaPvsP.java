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
        List<Pokemon> pokemones = new ArrayList<>();
        List<String> nombresPokemon = PokemonFactory.obtenerNombresDePokemones();
        
        // Seleccionar 6 Pokémones aleatorios sin repetir
        for (int i = 0; i < cantidad; i++) {
            // Si ya usamos todos los nombres disponibles, volvemos a empezar
            if (nombresPokemon.isEmpty()) {
                nombresPokemon = PokemonFactory.obtenerNombresDePokemones();
            }
            
            // Seleccionar un pokémon aleatorio de la lista
            int indiceAleatorio = random.nextInt(nombresPokemon.size());
            String nombrePokemon = nombresPokemon.get(indiceAleatorio);
            nombresPokemon.remove(indiceAleatorio);
            
            // Crear el pokémon y añadirlo a la lista
            Pokemon pokemon = PokemonFactory.crearPokemonPorNombre(nombrePokemon);
            pokemones.add(pokemon);
        }
        
        return pokemones;
    }

    public void iniciarBatalla() throws PoobkemonException {
        Batalla batalla = new Batalla(entrenador1, entrenador2);
    }
}
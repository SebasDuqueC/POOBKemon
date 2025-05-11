package domain;

import java.util.ArrayList;
import java.util.List;
import exceptions.PoobkemonException;

public class ModoSupervivenciaPvsP {
    private Entrenador jugador1;
    private Entrenador jugador2;

    public ModoSupervivenciaPvsP(String nombre1, String nombre2) throws PoobkemonException {
        List<Pokemon> pokemons1 = generarPokemonesAleatorios(6);
        List<Pokemon> pokemons2 = generarPokemonesAleatorios(6);

        List<Item> sinItems = new ArrayList<>(); // vacía

        jugador1 = new Entrenador(nombre1, true, pokemons1, sinItems);
        jugador2 = new Entrenador(nombre2, true, pokemons2, sinItems);
    }

    private List<Pokemon> generarPokemonesAleatorios(int cantidad) throws PoobkemonException {
        List<Pokemon> pokemones = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Pokemon p = PokemonFactory.crearPokemonAleatorio("P" + i, 100);
            Movimiento[] movimientos = MovimientoFactory.obtenerCuatroMovimientos(p.getTipo());
            for (Movimiento m : movimientos) {
                p.agregarMovimiento(m);
            }
            pokemones.add(p);
        }
        return pokemones;
    }

    public void iniciarBatalla() throws PoobkemonException {
        Batalla batalla = new Batalla(jugador1, jugador2);
        batalla.luchar();
    }
}

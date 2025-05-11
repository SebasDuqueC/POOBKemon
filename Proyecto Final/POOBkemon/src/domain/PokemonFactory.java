package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokemonFactory {

    private static final Tipo[] TIPOS_DISPONIBLES = Tipo.values(); // Asegúrate de tener enum Tipo

    public static Pokemon crearCharizard() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Lanzallamas", Tipo.FUEGO, 90, 100, 15, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Garra Dragón", Tipo.DRAGON, 80, 100, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Vuelo", Tipo.VOLADOR, 90, 95, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Pantalla de Humo", Tipo.NORMAL, 0, 100, 20, CategoriaMovimiento.ESTADO));

        return new Pokemon(
                "Charizard",
                Tipo.FUEGO,
                Tipo.VOLADOR,
                360, // PS
                293, // Ataque
                280, // Defensa
                348, // Ataque Especial
                295, // Defensa Especial
                328, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearBlastoise() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Hidrobomba", Tipo.AGUA, 110, 80, 5, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Cabezazo", Tipo.NORMAL, 70, 100, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Pistola Agua", Tipo.AGUA, 40, 100, 25, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Refugio", Tipo.NORMAL, 0, 100, 20, CategoriaMovimiento.ESTADO));

        return new Pokemon(
                "Blastoise",
                Tipo.AGUA,
                null, // No tiene tipo secundario
                362, // PS
                291, // Ataque
                328, // Defensa
                295, // Ataque Especial
                339, // Defensa Especial
                280, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearVenusaur() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Rayo Solar", Tipo.PLANTA, 120, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Bomba Lodo", Tipo.VENENO, 90, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Hoja Afilada", Tipo.PLANTA, 55, 95, 25, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Drenadoras", Tipo.PLANTA, 0, 100, 10, CategoriaMovimiento.ESTADO));

        return new Pokemon(
                "Venusaur",
                Tipo.PLANTA,
                Tipo.VENENO,
                364, // PS
                289, // Ataque
                291, // Defensa
                328, // Ataque Especial
                328, // Defensa Especial
                284, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearGengar() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Bola Sombra", Tipo.FANTASMA, 80, 100, 15, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Tóxico", Tipo.VENENO, 0, 90, 10, CategoriaMovimiento.ESTADO));
        movimientos.add(new Movimiento("Psíquico", Tipo.PSIQUICO, 90, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Rayo", Tipo.ELECTRICO, 90, 100, 15, CategoriaMovimiento.ESPECIAL));

        return new Pokemon(
                "Gengar",
                Tipo.FANTASMA,
                Tipo.VENENO,
                324, // PS
                251, // Ataque
                240, // Defensa
                394, // Ataque Especial
                273, // Defensa Especial
                350, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearDragonite() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Enfado", Tipo.DRAGON, 120, 100, 10, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Vuelo", Tipo.VOLADOR, 90, 95, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Rayo Hielo", Tipo.HIELO, 90, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO));

        return new Pokemon(
                "Dragonite",
                Tipo.DRAGON,
                Tipo.VOLADOR,
                386, // PS
                403, // Ataque
                317, // Defensa
                328, // Ataque Especial
                328, // Defensa Especial
                284, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearTogetic() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Viento Feérico", Tipo.HADA, 40, 100, 30, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Deseo", Tipo.NORMAL, 0, 100, 10, CategoriaMovimiento.ESTADO));
        movimientos.add(new Movimiento("Aire Afilado", Tipo.VOLADOR, 75, 95, 25, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Pantalla de Luz", Tipo.PSIQUICO, 0, 100, 30, CategoriaMovimiento.ESTADO));

        return new Pokemon(
                "Togetic",
                Tipo.HADA,
                Tipo.VOLADOR,
                314, // PS
                196, // Ataque
                295, // Defensa
                284, // Ataque Especial
                339, // Defensa Especial
                196, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearTyranitar() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Roca Afilada", Tipo.ROCA, 100, 80, 5, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Triturar", Tipo.SINIESTRO, 80, 100, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Rayo", Tipo.ELECTRICO, 90, 100, 15, CategoriaMovimiento.ESPECIAL));

        return new Pokemon(
                "Tyranitar",
                Tipo.ROCA,
                Tipo.SINIESTRO,
                404, // PS
                403, // Ataque
                350, // Defensa
                317, // Ataque Especial
                328, // Defensa Especial
                243, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearGardevoir() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Psíquico", Tipo.PSIQUICO, 90, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Brillo Mágico", Tipo.HADA, 80, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Calmamente", Tipo.PSIQUICO, 0, 100, 20, CategoriaMovimiento.ESTADO));
        movimientos.add(new Movimiento("Rayo", Tipo.ELECTRICO, 90, 100, 15, CategoriaMovimiento.ESPECIAL));

        return new Pokemon(
                "Gardevoir",
                Tipo.PSIQUICO,
                Tipo.HADA,
                340, // PS
                251, // Ataque
                251, // Defensa
                383, // Ataque Especial
                361, // Defensa Especial
                284, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearSnorlax() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Golpe Cuerpo", Tipo.NORMAL, 85, 100, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Descanso", Tipo.PSIQUICO, 0, 100, 10, CategoriaMovimiento.ESTADO));
        movimientos.add(new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Rayo", Tipo.ELECTRICO, 90, 100, 15, CategoriaMovimiento.ESPECIAL));

        return new Pokemon(
                "Snorlax",
                Tipo.NORMAL,
                null, // No tiene tipo secundario
                524, // PS
                350, // Ataque
                251, // Defensa
                251, // Ataque Especial
                350, // Defensa Especial
                174, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearMetagross() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Puño Meteoro", Tipo.ACERO, 90, 90, 10, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Cabeza de Hierro", Tipo.ACERO, 80, 100, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Psíquico", Tipo.PSIQUICO, 90, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO));

        return new Pokemon(
                "Metagross",
                Tipo.ACERO,
                Tipo.PSIQUICO,
                364, // PS
                405, // Ataque
                394, // Defensa
                317, // Ataque Especial
                306, // Defensa Especial
                262, // Velocidad
                movimientos
        );
    }


    public static Pokemon crearDonphan() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Rueda Fuego", Tipo.FUEGO, 60, 100, 25, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Avalancha", Tipo.ROCA, 75, 90, 10, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Refugio", Tipo.NORMAL, 0, 100, 20, CategoriaMovimiento.ESTADO));

        return new Pokemon(
                "Donphan",
                Tipo.TIERRA,
                null, // No tiene tipo secundario
                384, // PS
                372, // Ataque
                372, // Defensa
                240, // Ataque Especial
                240, // Defensa Especial
                218, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearMachamp() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Puño Dinámico", Tipo.LUCHA, 100, 50, 5, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Golpe Karate", Tipo.LUCHA, 50, 100, 25, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Roca Afilada", Tipo.ROCA, 100, 80, 5, CategoriaMovimiento.FISICO));

        return new Pokemon(
                "Machamp",
                Tipo.LUCHA,
                null, // No tiene tipo secundario
                384, // PS
                394, // Ataque
                284, // Defensa
                251, // Ataque Especial
                295, // Defensa Especial
                229, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearDelibird() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Regalo", Tipo.NORMAL, 0, 90, 15, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Ventisca", Tipo.HIELO, 110, 70, 5, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Vuelo", Tipo.VOLADOR, 90, 95, 15, CategoriaMovimiento.FISICO));
        movimientos.add(new Movimiento("Rayo Hielo", Tipo.HIELO, 90, 100, 10, CategoriaMovimiento.ESPECIAL));

        return new Pokemon(
                "Delibird",
                Tipo.HIELO,
                Tipo.VOLADOR,
                294, // PS
                229, // Ataque
                207, // Defensa
                251, // Ataque Especial
                207, // Defensa Especial
                273, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearRaichu() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Impactrueno", Tipo.ELECTRICO, 40, 100, 30, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Rayo", Tipo.ELECTRICO, 90, 100, 15, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Onda Trueno", Tipo.ELECTRICO, 0, 90, 20, CategoriaMovimiento.ESTADO));
        movimientos.add(new Movimiento("Cola Férrea", Tipo.ACERO, 100, 75, 15, CategoriaMovimiento.FISICO));

        return new Pokemon(
                "Raichu",
                Tipo.ELECTRICO,
                null, // No tiene tipo secundario
                324, // PS
                306, // Ataque
                229, // Defensa
                306, // Ataque Especial
                284, // Defensa Especial
                350, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearMewtwo() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento("Psíquico", Tipo.PSIQUICO, 90, 100, 10, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Bola Sombra", Tipo.FANTASMA, 80, 100, 15, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Rayo", Tipo.ELECTRICO, 90, 100, 15, CategoriaMovimiento.ESPECIAL));
        movimientos.add(new Movimiento("Recuperación", Tipo.NORMAL, 0, 100, 10, CategoriaMovimiento.ESTADO));

        return new Pokemon(
                "Mewtwo",
                Tipo.PSIQUICO,
                null, // No tiene tipo secundario
                416, // PS
                350, // Ataque
                306, // Defensa
                447, // Ataque Especial
                306, // Defensa Especial
                394, // Velocidad
                movimientos
        );
    }

    public static Pokemon crearPokemonAleatorio(String nombre, int nivel) {
        Random rand = new Random();

        Tipo tipo = TIPOS_DISPONIBLES[rand.nextInt(TIPOS_DISPONIBLES.length)];

        int ps = 250 + rand.nextInt(101); // 250–350
        int ataque = 100 + rand.nextInt(51);
        int defensa = 90 + rand.nextInt(41);
        int velocidad = 80 + rand.nextInt(41);
        int ataqueEspecial = 100 + rand.nextInt(51);
        int defensaEspecial = 90 + rand.nextInt(41);

        Movimiento[] movsArray = MovimientoFactory.obtenerCuatroMovimientos(tipo);
        List<Movimiento> movimientos = new ArrayList<>(List.of(movsArray));

        return new Pokemon(nombre, tipo, null, ps, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, movimientos);    }


    public static Pokemon crearPokemonPorNombre(String nombrePokemon) {
        switch (nombrePokemon.toLowerCase()) {
            case "charizard":
                return crearCharizard();
            case "blastoise":
                return crearBlastoise();
            case "venusaur":
                return crearVenusaur();
            case "gengar":
                return crearGengar();
            case "dragonite":
                return crearDragonite();
            case "togetic":
                return crearTogetic();
            case "tyranitar":
                return crearTyranitar();
            case "gardevoir":
                return crearGardevoir();
            case "snorlax":
                return crearSnorlax();
            case "metagross":
                return crearMetagross();
            case "donphan":
                return crearDonphan();
            case "machamp":
                return crearMachamp();
            case "delibird":
                return crearDelibird();
            case "raichu":
                return crearRaichu();
            default:
                throw new IllegalArgumentException("El Pokémon con nombre " + nombrePokemon + " no está registrado.");
        }
    }

    public static List<String> obtenerNombresDePokemones() {
        List<String> nombres = new ArrayList<>();
        nombres.add("Charizard");
        nombres.add("Blastoise");
        nombres.add("Venusaur");
        nombres.add("Gengar");
        nombres.add("Dragonite");
        nombres.add("Togetic");
        nombres.add("Tyranitar");
        nombres.add("Gardevoir");
        nombres.add("Snorlax");
        nombres.add("Metagross");
        nombres.add("Donphan");
        nombres.add("Machamp");
        nombres.add("Delibird");
        nombres.add("Raichu");
        return nombres;
    }
}


package domain;

/**
 * Clase que actúa como un catálogo de movimientos predefinidos para los Pokémon.
 * Proporciona métodos estáticos para obtener instancias de movimientos comunes,
 * cada uno con sus características específicas como tipo, potencia, precisión,
 * puntos de poder (PP) y categoría.
 *
 * Esta clase facilita la reutilización de movimientos estándar en el juego.
 */
public class MovimientoCatalogo {

    /**
     * Crea un movimiento predefinido llamado "Lanzallamas".
     * Este movimiento es de tipo Fuego, tiene una potencia de 90,
     * una precisión del 100%, un máximo de 15 puntos de poder (PP),
     * y pertenece a la categoría especial.
     *
     * @return Una instancia del movimiento "Lanzallamas".
     */

    public static Movimiento lanzallamas() {
        return new Movimiento("Lanzallamas", Tipo.FUEGO, 90, 100, 15, CategoriaMovimiento.ESPECIAL);
    }

    /**
     * Crea un movimiento predefinido llamado "Garra Dragón".
     * Este movimiento es de tipo Dragón, tiene una potencia de 80,
     * una precisión del 100%, un máximo de 15 puntos de poder (PP),
     * y pertenece a la categoría física.
     *
     * @return Una instancia del movimiento "Garra Dragón".
     */

    public static Movimiento garraDragon() {
        return new Movimiento("Garra Dragón", Tipo.DRAGON, 80, 100, 15, CategoriaMovimiento.FISICO);
    }

    /**
     * Crea un movimiento predefinido llamado "Hidrobomba".
     * Este movimiento es de tipo Agua, tiene una potencia de 110,
     * una precisión del 80%, un máximo de 5 puntos de poder (PP),
     * y pertenece a la categoría especial.
     *
     * @return Una instancia del movimiento "Hidrobomba".
     */

    public static Movimiento hidrobomba() {
        return new Movimiento("Hidrobomba", Tipo.AGUA, 110, 80, 5, CategoriaMovimiento.ESPECIAL);
    }


    /**
     * Crea un movimiento predefinido llamado "Cabezazo".
     * Este movimiento es de tipo Normal, tiene una potencia de 70,
     * una precisión del 100%, un máximo de 15 puntos de poder (PP),
     * y pertenece a la categoría física.
     *
     * @return Una instancia del movimiento "Cabezazo".
     */

    public static Movimiento cabezazo() {
        return new Movimiento("Cabezazo", Tipo.NORMAL, 70, 100, 15, CategoriaMovimiento.FISICO);
    }

    /**
     * Crea un movimiento predefinido llamado "Trueno".
     * Este movimiento es de tipo Eléctrico, tiene una potencia de 110,
     * una precisión del 70%, un máximo de 10 puntos de poder (PP),
     * y pertenece a la categoría especial.
     *
     * @return Una instancia del movimiento "Trueno".
     */

    public static Movimiento trueno() {
        return new Movimiento("Trueno", Tipo.ELECTRICO, 110, 70, 10, CategoriaMovimiento.ESPECIAL);
    }

    /**
     * Crea un movimiento predefinido llamado "Terremoto".
     * Este movimiento es de tipo Tierra, tiene una potencia de 100,
     * una precisión del 100%, un máximo de 10 puntos de poder (PP),
     * y pertenece a la categoría física.
     *
     * @return Una instancia del movimiento "Terremoto".
     */

    public static Movimiento terremoto() {
        return new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO);
    }


    /**
     * Crea un movimiento predefinido llamado "Golpe Cuerpo".
     * Este movimiento es de tipo Normal, tiene una potencia de 85,
     * una precisión del 100%, un máximo de 15 puntos de poder (PP),
     * y pertenece a la categoría física.
     *
     * @return Una instancia del movimiento "Golpe Cuerpo".
     */

    public static Movimiento golpeCuerpo() {
        return new Movimiento("Golpe Cuerpo", Tipo.NORMAL, 85, 100, 15, CategoriaMovimiento.FISICO);
    }

    /**
     * Crea un movimiento predefinido llamado "Psicoataque".
     * Este movimiento es de tipo Psíquico, tiene una potencia de 90,
     * una precisión del 100%, un máximo de 10 puntos de poder (PP),
     * y pertenece a la categoría especial.
     *
     * @return Una instancia del movimiento "Psicoataque".
     */

    public static Movimiento psicoataque() {
        return new Movimiento("Psicoataque", Tipo.PSIQUICO, 90, 100, 10, CategoriaMovimiento.ESPECIAL);
    }
}

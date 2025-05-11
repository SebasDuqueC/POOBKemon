package domain;

public class MovimientoCatalogo {

    public static Movimiento lanzallamas() {
        return new Movimiento("Lanzallamas", Tipo.FUEGO, 90, 100, 15, CategoriaMovimiento.ESPECIAL);
    }

    public static Movimiento garraDragon() {
        return new Movimiento("Garra Dragón", Tipo.DRAGON, 80, 100, 15, CategoriaMovimiento.FISICO);
    }

    public static Movimiento hidrobomba() {
        return new Movimiento("Hidrobomba", Tipo.AGUA, 110, 80, 5, CategoriaMovimiento.ESPECIAL);
    }

    public static Movimiento cabezazo() {
        return new Movimiento("Cabezazo", Tipo.NORMAL, 70, 100, 15, CategoriaMovimiento.FISICO);
    }

    public static Movimiento trueno() {
        return new Movimiento("Trueno", Tipo.ELECTRICO, 110, 70, 10, CategoriaMovimiento.ESPECIAL);
    }

    public static Movimiento terremoto() {
        return new Movimiento("Terremoto", Tipo.TIERRA, 100, 100, 10, CategoriaMovimiento.FISICO);
    }

    public static Movimiento golpeCuerpo() {
        return new Movimiento("Golpe Cuerpo", Tipo.NORMAL, 85, 100, 15, CategoriaMovimiento.FISICO);
    }

    public static Movimiento psicoataque() {
        return new Movimiento("Psicoataque", Tipo.PSIQUICO, 90, 100, 10, CategoriaMovimiento.ESPECIAL);
    }
}

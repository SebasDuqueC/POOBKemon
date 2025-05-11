package exceptions;

public class PoobkemonException extends Exception {
    
    public PoobkemonException(String mensaje) {
        super(mensaje);
    }
    
    public PoobkemonException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
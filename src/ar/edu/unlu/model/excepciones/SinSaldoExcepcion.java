package ar.edu.unlu.model.excepciones;

public class SinSaldoExcepcion extends RuntimeException{
    public SinSaldoExcepcion(String mensaje){
        super(mensaje);
    }
}

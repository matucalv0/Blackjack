package ar.edu.unlu.model.excepciones;

public class AccionNoPermitidaExcepcion extends RuntimeException{
    public AccionNoPermitidaExcepcion(String mensaje){
        super(mensaje);
    }
}

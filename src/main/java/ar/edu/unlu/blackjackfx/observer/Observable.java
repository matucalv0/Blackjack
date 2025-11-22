package ar.edu.unlu.blackjackfx.observer;

public interface Observable {
    void agregarObservador(Observador observador);
    void eliminarObservador(Observador observador);
    void notificar(Object evento, Object data);
}

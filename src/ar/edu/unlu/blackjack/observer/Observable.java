package ar.edu.unlu.blackjack.observer;

public interface Observable {
    void agregarObservador(Observador observador);
    void eliminarObservador(Observador observador);
    void notificar(Object o);
}

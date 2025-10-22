package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;

public interface IParticipante {
    public void dividir();
    public void doblar();
    public void realizarApuesta(double apuesta) throws ApuestaMayorAlSaldoExcepcion;


}

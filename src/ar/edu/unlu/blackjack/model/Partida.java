package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.blackjack.observer.Observable;
import ar.edu.unlu.blackjack.observer.Observador;
import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

import java.util.*;

public class Partida implements Observable {
    private ArrayList<Observador> observadores = new ArrayList<>();
    private Participante participante;
    private Ronda ronda;

    public Partida(){
        this.ronda = new Ronda();
    }

    public void jugadorSeUne(Jugador jugador){
        participante = new Participante(jugador);
    }

    public void recibirApuesta(Participante participante, double apuesta) throws ApuestaMayorAlSaldoExcepcion{
        if (Double.compare(apuesta, participante.getSaldoJugador()) > 0){
            throw new ApuestaMayorAlSaldoExcepcion("Saldo insuficiente");
        }


        participante.setApuesta(apuesta);
        participante.restarBanca(apuesta);

        ronda.agregarParticipante(participante);

    }


    public void iniciarPartida() throws RondaVaciaExcepcion {
        ronda.rondaInicial();
    }

    public Participante getParticipante(){
        return participante;
    }

    public Ronda getRonda(){
        return ronda;
    }


    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);

    }

    @Override
    public void notificar() {
        for (Observador observador: observadores){
            observador.actualizar();
        }
    }
}

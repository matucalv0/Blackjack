package ar.edu.unlu.blackjackfx.model;

import ar.edu.unlu.blackjackfx.observer.Observable;
import ar.edu.unlu.blackjackfx.observer.Observador;

import java.util.*;

public class Partida implements Observable {
    private ArrayList<Observador> observadores = new ArrayList<>();
    private ArrayList<Participante> participantes = new ArrayList<>();
    private Ronda ronda;

    public Partida(){
        this.ronda = new Ronda();
    }

    public void jugadorSeUne(Jugador jugador){
        participantes.add(new Participante(jugador));
        notificar(EVENTO_PARTIDA.JUGADOR_UNIDO, jugador);
    }

    public void sumarApuesta(Participante participante, int monto){
        participante.getApuesta().sumar(monto);
        participante.restarBanca(monto);
        notificar(EVENTO_PARTIDA.APUESTA_RECIBIDA, null);
    }

    public void jugadorPaso(Participante participante){
        notificar(EVENTO_PARTIDA.JUGADOR_PASO, participante);
    }

    public void limpiarManos(){
        ronda.limpiarManoCrupier();
        for(Participante participante: participantes){
            for(Mano mano: participante.getManos()){
                mano.vaciarMano();
            }
        }
    }

    public void recibirApuesta(Participante participante, double apuesta){

        participante.setApuesta(apuesta);
        participante.restarBanca(apuesta);

        ronda.agregarJugadorRonda(participante);

        notificar(EVENTO_PARTIDA.APUESTA_RECIBIDA, null);

    }

    public void recibirApuesta(Participante participante){

        ronda.agregarJugadorRonda(participante);

        notificar(EVENTO_PARTIDA.APUESTA_RECIBIDA, true);

    }



    public ArrayList<Participante> getListaParticipantes(){
        return participantes;
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
    public void notificar(Object evento, Object data) {
        for (Observador observador: observadores){
            observador.actualizar(evento, data);
        }
    }
}

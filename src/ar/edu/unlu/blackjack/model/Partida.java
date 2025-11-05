package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.blackjack.observer.Observable;
import ar.edu.unlu.blackjack.observer.Observador;
import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;


import java.util.*;

public class Partida implements Observable {
    private ArrayList<Observador> observadores = new ArrayList<>();
    private Participante participante;
    private Mazo mazo;
    private Crupier crupier;

    public Partida() {
        this.mazo = new Mazo();
        this.crupier = new Crupier();

    }



    public void jugadorSeUne(Jugador jugador){
        participante = new Participante(jugador);
    }

    public void recibirApuesta(double apuesta) throws ApuestaMayorAlSaldoExcepcion{
        if (Double.compare(apuesta, participante.getSaldoJugador()) > 0){
            throw new ApuestaMayorAlSaldoExcepcion("Saldo insuficiente");
        }


        participante.setApuesta(apuesta);
        participante.restarBanca(apuesta);

    }

    public void rondaInicial() {

        crupier.agregarCarta(mazo.repartirCarta());

        participante.agregarCarta(mazo.repartirCarta());

        crupier.agregarCarta(mazo.repartirCarta());

        participante.agregarCarta(mazo.repartirCarta());

        notificar();


    }

    public boolean participanteSePaso(){
        if (participante.getMano().sePaso()){
            return true;
        }

        return false;
    }


    public void participantePideCarta()  {

        if (participante == null){
            return;
        }

        participante.agregarCarta(mazo.repartirCarta());

        notificar();


    }

    public void evaluarGanador(){
        if (crupier.getMano().sePaso()){
            pagoNormal(participante);
            participante.getApuesta().clearApuesta();
        }
        else {
            if (participante.puntajeActual() > crupier.puntajeActual()){
                pagoNormal(participante);
            } else if (participante.puntajeActual() == crupier.puntajeActual()){
                devolverDinero(participante);
            }

            participante.getApuesta().clearApuesta();
        }
    }

    public void devolverDinero(Participante participante){
        participante.sumarBanca(participante.getApuesta().getMonto());
    }

    public void pagoNormal(Participante participante){
        double pagoApuesta = (participante.getApuesta().getMonto()) * 2;
        participante.sumarBanca(pagoApuesta);
    }

    public void pagoBlackjack(Participante participante){
        double pagoApuesta = (participante.getApuesta().getMonto()) * 2.25;
        participante.sumarBanca(pagoApuesta);
    }



    public boolean hizoBlackjack(ParticipanteBase participante){
        return (participante.puntajeActual() == 21) && (participante.cantidadCartasEnMano() == 2);
    }


    public void finDeRonda(){
        if (hizoBlackjack(crupier)){
            if (hizoBlackjack(participante)){
                devolverDinero(participante);
                return;
            }
        }else {
            if (hizoBlackjack(participante)){
                pagoBlackjack(participante);
                return;
            }
        }

        while (crupier.puntajeActual() < 17){
            crupier.agregarCarta(mazo.repartirCarta());
        }

        evaluarGanador();
    }



    public Participante getParticipante(){
        return participante;
    }

    public Crupier getCrupier() {
        return crupier;
    }

    public Mazo getMazo() {
        return mazo;
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

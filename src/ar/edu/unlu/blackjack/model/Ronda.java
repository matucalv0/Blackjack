package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.PuntajeMayorA21Excepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ronda {
    private Queue<Participante> colaTurnos = new LinkedList<>();
    private ArrayList<Participante> participantesActivosFinalRonda = new ArrayList<>();
    private Mazo mazo;
    private Crupier crupier;

    public Ronda(){
        mazo = new Mazo();
        crupier = new Crupier();
    }


    public void agregarJugadorRonda(Participante jugadorRonda){
        colaTurnos.add(jugadorRonda);
    }

    public void finDeRonda(){
        while (crupier.puntajeActual() < 17){
            crupier.agregarCarta(mazo.repartirCarta());
        }

        evaluarGanadores();
        participantesActivosFinalRonda.clear();

    }

    public void participantePideCarta() throws PuntajeMayorA21Excepcion {
        Participante participante = participanteConTurno();
        if (participante.puntajeActual() > 21){
            throw new PuntajeMayorA21Excepcion("Su puntaje es mayor a 21, no puede pedir mas cartas");
        }

        participante.agregarCarta(mazo.repartirCarta());

    }

    public void evaluarGanadores(){
        if (crupier.getMano().sePaso()){
            for (Participante participante: participantesActivosFinalRonda){
                pagoNormal(participante);
                participante.getApuesta().clearApuesta();
            }
        } else {
            for (Participante participante: participantesActivosFinalRonda){
                if (participante.puntajeActual() > crupier.puntajeActual()){
                    pagoNormal(participante);
                } else if (participante.puntajeActual() == crupier.puntajeActual()){
                    devolverDinero(participante);
                }
                participante.getApuesta().clearApuesta();
            }
        }

    }


    public void devolverDinero(Participante participante){
        participante.sumarBanca(participante.getApuesta().getMonto());
    }

    public void pagoNormal(Participante participante){
        double pagoApuesta = (participante.getApuesta().getMonto()) * 2;
        participante.sumarBanca(pagoApuesta);
    }

    public void verificarBlackjack(){
        ArrayList<Participante> jugadoresConBlackjack = new ArrayList<>();
        for (Participante participante: colaTurnos){
            if (participante.puntajeActual() == 21){
                double pagoApuesta = (participante.getApuesta().getMonto()) * 2.5;
                participante.sumarBanca(pagoApuesta);
                participante.getApuesta().clearApuesta();
            }
        }


    }





    public void participanteSePlanta(){
        participantesActivosFinalRonda.add(colaTurnos.poll());
    }

    public Participante participanteConTurno(){
        return colaTurnos.peek();
    }




    public void rondaInicial() throws RondaVaciaExcepcion {
        if (colaTurnos.isEmpty()){
            throw new RondaVaciaExcepcion("Ningun jugador apostó, no se puede iniciar la ronda");
        }

        crupier.agregarCarta(mazo.repartirCarta());

        for (Participante participante : colaTurnos) {
            participante.agregarCarta(mazo.repartirCarta());

        }

        crupier.agregarCarta(mazo.repartirCarta());

        for (Participante participante : colaTurnos) {
            participante.agregarCarta(mazo.repartirCarta());
        }

        verificarBlackjack();
    }

    public Queue<Participante> getColaTurnos(){
        return colaTurnos;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public Crupier getCrupier() {
        return crupier;
    }
}

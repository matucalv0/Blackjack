package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

import java.util.LinkedList;
import java.util.Queue;

public class Ronda {
    Queue<Participante> participantesRonda = new LinkedList<>();

    public Ronda(){
    }


    public void agregarJugadorRonda(Participante jugadorRonda){
        participantesRonda.add(jugadorRonda);
    }




    public void rondaInicial(Crupier crupier, Mazo mazo) throws RondaVaciaExcepcion {

        if (participantesRonda.isEmpty()){
            throw new RondaVaciaExcepcion("Ningun jugador apostó, no se puede iniciar la ronda");
        }

        crupier.agregarCarta(mazo.repartirCarta());

        for (Participante participante : participantesRonda) {
            participante.agregarCarta(mazo.repartirCarta());

        }

        crupier.agregarCarta(mazo.repartirCarta());

        for (Participante participante : participantesRonda) {
            participante.agregarCarta(mazo.repartirCarta());
        }

    }

}

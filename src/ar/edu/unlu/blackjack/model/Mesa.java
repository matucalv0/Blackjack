package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.PartidaSinApuestasExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;

import java.util.*;

public class Mesa {
    ArrayList<Participante> participantes = new ArrayList<>();
    Crupier crupier;
    Mazo mazo;

    public Mesa(){
        this.crupier = new Crupier();
        this.mazo = new Mazo();
    }

    public void jugadorSeUne(Jugador jugador){
        participantes.add(new Participante(jugador));
    }


    public void iniciarPartida() throws PartidaSinJugadoresExcepcion, PartidaSinApuestasExcepcion {

            if (participantes.isEmpty()){
                throw new PartidaSinJugadoresExcepcion("Debe haber al menos un jugador en la mesa");
            }



            if (participantes.stream().noneMatch(participante -> participante.isApuesta)){
                throw new PartidaSinApuestasExcepcion("Al menos un jugador debe realizar una apuesta");
            }

            crupier.agregarCarta(mazo.repartirCarta());

            for (Participante participante : participantes) {
                if (participante.isApuesta){
                    participante.agregarCarta(mazo.repartirCarta());
                }
            }

            crupier.agregarCarta(mazo.repartirCarta());

            for (Participante participante : participantes) {
                if (participante.isApuesta){
                    participante.agregarCarta(mazo.repartirCarta());
                }
            }

    }

    public ArrayList<Participante> getListaParticipantes(){
        return participantes;
    }

    public Crupier getCrupier() {
        return crupier;
    }

    public Mazo getMazo() {
        return mazo;
    }
}

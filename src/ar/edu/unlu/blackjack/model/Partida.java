package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinApuestasExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

import java.util.*;

public class Partida {
    ArrayList<Participante> participantes = new ArrayList<>();
    Crupier crupier;
    Mazo mazo;
    Ronda ronda;

    public Partida(){
        this.crupier = new Crupier();
        this.mazo = new Mazo();
        this.ronda = new Ronda();
    }

    public void jugadorSeUne(Jugador jugador){
        participantes.add(new Participante(jugador));
    }

    public void recibirApuesta(Participante participante, double apuesta) throws ApuestaMayorAlSaldoExcepcion{
        if (Double.compare(apuesta, participante.getSaldoJugador()) > 0){
            throw new ApuestaMayorAlSaldoExcepcion("Saldo insuficiente");
        }

        participante.setApuesta(apuesta);
        participante.restarBanca(apuesta);

        ronda.agregarJugadorRonda(participante);


    }


    public void iniciarPartida() throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion {
        if (participantes.isEmpty()){
            throw new PartidaSinJugadoresExcepcion("Debe haber al menos un jugador en la mesa");
        }

        ronda.rondaInicial(crupier, mazo);
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

package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinApuestasExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

import java.util.*;

public class Partida {
    private ArrayList<Participante> participantes = new ArrayList<>();
    private Ronda ronda;

    public Partida(){
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

        ronda.rondaInicial();
    }

    public ArrayList<Participante> getListaParticipantes(){
        return participantes;
    }

    public Ronda getRonda(){
        return ronda;
    }


}

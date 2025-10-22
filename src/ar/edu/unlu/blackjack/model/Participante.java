package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;

public class Participante extends ParticipanteBase implements IParticipante {
    Jugador jugador;
    boolean isApuesta = false;


    public Participante(Jugador jugador) {
        this.jugador = jugador;
    }


    @Override
    public void dividir() {

    }

    @Override
    public void doblar() {

    }

    @Override
    public void realizarApuesta(double apuesta) throws ApuestaMayorAlSaldoExcepcion{
        if (Double.compare(apuesta, jugador.getSaldo()) > 0){
            throw new ApuestaMayorAlSaldoExcepcion("Saldo insuficiente");
        }

        jugador.getBanca().restarDinero(apuesta);
        isApuesta = true;


    }

}

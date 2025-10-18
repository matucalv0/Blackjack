package ar.edu.unlu.blackjack.model;

public class Participante extends ParticipanteBase implements IParticipante {
    Jugador jugador;


    public Participante(Mano mano, Jugador jugador) {
        super(mano);
        this.jugador = jugador;
    }


    @Override
    public void dividir() {

    }

    @Override
    public void doblar() {

    }
}

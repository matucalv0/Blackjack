package ar.edu.unlu.blackjack.model;

public abstract class ParticipanteBase {
    Mano mano;
    Integer puntaje;


    public ParticipanteBase(Mano mano){
        this.mano = mano;
    }

    public void setMano(Mano mano){
        this.mano = mano;
    }

    public void pedirCarta(Carta carta){
        mano.addCarta(carta);
    }

    public void plantarse(){

    }

}

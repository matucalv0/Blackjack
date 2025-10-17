package ar.edu.unlu.blackjack.model;

public class Participante {
    Jugador jugador;
    Mano mano;

    public Participante(Jugador jugador){
        this.jugador = jugador;
    }

    public Participante(Jugador jugador, Mano mano){
        this.jugador = jugador;
        this.mano = mano;
    }

    public void setMano(Mano mano){
        this.mano = mano;
    }



}

package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.PuntajeMayorA21Excepcion;

public abstract class ParticipanteBase {
    protected Mano mano;

    public ParticipanteBase(){
        this.mano = new Mano();
    }


    public void agregarCarta(Carta carta){
        mano.addCarta(carta);
    }

    public int cantidadCartasEnMano(){
        return mano.cantidadCartas();
    }


    public Mano getMano(){
        return mano;
    }
    

    public int puntajeActual(){
        return this.mano.puntaje();
    }


}

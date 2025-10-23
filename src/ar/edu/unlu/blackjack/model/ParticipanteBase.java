package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.PuntajeMayorA21Excepcion;

public abstract class ParticipanteBase {
    Mano mano;
    Integer puntaje;


    public ParticipanteBase(){
        this.mano = new Mano();
    }


    public void agregarCarta(Carta carta){
        mano.addCarta(carta);
    }

    public Mano getMano(){
        return mano;
    }

    public int puntajeActual(){
        return this.mano.puntaje();
    }

    public void pedirCarta(Carta carta) throws PuntajeMayorA21Excepcion {
        mano.addCarta(carta);
    }


    public void plantarse(){

    }

}

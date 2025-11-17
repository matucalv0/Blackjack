package ar.edu.unlu.blackjack.model;

import java.util.ArrayList;

public abstract class ParticipanteBase {
    private ArrayList<Mano> manos = new ArrayList<>();
    protected int manoActual = 0;

    public ParticipanteBase(){
        manos.add(new Mano());
    }


    public void agregarCarta(Carta carta){
        manos.get(manoActual).addCarta(carta);
    }

    public int cantidadCartasEnMano(){
        return manos.get(manoActual).cantidadCartas();
    }


    public Mano getMano(){
        return manos.get(manoActual);
    }
    

    public int puntajeActual(){
        return manos.get(manoActual).puntaje();
    }


    public ArrayList<Mano> getManos(){
        return this.manos;
    }

    public void agregarMano(Mano mano){
        manos.add(mano);
    }


}

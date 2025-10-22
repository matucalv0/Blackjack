package ar.edu.unlu.blackjack.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Mano {
    ArrayList<Carta> cartas = new ArrayList<>();


    public Mano(){};

    public void addCarta(Carta carta){
        cartas.add(carta);
    }

    public ArrayList<Carta> getCartas(){
        return cartas;
    }

    public int cantidadCartas(){
        return cartas.size();
    }

    public void resetMano(){
        cartas.clear();
    }

    @Override
    public String toString() {
        return "Mano{" +
                "cartas=" + cartas +
                '}';
    }
}

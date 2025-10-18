package ar.edu.unlu.blackjack.model;

import java.util.HashSet;
import java.util.Set;

public class Mano {
    Set<Carta> cartas = new HashSet<>();

    public void addCarta(Carta carta){
        cartas.add(carta);
    }

    public Set<Carta> getCartas(){
        return cartas;
    }

    @Override
    public String toString() {
        return "Mano{" +
                "cartas=" + cartas +
                '}';
    }
}

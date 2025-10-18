package ar.edu.unlu.blackjack.model;

import java.util.Objects;

public class Carta {
    Palo palo;
    int numero;

    public Carta(){

    }

    public Carta(int numero, Palo palo){
        this.numero = numero;
        this.palo = palo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Carta carta)) return false;
        return numero == carta.numero && palo == carta.palo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(palo, numero);
    }

    @Override
    public String toString() {
        return "Carta{" +
                "palo=" + palo +
                ", numero=" + numero +
                '}';
    }
}

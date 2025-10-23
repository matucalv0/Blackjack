package ar.edu.unlu.blackjack.model;

import java.util.Objects;

public class Carta {
    private Palo palo;
    private int numero;
    private boolean esVisible = true;

    public Carta(){

    }

    public Carta(int numero, Palo palo){
        this.numero = numero;
        this.palo = palo;
    }

    public void ocultar(){
        this.esVisible = false;
    }

    public void revelar(){
        this.esVisible = true;
    }

    public int getnumero(){
        return numero;
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

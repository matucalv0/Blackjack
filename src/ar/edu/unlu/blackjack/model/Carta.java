package ar.edu.unlu.blackjack.model;

public class Carta {
    Palo palo;
    int numero;

    public Carta(){

    }

    public Carta(int numero, Palo palo){
        this.numero = numero;
        this.palo = palo;
    }

}

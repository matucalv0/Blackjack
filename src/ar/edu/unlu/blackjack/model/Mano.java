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

    public int puntaje(){
        int p = 0;
        int cantidadAses = 0;

        for (Carta carta: cartas){
            if (carta.getValor().equals("A")){
                cantidadAses++;
            }
            p += carta.getNumero();
        }


        while (p > 21 && cantidadAses > 0){
            p -= 10;
            cantidadAses--;
        }



        return p;
    }

    public boolean verificarBlackjack(){
        return false;

    }

    public int cantidadCartas(){
        return cartas.size();
    }

    public void resetMano(){
        cartas.clear();
    }

    public boolean compararMano(Mano mano){
        return this.puntaje() > mano.puntaje();
    }

    @Override
    public String toString() {
        return "Mano{" +
                "cartas=" + cartas +
                '}';
    }
}

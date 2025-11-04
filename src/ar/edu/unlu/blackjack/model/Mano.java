package ar.edu.unlu.blackjack.model;

import java.util.ArrayList;

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
            if (carta.isEsVisible()){
                if (carta.getCaracter().equals("A")){
                    cantidadAses++;
                }
                p += carta.getValor();
            }
        }

        while (p > 21 && cantidadAses > 0){
            p -= 10;
            cantidadAses--;
        }



        return p;
    }


    public int cantidadCartas(){
        return cartas.size();
    }


    public void resetMano(){
        cartas.clear();
    }

    public boolean sePaso(){
        return (puntaje() > 21);
    }



    @Override
    public String toString() {
        return "Mano{" +
                "cartas=" + cartas +
                '}';
    }
}

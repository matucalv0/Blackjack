package ar.edu.unlu.blackjack.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Mazo {
    Stack<Carta> mazo = new Stack<>();


    public Mazo(){
        generarMazo();
    }

    public void generarMazo() {
        List<Integer> numero = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);

        for (Palo palo : Palo.values()) {
            for (Integer num : numero){
                mazo.add(new Carta(num, palo));
            }
        }
    }

    public Stack<Carta> getMazo(){
        return mazo;
    }

}

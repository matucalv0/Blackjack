package ar.edu.unlu.blackjack.model;

import java.util.Objects;

public class Carta {
    private Palo palo;
    private String valor;
    private boolean esVisible = true;

    public Carta(){

    }

    public Carta(int valor, Palo palo){
        switch (valor){
            case 11:
                this.valor = "J";
                break;
            case 12:
                this.valor = "Q";
                break;
            case 13:
                this.valor = "K";
                break;
            case 1:
                this.valor = "A";
                break;
            default:
                this.valor = String.valueOf(valor);
        }

        this.palo = palo;
    }

    public void ocultar(){
        this.esVisible = false;
    }

    public void revelar(){
        this.esVisible = true;
    }

    public String getValor(){
        return valor;
    }

    public int getNumero(){
        if (valor.equals("J") || valor.equals("Q") || valor.equals("K")){
            return 10;
        } else if (valor.equals("A")){
            return 11;
        }

        return Integer.parseInt(valor);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Carta carta)) return false;
        return Objects.equals(valor, carta.valor) && palo == carta.palo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(palo, valor);
    }

    @Override
    public String toString() {
        return "Carta{" +
                "palo=" + palo +
                ", numero=" + valor +
                '}';
    }
}

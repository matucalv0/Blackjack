package ar.edu.unlu.blackjack.model;

public class Bankroll {
    private Double dinero;



    public Double getDinero(){
        return dinero;
    }


    public void agregarDinero(double dinero){
        this.dinero += dinero;
    }

}

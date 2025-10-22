package ar.edu.unlu.blackjack.model;

public class Bankroll {
    private double dinero;



    public Double getDinero(){
        return dinero;
    }


    public void agregarDinero(double dinero){
        this.dinero += dinero;
    }

    public void restarDinero(double dinero){
        this.dinero -= dinero;
    }

}

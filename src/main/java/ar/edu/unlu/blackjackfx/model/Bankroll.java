package ar.edu.unlu.blackjackfx.model;

public class Bankroll {
    private double dinero;



    public Double getDinero(){
        return dinero;
    }


    public void agregarDinero(double dinero) {
        if(dinero < 0){
            return;
        }

        this.dinero += dinero;
    }

    public void restarDinero(double dinero) {
        if (dinero < 0) {
            return;
        }

        if (dinero > this.dinero) {
            return;
        }

        this.dinero -= dinero;
    }

}

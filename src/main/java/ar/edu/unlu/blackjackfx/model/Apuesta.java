package ar.edu.unlu.blackjackfx.model;

public class Apuesta {
    private double monto;

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void clearApuesta(){
        monto = 0;
    }


}

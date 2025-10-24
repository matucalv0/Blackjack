package ar.edu.unlu.blackjack.model;

public class Jugador  {
    private String nombre;
    private Bankroll banca;


    public Jugador(String nombre){
        this.nombre = nombre;
        this.banca = new Bankroll();
    }

    public Double getSaldo(){
        return banca.getDinero();
    }

    public void agregarDinero(double dinero){
        banca.agregarDinero(dinero);
    }



    public String getNombre(){
        return nombre;
    }

    public Bankroll getBanca(){
        return banca;
    }
}

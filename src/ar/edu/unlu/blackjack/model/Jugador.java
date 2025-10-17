package ar.edu.unlu.blackjack.model;

public class Jugador  {
    private String nombre;
    private Bankroll banca;


    public Jugador(String nombre){
        this.nombre = nombre;
    }



    public String getNombre(){
        return nombre;
    }
}

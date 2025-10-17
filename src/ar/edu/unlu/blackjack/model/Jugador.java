package ar.edu.unlu.blackjack.model;

public class Jugador {
    private String nombre;
    private Mano mano;
    private Integer puntaje = 0;


    public Jugador(String nombre){
        this.nombre = nombre;
    }

    public Jugador(String nombre, Mano mano){
        this.nombre = nombre;
        this.mano = mano;

    }

    public String getNombre(){
        return nombre;
    }
}

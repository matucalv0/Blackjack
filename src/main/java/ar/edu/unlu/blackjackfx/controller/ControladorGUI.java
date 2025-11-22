package ar.edu.unlu.blackjackfx.controller;

import ar.edu.unlu.blackjackfx.model.EVENTO_PARTIDA;
import ar.edu.unlu.blackjackfx.model.Jugador;
import ar.edu.unlu.blackjackfx.model.Partida;
import ar.edu.unlu.blackjackfx.model.Ronda;
import ar.edu.unlu.blackjackfx.observer.Observador;
import ar.edu.unlu.blackjackfx.view.VistaGUI;
import ar.edu.unlu.blackjackfx.view.VistaInicio;
import ar.edu.unlu.blackjackfx.view.VistaMesa;


public class ControladorGUI implements Observador{
    private Partida modeloPartida;
    private Ronda modeloRonda;
    private VistaGUI vista;

    public ControladorGUI(Partida modeloPartida, Ronda modeloRonda, VistaGUI vista) {
        this.modeloPartida = modeloPartida;
        this.modeloRonda = modeloRonda;
        this.vista = vista;
        this.modeloPartida.agregarObservador(this);
        this.modeloRonda.agregarObservador(this);
    }


    public void agregarUsuario(String nombre, double dinero){
        Jugador nuevoJugador = new Jugador(nombre);
        nuevoJugador.agregarDinero(dinero);
        modeloPartida.jugadorSeUne(nuevoJugador);
    }

    @Override
    public void actualizar(Object evento, Object data) {
        switch (evento) {
            case EVENTO_PARTIDA.JUGADOR_UNIDO -> {
                if (vista instanceof VistaInicio){
                    if (data instanceof Jugador){
                        ((VistaInicio) vista).agregarJugador(modeloPartida.getListaParticipantes());
                    }
                }

            }
            default -> throw new IllegalStateException("Unexpected value: " + evento);
        }

        }



}

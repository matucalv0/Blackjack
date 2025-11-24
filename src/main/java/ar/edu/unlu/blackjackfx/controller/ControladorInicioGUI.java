package ar.edu.unlu.blackjackfx.controller;

import ar.edu.unlu.blackjackfx.model.*;
import ar.edu.unlu.blackjackfx.observer.Observador;
import ar.edu.unlu.blackjackfx.view.VistaInicio;
import ar.edu.unlu.blackjackfx.view.VistaMesa;


public class ControladorInicioGUI implements Observador{
    private Partida modeloPartida;
    private Ronda modeloRonda;
    private VistaInicio vista;

    public ControladorInicioGUI(Partida modeloPartida, Ronda modeloRonda, VistaInicio vista) {
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

    public boolean verificarCantidadJugadores(){
        return modeloPartida.getListaParticipantes().size() < 4;
    }


    @Override
    public void actualizar(Object evento, Object data) {
        switch (evento) {
            case EVENTO_PARTIDA.JUGADOR_UNIDO -> {
                    if (data instanceof Jugador){
                        vista.agregarJugador(modeloPartida.getListaParticipantes());
                    }
                }
            case EVENTO_PARTIDA.APUESTA_RECIBIDA -> {}


            default -> throw new IllegalStateException("Unexpected value: " + evento);
        }

    }



}


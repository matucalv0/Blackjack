package ar.edu.unlu.blackjackfx.controller;

import ar.edu.unlu.blackjackfx.model.*;
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

    public void recibirApuesta(int apuesta, int jugador){
        Participante participante = modeloPartida.getListaParticipantes().get(jugador);
        if (apuesta > participante.getSaldoJugador()){
            return;
        }

        modeloPartida.sumarApuesta(participante, apuesta);
    }

    public boolean verificarCantidadJugadores(){
        return modeloPartida.getListaParticipantes().size() < 4;
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
            case EVENTO_PARTIDA.APUESTA_RECIBIDA -> {
                if (vista instanceof VistaMesa){
                    ((VistaMesa) vista).mostrarJugadorConTurno(modeloPartida.getListaParticipantes().get(((VistaMesa) vista).getTurnoMesa()));
                }
            }



            default -> throw new IllegalStateException("Unexpected value: " + evento);
        }

        }



}

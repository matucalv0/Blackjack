package ar.edu.unlu.blackjackfx.controller;

import ar.edu.unlu.blackjackfx.model.*;
import ar.edu.unlu.blackjackfx.observer.Observador;
import ar.edu.unlu.blackjackfx.view.VistaInicio;
import ar.edu.unlu.blackjackfx.view.VistaMesa;


public class ControladorMesaGUI implements Observador{
    private Partida modeloPartida;
    private Ronda modeloRonda;
    private VistaMesa vista;

    public ControladorMesaGUI(Partida modeloPartida, Ronda modeloRonda, VistaMesa vista) {
        this.modeloPartida = modeloPartida;
        this.modeloRonda = modeloRonda;
        this.vista = vista;
        this.modeloPartida.agregarObservador(this);
        this.modeloRonda.agregarObservador(this);
    }


    public void recibirApuesta(int apuesta, int jugador){
        Participante participante = modeloPartida.getListaParticipantes().get(jugador);
        if (apuesta > participante.getSaldoJugador()){
            return;
        }
        modeloPartida.sumarApuesta(participante, apuesta);
    }


    public void jugadorApuesta(int turno){
        modeloPartida.recibirApuesta(modeloPartida.getListaParticipantes().get(turno));
        if (turno == modeloPartida.getListaParticipantes().size()){

        }

    }

    public int jugadoresEnLaMesa(){
        return modeloPartida.getListaParticipantes().size();
    }



    @Override
    public void actualizar(Object evento, Object data) {
        switch (evento) {
            case EVENTO_PARTIDA.APUESTA_RECIBIDA -> vista.mostrarJugadorConTurno(modeloPartida.getListaParticipantes().get(vista.getTurnoMesa()));

            default -> throw new IllegalStateException("Unexpected value: " + evento);
        }

        }



}

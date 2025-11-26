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

    public void iniciarRonda(){
        modeloRonda.rondaInicial();
    }

    public void finDeRonda(){
        modeloRonda.faseCrupier();
        modeloRonda.finDeRonda();
    }




    public void jugadorApuesta(int turno){
        modeloPartida.recibirApuesta(modeloPartida.getListaParticipantes().get(turno));
        if (turno == modeloPartida.getListaParticipantes().size()){
            iniciarRonda();
        }

    }

    public void plantarse(){
        modeloRonda.participanteSePlanta();
    }

    public void agregarCartaJugador(){
        modeloRonda.participantePideCarta();

    }

    public int jugadoresEnLaMesa(){
        return modeloPartida.getListaParticipantes().size();
    }



    @Override
    public void actualizar(Object evento, Object data) {
        switch (evento) {
            case EVENTO_PARTIDA.APUESTA_RECIBIDA -> {

                // si el jugador solo sumo fichas
                if (data == null) {
                    vista.mostrarJugadorConTurno(
                            modeloPartida.getListaParticipantes().get(vista.getTurnoMesa())
                    );
                    return;
                }

                // si el jugador confirmó apuesta -> pasar turno
                vista.turnoSiguienteMesa();

                // Si ya pasamos el último jugador -> arrancar ronda
                if (vista.getTurnoMesa() == modeloPartida.getListaParticipantes().size()) {
                    // resetear turno a 0 para la ronda
                    vista.resetTurnoMesa();
                    vista.ocultarApuestas();
                    iniciarRonda();
                    return;
                }

                // Mostrar al nuevo jugador actual
                vista.mostrarJugadorConTurno(
                        modeloPartida.getListaParticipantes().get(vista.getTurnoMesa())
                );

        }
            case EVENTO_PARTIDA.PARTIDA_INICIADA -> {
                vista.resetTurnoMesa();
                vista.mostrarCartasCrupier(modeloRonda.getCrupier());
                vista.mostrarJugadorConTurno(modeloRonda.participanteConTurno());
            }

            case EVENTO_RONDA.CARTA_REPARTIDA -> {
                vista.mostrarJugadorConTurno(modeloRonda.participanteConTurno());
            }
            case EVENTO_RONDA.JUGADOR_SE_PASA -> {
                vista.mostrarAviso((Participante) data, "se pasó");

                if (modeloRonda.getColaTurnos().isEmpty()){
                    finDeRonda();
                } else {
                    vista.mostrarJugadorConTurno(modeloRonda.participanteConTurno());
                }

            }
            case EVENTO_RONDA.JUGADOR_SE_PLANTA -> {
                vista.mostrarAviso((Participante) data, "se plantó");

                if (modeloRonda.getColaTurnos().isEmpty()){
                    finDeRonda();
                } else {
                    vista.mostrarJugadorConTurno(modeloRonda.participanteConTurno());
                }

            }

            case EVENTO_RONDA.CRUPIER_JUEGA -> {
                vista.mostrarCartasCrupier((Crupier) data);
            }

            case EVENTO_RONDA.RONDA_TERMINADA -> {
                vista.mostrarJugadorConTurno(modeloPartida.getListaParticipantes().getFirst());
                modeloPartida.limpiarManos();
                vista.refrescarVista();

            }

            default -> throw new IllegalStateException("Unexpected value: " + evento);
        }

        }



}

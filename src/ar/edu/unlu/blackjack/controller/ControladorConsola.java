package ar.edu.unlu.blackjack.controller;


import ar.edu.unlu.blackjack.model.*;
import ar.edu.unlu.blackjack.observer.Observador;
import ar.edu.unlu.blackjack.view.VistaConsola;
import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;
import ar.edu.unlu.model.excepciones.PuntajeMayorA21Excepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

public class ControladorConsola implements Observador {
    private Partida modeloPartida;
    private Ronda modeloRonda;
    private VistaConsola vistaConsola;

    public ControladorConsola(Partida modeloPartida, Ronda modeloRonda, VistaConsola vistaConsola) {
        this.modeloPartida = modeloPartida;
        this.modeloRonda = modeloRonda;
        this.vistaConsola = vistaConsola;
        this.modeloPartida.agregarObservador(this);
        this.modeloRonda.agregarObservador(this);
    }


    public boolean loguearse() throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion, ApuestaMayorAlSaldoExcepcion {
        while (true){
            vistaConsola.login();
            int opcion = vistaConsola.obtenerOpcion();
            switch (opcion){
                case 1:
                    unirJugador();
                case 2:
                    iniciarRonda();
                default:
                    vistaConsola.mostrarMensaje("Opcion no valida");
            }
        }
    }

    public void iniciar() throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion, ApuestaMayorAlSaldoExcepcion {
        if (loguearse()) {
            unirJugador();
            }

        vistaConsola.mostrarMensaje("Adios!");
    }

    public void faseDeApuestas() throws ApuestaMayorAlSaldoExcepcion {
        for (Participante participante: modeloPartida.getListaParticipantes()){
            vistaConsola.mostrarDatos(participante);
            vistaConsola.mostrarMesa();
            if (vistaConsola.solicitarDato() == 1){
                modeloPartida.recibirApuesta(participante, vistaConsola.solicitarDato());
            }

        }
    }

    public void iniciarRonda() throws ApuestaMayorAlSaldoExcepcion, PartidaSinJugadoresExcepcion, RondaVaciaExcepcion {
       faseDeApuestas();
       if (!modeloRonda.getColaTurnos().isEmpty()){
           modeloRonda.rondaInicial();
       }
    }



    public void unirJugador() {
        String usuario = vistaConsola.solicitarDato("usuario");
        for (Participante participante: modeloPartida.getListaParticipantes()){
            if (participante.getNombre().equals(usuario)){
                vistaConsola.mostrarMensaje("El jugador ya se encuentra registrado");
                return;
            }
        }

        Jugador nuevoJugador = new Jugador(usuario);
        modeloPartida.jugadorSeUne(nuevoJugador);
    }

    public void ingresarDinero(Participante participante){
        participante.sumarBanca(vistaConsola.solicitarDato());
    }




    @Override
    public void actualizar(Object o) {
        switch (o) {
            case EVENTO_PARTIDA.JUGADOR_UNIDO -> vistaConsola.mostrarMensaje("Jugador se unió");
            case EVENTO_PARTIDA.APUESTA_RECIBIDA -> vistaConsola.mostrarMensaje("Apuesta recibida");
            case EVENTO_PARTIDA.PARTIDA_INICIADA -> vistaConsola.mostrarMensaje("Comienza la ronda!");
            case EVENTO_RONDA.CARTA_REPARTIDA -> vistaConsola.mostrarManoJugadores(modeloRonda.getColaTurnos());
            case EVENTO_RONDA.JUGADOR_SE_PASA -> vistaConsola.mostrarMensaje("El jugador se pasó!");
            case EVENTO_RONDA.JUGADOR_SE_PLANTA -> vistaConsola.mostrarMensaje("Jugador se plantó");
            case EVENTO_RONDA.CRUPIER_JUEGA -> vistaConsola.mostrarManoCrupier(modeloRonda.getCrupier());
            default -> throw new IllegalStateException("Unexpected value: " + o);
        }


    }
}

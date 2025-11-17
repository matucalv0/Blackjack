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


    public void loguearse() throws ApuestaMayorAlSaldoExcepcion {
        while (true){
            vistaConsola.login();
            int opcion = vistaConsola.obtenerOpcion();
            switch (opcion){
                case 1:
                    unirJugador();
                    break;
                case 2:
                    iniciarRonda();
                    break;
                case 0:
                    vistaConsola.mostrarMensaje("Hasta luego!");
                    return;
                default:
                    vistaConsola.mostrarMensaje("Opcion no valida");
                    break;
            }
        }
    }


    public void faseDeApuestas() throws ApuestaMayorAlSaldoExcepcion {
        for (Participante participante: modeloPartida.getListaParticipantes()){
            vistaConsola.mostrarDatos(participante);
            vistaConsola.mostrarMesa();
            int opcion = vistaConsola.obtenerOpcion();
            if (opcion == 1){
                try {
                    modeloPartida.recibirApuesta(participante, vistaConsola.solicitarDatoApuesta());
                } catch (ApuestaMayorAlSaldoExcepcion e) {
                    vistaConsola.mostrarMensaje("Saldo insuficiente");
                }
            }

        }
    }



    public void faseRonda(){
        while (!modeloRonda.getColaTurnos().isEmpty()){
            if(modeloRonda.verificarDividir() && modeloRonda.jugadorTieneUnaSolaMano()){
                vistaConsola.vistaRondaExtendida(modeloRonda.participanteConTurno());
                int opcion = vistaConsola.obtenerOpcion();
                switch (opcion){
                    case 1 -> modeloRonda.participantePideCarta();
                    case 2 -> modeloRonda.participanteSePlanta();
                    case 3 -> modeloRonda.jugadorDivide();
                }

            } else {
                vistaConsola.vistaRonda(modeloRonda.participanteConTurno());
                int opcion = vistaConsola.obtenerOpcion();
                switch (opcion){
                    case 1 -> modeloRonda.participantePideCarta();
                    case 2 -> modeloRonda.participanteSePlanta();
                }
            }
        }
    }

    public void faseFinal(){
        modeloRonda.faseCrupier();

    }

    public void iniciarRonda() throws ApuestaMayorAlSaldoExcepcion {
        while (true){
            vistaConsola.vistaMesa(modeloPartida.getListaParticipantes(), modeloRonda.getCrupier());
            int opcion = vistaConsola.obtenerOpcion();
            switch (opcion) {
                case 0 -> {
                    vistaConsola.mostrarMensaje("Saliendo de la mesa....");
                    return;
                }
                case 1 -> {
                    faseDeApuestas();
                    if (!modeloRonda.getColaTurnos().isEmpty()) {
                        modeloPartida.limpiarManos(); // o al final
                        modeloRonda.rondaInicial();
                        faseRonda();
                        faseFinal();
                        modeloRonda.finDeRonda();
                    }
                }
            }
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
        nuevoJugador.agregarDinero(vistaConsola.solicitarDato());
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
            case EVENTO_PARTIDA.PARTIDA_INICIADA, EVENTO_RONDA.CARTA_REPARTIDA, EVENTO_RONDA.MANO_DIVIDIDA -> {
                vistaConsola.mostrarManoCrupier(modeloRonda.getCrupier());
                vistaConsola.mostrarManoJugadores(modeloRonda.getColaTurnos());
            }
            case EVENTO_RONDA.JUGADOR_SE_PASA -> {
                vistaConsola.mostrarMensaje("El jugador se pasó!");
                vistaConsola.mostrarManoCrupier(modeloRonda.getCrupier());
                vistaConsola.mostrarManoJugadores(modeloRonda.getColaTurnos());
            }
            case EVENTO_RONDA.JUGADOR_SE_PLANTA -> vistaConsola.mostrarMensaje("Jugador se plantó");
            case EVENTO_RONDA.CRUPIER_JUEGA -> vistaConsola.mostrarManoCrupier(modeloRonda.getCrupier());
            case EVENTO_RONDA.BANCA_GANA -> vistaConsola.mostrarMensaje("La banca gana. ");
            case EVENTO_RONDA.RONDA_TERMINADA -> vistaConsola.mostrarJugadoresFinales(modeloRonda.getParticipantesActivosFinalRonda());
            default -> throw new IllegalStateException("Unexpected value: " + o);
        }


    }
}

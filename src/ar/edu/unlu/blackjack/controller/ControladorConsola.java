package ar.edu.unlu.blackjack.controller;


import ar.edu.unlu.blackjack.model.*;
import ar.edu.unlu.blackjack.observer.Observador;
import ar.edu.unlu.blackjack.view.VistaConsola;
import ar.edu.unlu.model.excepciones.AccionNoPermitidaExcepcion;

import java.util.ArrayList;

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


    public void loguearse() {
        while (true){
            if (modeloPartida.getListaParticipantes().isEmpty()){
                vistaConsola.login();
            } else {
                vistaConsola.loginExtendido();
            }

            String opcion = vistaConsola.solicitarDato("opcion");
            switch (opcion){
                case "1":
                    unirJugador();
                    break;
                case "2":
                    if (!modeloPartida.getListaParticipantes().isEmpty()){
                        iniciarRonda();
                    } else {
                        vistaConsola.mostrarMensaje("No hay jugadores");
                    }
                    break;
                case "3":
                    ingresarDinero();
                    break;
                case "0":
                    vistaConsola.limpiarConsola();
                    vistaConsola.mostrarMensaje("Hasta luego!");
                    return;
                default:
                    vistaConsola.mostrarMensaje("Opcion no valida");
                    break;
            }
        }
    }


    public void faseDeApuestas() {
        for (Participante participante : modeloPartida.getListaParticipantes()) {
            boolean accionValida = false;

            while (!accionValida) {
                vistaConsola.mostrarDatos(participante);
                vistaConsola.mostrarMesa();
                String opcion = vistaConsola.solicitarDato("opcion");

                switch (opcion) {
                    case "1":
                        vistaConsola.limpiarConsola();
                        vistaConsola.mostrarDatos(participante);
                        String apuesta = vistaConsola.solicitarDato("apuesta");
                        if (verificarApuesta(participante, apuesta)) {
                            modeloPartida.recibirApuesta(participante, Double.parseDouble(apuesta));
                            accionValida = true;
                        } else {
                            vistaConsola.mostrarMensaje("Apuesta no válida");
                        }
                        break;
                    case "2":
                        vistaConsola.mostrarMensaje("El jugador " + participante.getNombre() + " no participa de la ronda");
                        accionValida = true;
                        break;
                    default:
                        vistaConsola.mostrarMensaje("Opción inválida");
                        break;
                }
            }
        }
    }


    public boolean verificarApuesta(Participante participante, String apuesta){
        if (!apuesta.matches("\\d+")){
            return false;
        }
        double apuestaAux = Double.parseDouble(apuesta);

        return ((apuestaAux <= participante.getSaldoJugador()) && apuestaAux > 0);


    }



    public void faseRonda() {
        while (!modeloRonda.getColaTurnos().isEmpty()) {

            boolean opcionValida = false;

            while (!opcionValida) {
                if (modeloRonda.verificarDividir() && modeloRonda.jugadorTieneUnaSolaMano()) {
                    vistaConsola.vistaRondaExtendida(modeloRonda.participanteConTurno());
                    String opcion = vistaConsola.solicitarDato("opcion");

                    switch (opcion) {
                        case "1" -> {
                            modeloRonda.participantePideCarta();
                            opcionValida = true;
                        }
                        case "2" -> {
                            modeloRonda.participanteSePlanta();
                            opcionValida = true;
                        }
                        case "3" -> {
                            modeloRonda.jugadorDivide();
                            opcionValida = true;
                        }
                        default -> {
                            vistaConsola.mostrarMensaje("Opcion invalida");
                        }
                    }

                } else {
                    vistaConsola.vistaRonda(modeloRonda.participanteConTurno());
                    String opcion = vistaConsola.solicitarDato("opcion");

                    switch (opcion) {
                        case "1" -> {
                            modeloRonda.participantePideCarta();
                            opcionValida = true;
                        }
                        case "2" -> {
                            modeloRonda.participanteSePlanta();
                            opcionValida = true;
                        }
                        default -> {
                            vistaConsola.mostrarMensaje("Opcion invalida");
                        }
                    }
                }
            }
        }
    }


    public void faseFinal(){
        modeloRonda.faseCrupier();
    }

    public void iniciarRonda() {
        while (true){
            vistaConsola.esperarEnter();
            vistaConsola.limpiarConsola();
            vistaConsola.vistaMesa(modeloPartida.getListaParticipantes(), modeloRonda.getCrupier());
            String opcion = vistaConsola.solicitarDato("opcion");
            switch (opcion) {
                case "0" -> {
                    vistaConsola.mostrarMensaje("Saliendo de la mesa....");
                    vistaConsola.limpiarConsola();
                    return;
                }
                case "1" -> {
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
        vistaConsola.limpiarConsola();
        String usuario = vistaConsola.solicitarDato("usuario");
        if(verificarUsuario(usuario)){
            for (Participante participante: modeloPartida.getListaParticipantes()){
                if (participante.getNombre().equals(usuario)){
                    vistaConsola.mostrarMensaje("El jugador ya se encuentra registrado");
                    return;
                }
            }
            vistaConsola.limpiarConsola();
            String dinero = vistaConsola.solicitarDato("dinero: ");
            if(verificarDinero(dinero)){
                Jugador nuevoJugador = new Jugador(usuario);
                nuevoJugador.agregarDinero(Double.parseDouble(dinero));
                modeloPartida.jugadorSeUne(nuevoJugador);
            }

        }
    }

    public void ingresarDinero(){
        String usuario = vistaConsola.solicitarDato("usuario: ");

        if (!verificarUsuario(usuario)){
            vistaConsola.mostrarMensaje("Usuario no valido!");
            return;
        }


        Participante participante = buscarJugador(usuario);

        if (participante == null){
            vistaConsola.mostrarMensaje("Jugador no encontrado");
        } else {
            String dinero = vistaConsola.solicitarDato("dinero: ");

            if (!verificarDinero(dinero)){
                vistaConsola.mostrarMensaje("Dinero no valido");
                return;
            }
            participante.sumarBanca(Double.parseDouble(dinero));
        }

    }

    public Participante buscarJugador(String usuario){
        for(Participante participante: modeloPartida.getListaParticipantes()){
            if (participante.getNombre().equals(usuario)){
                return participante;
            }
        }
        return null;
    }



    public boolean verificarDinero(String dinero){
        if (Double.parseDouble(dinero) <= 0){
            vistaConsola.mostrarMensaje("Debe ingresar un monto mayor a 0");
            return false;
        }

        return true;

    }

    public boolean verificarUsuario(String usuario) {
        String usuarioAux = usuario.trim();

        if (usuarioAux.isEmpty()) {
            vistaConsola.mostrarMensaje("Debe ingresar un usuario");
            return false;
        }

        if (usuarioAux.contains(" ")) {
            vistaConsola.mostrarMensaje("El usuario no puede contener espacios");
            return false;
        }

        if (usuarioAux.matches("\\d+")) {
            vistaConsola.mostrarMensaje("El usuario no puede ser solo numeros");
            return false;
        }

        if (!usuarioAux.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
            vistaConsola.mostrarMensaje("El usuario deben ser solo letras");
            return false;
        }

        return true;
    }








    @Override
    public void actualizar(Object o) {
        switch (o) {
            case EVENTO_PARTIDA.JUGADOR_UNIDO -> {
                vistaConsola.limpiarConsola();
                vistaConsola.mostrarMensaje("Jugador se unió");
            }
            case EVENTO_PARTIDA.APUESTA_RECIBIDA -> vistaConsola.mostrarMensaje("Apuesta recibida");
            case EVENTO_PARTIDA.PARTIDA_INICIADA, EVENTO_RONDA.CARTA_REPARTIDA, EVENTO_RONDA.MANO_DIVIDIDA -> {
                vistaConsola.limpiarConsola();
                vistaConsola.mostrarManoCrupier(modeloRonda.getCrupier());
                vistaConsola.mostrarManoJugadores(modeloRonda.getColaTurnos());
            }
            case EVENTO_RONDA.JUGADOR_SE_PASA -> {
                vistaConsola.limpiarConsola();
                vistaConsola.mostrarMensaje("El jugador se pasó!");
                vistaConsola.mostrarManoCrupier(modeloRonda.getCrupier());
                vistaConsola.mostrarManoJugadores(modeloRonda.getColaTurnos());
            }
            case EVENTO_RONDA.JUGADOR_SE_PLANTA -> vistaConsola.mostrarMensaje("Jugador se plantó");
            case EVENTO_RONDA.CRUPIER_JUEGA -> vistaConsola.mostrarManoCrupier(modeloRonda.getCrupier());
            case EVENTO_RONDA.BANCA_GANA -> vistaConsola.mostrarMensaje("La banca gana. ");
            case EVENTO_RONDA.RONDA_TERMINADA ->{
                vistaConsola.mostrarMensaje("Fin de ronda..");
                vistaConsola.mostrarJugadoresFinales(modeloRonda.getParticipantesActivosFinalRonda());
            }
            default -> throw new IllegalStateException("Unexpected value: " + o);
        }


    }
}

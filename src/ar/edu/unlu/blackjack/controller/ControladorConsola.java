package ar.edu.unlu.blackjack.controller;


import ar.edu.unlu.blackjack.model.Jugador;
import ar.edu.unlu.blackjack.model.Participante;
import ar.edu.unlu.blackjack.model.Partida;
import ar.edu.unlu.blackjack.observer.Observador;
import ar.edu.unlu.blackjack.view.VistaConsola;
import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;
import ar.edu.unlu.model.excepciones.PuntajeMayorA21Excepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

public class ControladorConsola implements Observador {
    private Partida modelo;
    private VistaConsola vistaConsola;

    public ControladorConsola(Partida modelo, VistaConsola vistaConsola) {
        this.modelo = modelo;
        this.vistaConsola = vistaConsola;
        this.modelo.agregarObservador(this);
    }

    public boolean loguearse(){
        while (true){
            vistaConsola.login();
            int opcion = vistaConsola.obtenerOpcion();
            switch (opcion){
                case 1:
                    return true;
                case 0:
                    return false;
                default:
                    vistaConsola.mostrarMensaje("Opcion no valida");
            }
        }

    }

    public void iniciar() throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion, ApuestaMayorAlSaldoExcepcion {
        if (loguearse()) {
            unirJugador();
            while (true) {
                vistaConsola.mostrarMenu();
                int opcion = vistaConsola.obtenerOpcion();

                switch (opcion) {
                    case 1:
                        iniciarMesa();
                        break;
                    case 2:
                        ingresarDinero(modelo.getListaParticipantes().getFirst());
                        break;
                    case 3:
                        vistaConsola.mostrarDatos(modelo.getListaParticipantes().getFirst());
                        break;
                    case 0:
                        vistaConsola.mostrarMensaje("Adios!");
                        return;
                    default:
                        vistaConsola.mostrarMensaje("Opcion no valida");
                        break;
                }
            }
        }
        vistaConsola.mostrarMensaje("Adios!");
    }

    public void iniciarMesa() throws ApuestaMayorAlSaldoExcepcion, PartidaSinJugadoresExcepcion, RondaVaciaExcepcion {
        while (true) {
            vistaConsola.mostrarMesa();
            int opcion = vistaConsola.obtenerOpcion();

            switch (opcion){
                case 1:
                    try {
                        modelo.recibirApuesta(modelo.getListaParticipantes().getFirst(), vistaConsola.solicitarDato());
                        iniciarRonda();
                    } catch (ApuestaMayorAlSaldoExcepcion | PuntajeMayorA21Excepcion e ){
                        vistaConsola.mostrarMensaje(e.getMessage());
                    }
                    break;
                case 0:
                    vistaConsola.mostrarMensaje("Saliendo de la mesa...");
                    return;
            }

        }
    }

    public void iniciarRonda() throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion, PuntajeMayorA21Excepcion {
        boolean sePaso = false;
        modelo.iniciarPartida();
        while (!modelo.getRonda().getColaTurnos().isEmpty()){  // se ejecuta mientras la cola tenga algun jugador
            while (!sePaso){
                actualizar();
                vistaConsola.vistaRonda();
                int opcion = vistaConsola.obtenerOpcion();
                switch (opcion){
                    case 1:
                        modelo.getRonda().participantePideCarta();
                        break;
                    case 2:
                        modelo.getRonda().participanteSePlanta();
                        break;
                    default:
                        vistaConsola.mostrarMensaje("Opcion incorrecta");
                        break;
                }
                sePaso = modelo.getRonda().participanteSePaso();
            }

        }



    }

    public void unirJugador() {
        Jugador nuevoJugador = new Jugador(vistaConsola.solicitarDato("usuario"));
        modelo.jugadorSeUne(nuevoJugador);
    }

    public void ingresarDinero(Participante participante){
        participante.sumarBanca(vistaConsola.solicitarDato());
    }




    @Override
    public void actualizar() {
        vistaConsola.mostrarManoCrupier(modelo.getRonda().getCrupier());

        vistaConsola.mostrarManoJugadores(modelo.getRonda().getColaTurnos());

    }
}

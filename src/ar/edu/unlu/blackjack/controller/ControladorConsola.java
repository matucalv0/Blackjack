package ar.edu.unlu.blackjack.controller;

import ar.edu.unlu.blackjack.model.Jugador;
import ar.edu.unlu.blackjack.model.Partida;
import ar.edu.unlu.blackjack.observer.Observador;
import ar.edu.unlu.blackjack.view.VistaConsola;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

public class ControladorConsola implements Observador {
    private Partida modelo;
    private VistaConsola vistaConsola;

    public ControladorConsola(Partida modelo, VistaConsola vistaConsola) {
        this.modelo = modelo;
        this.vistaConsola = vistaConsola;
        this.modelo.agregarObservador(this);
    }

    public void iniciar() throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion {
        while (true){
            vistaConsola.mostrarMenu();
            int opcion = vistaConsola.obtenerOpcion();

            switch (opcion) {
                case 1:
                    iniciarPartida();
                    break;
                case 2:
                    vistaConsola.mostrarMensaje("Adios!");
                    return;


            }


        }
    }

    private void iniciarPartida() throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion {
        Jugador nuevoJugador = new Jugador(vistaConsola.solicitarDato("nombre"));
        modelo.jugadorSeUne(nuevoJugador);

        modelo.iniciarPartida();

    }


    @Override
    public void actualizar() {
        vistaConsola.mostrarManoJugadores(modelo.getRonda().getColaTurnos());
        vistaConsola.mostrarManoCrupier(modelo.getRonda().getCrupier());

    }
}

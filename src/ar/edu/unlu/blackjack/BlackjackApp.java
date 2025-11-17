package ar.edu.unlu.blackjack;

import ar.edu.unlu.blackjack.controller.ControladorConsola;
import ar.edu.unlu.blackjack.model.Partida;
import ar.edu.unlu.blackjack.model.Ronda;
import ar.edu.unlu.blackjack.view.VistaConsola;
import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BlackjackApp {
    public static void main(String[] args) throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion, ApuestaMayorAlSaldoExcepcion {
        Partida modeloPartida = new Partida();
        Ronda modeloRonda = modeloPartida.getRonda();  //le paso la referencia a la ronda interna a la partida
        VistaConsola vista = new VistaConsola();

        ControladorConsola controladorConsola = new ControladorConsola(modeloPartida, modeloRonda, vista);

        controladorConsola.loguearse();

        System.out.println("Blackjack");
    }
}
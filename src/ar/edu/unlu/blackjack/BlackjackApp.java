package ar.edu.unlu.blackjack;

import ar.edu.unlu.blackjack.controller.ControladorConsola;
import ar.edu.unlu.blackjack.model.Partida;
import ar.edu.unlu.blackjack.view.VistaConsola;
import ar.edu.unlu.model.excepciones.ApuestaMayorAlSaldoExcepcion;
import ar.edu.unlu.model.excepciones.PartidaSinJugadoresExcepcion;
import ar.edu.unlu.model.excepciones.RondaVaciaExcepcion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BlackjackApp {
    public static void main(String[] args) throws PartidaSinJugadoresExcepcion, RondaVaciaExcepcion, ApuestaMayorAlSaldoExcepcion {
        Partida modelo = new Partida();
        VistaConsola vista = new VistaConsola();

        ControladorConsola controladorConsola = new ControladorConsola(modelo, vista);

        controladorConsola.iniciar();

        System.out.println("Blackjack");
    }
}
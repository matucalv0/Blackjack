package ar.edu.unlu.blackjackfx;

import ar.edu.unlu.blackjackfx.controller.ControladorConsola;
import ar.edu.unlu.blackjackfx.model.Partida;
import ar.edu.unlu.blackjackfx.model.Ronda;
import ar.edu.unlu.blackjackfx.view.VistaConsola;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BlackjackApp {
    public static void main(String[] args)  {
        Partida modeloPartida = new Partida();
        Ronda modeloRonda = modeloPartida.getRonda();  //le paso la referencia a la ronda interna a la partida
        VistaConsola vista = new VistaConsola();

        ControladorConsola controladorConsola = new ControladorConsola(modeloPartida, modeloRonda, vista);

        controladorConsola.loguearse();

        System.out.println("Blackjack");
    }
}
package ar.edu.unlu.blackjackfx.view;

import ar.edu.unlu.blackjackfx.BlackjackAppGUI;
import ar.edu.unlu.blackjackfx.controller.ControladorGUI;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public abstract class VistaGUI {
    protected BlackjackAppGUI app;
    protected ControladorGUI controlador;

    public VistaGUI(BlackjackAppGUI app){
        this.app = app;
    }

    public void setControlador(ControladorGUI controlador){
        this.controlador = controlador;
    }

    protected void transicion(Node nodo){
        FadeTransition ft = new FadeTransition(Duration.millis(500), nodo);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

}

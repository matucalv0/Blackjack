package ar.edu.unlu.blackjackfx.view;

import ar.edu.unlu.blackjackfx.BlackjackAppGUI;
import ar.edu.unlu.blackjackfx.controller.ControladorGUI;

public abstract class VistaGUI {
    protected BlackjackAppGUI app;
    protected ControladorGUI controlador;

    public VistaGUI(BlackjackAppGUI app){
        this.app = app;
    }

    public void setControlador(ControladorGUI controlador){
        this.controlador = controlador;
    }

}

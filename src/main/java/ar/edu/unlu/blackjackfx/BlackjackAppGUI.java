package ar.edu.unlu.blackjackfx;

import ar.edu.unlu.blackjackfx.controller.ControladorGUI;
import ar.edu.unlu.blackjackfx.model.Partida;
import ar.edu.unlu.blackjackfx.model.Ronda;
import ar.edu.unlu.blackjackfx.view.VistaGUI;
import ar.edu.unlu.blackjackfx.view.VistaInicio;
import ar.edu.unlu.blackjackfx.view.VistaMesa;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BlackjackAppGUI extends Application {
    Partida modeloPartida = new Partida();
    Ronda modeloRonda = modeloPartida.getRonda();
    VistaGUI vista;



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new VistaInicio(this);

        ControladorGUI controladorGUI = new ControladorGUI(modeloPartida, modeloRonda, vista);
        vista.setControlador(controladorGUI);

        FXMLLoader fxmlLoader = new FXMLLoader(BlackjackAppGUI.class.getResource("hello-view.fxml"));

        fxmlLoader.setController(vista);

        //esto inyecta los nodos @FXML en VistaInicio
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 900, 700);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Blackjack");
        stage.setScene(scene);
        stage.show();
    }


    public Partida getModeloPartida(){
        return this.modeloPartida;
    }

}

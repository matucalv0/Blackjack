package ar.edu.unlu.blackjackfx;

import ar.edu.unlu.blackjackfx.controller.ControladorInicioGUI;
import ar.edu.unlu.blackjackfx.controller.ControladorMesaGUI;
import ar.edu.unlu.blackjackfx.model.Partida;
import ar.edu.unlu.blackjackfx.model.Ronda;
import ar.edu.unlu.blackjackfx.view.VistaInicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BlackjackAppGUI extends Application {
    Partida modeloPartida = new Partida();
    Ronda modeloRonda = modeloPartida.getRonda();
    VistaInicio vista;



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new VistaInicio(this);

        ControladorInicioGUI controladorInicioGUI = new ControladorInicioGUI(modeloPartida, modeloRonda, vista);
        vista.setControlador(controladorInicioGUI);

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

package ar.edu.unlu.blackjackfx.view;

import ar.edu.unlu.blackjackfx.BlackjackAppGUI;
import ar.edu.unlu.blackjackfx.controller.ControladorInicioGUI;
import ar.edu.unlu.blackjackfx.controller.ControladorMesaGUI;

import ar.edu.unlu.blackjackfx.model.Participante;
import ar.edu.unlu.blackjackfx.model.Partida;
import ar.edu.unlu.blackjackfx.model.Ronda;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;



public class VistaInicio {
    private ControladorInicioGUI controlador;
    private BlackjackAppGUI app;
    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    private HBox titleBar;
    @FXML
    private ListView<String> listaJugadores;
    @FXML
    private Label lbList;
    @FXML
    Button btnIniciar;


    public VistaInicio(BlackjackAppGUI app) {
        this.app = app;
    }

    public void setControlador(ControladorInicioGUI controlador){
        this.controlador = controlador;
    }

    @FXML
    private void initialize(){
        lbList.setVisible(false);
        listaJugadores.setVisible(false);  // invisible al inicio
        listaJugadores.setManaged(false);

    }


    public void agregarJugador(ArrayList<Participante> jugadores) {
        Platform.runLater(() -> {
            ObservableList items = FXCollections.observableArrayList();
            for (Participante jugador : jugadores) {
                items.add(jugador.toString());
            }
            listaJugadores.setItems(items);
        });


        if (!listaJugadores.isVisible()){
            btnIniciar.setVisible(true);
            lbList.setVisible(true);
            listaJugadores.setVisible(true);
            listaJugadores.setManaged(true);

            transicion(btnIniciar);
            transicion(listaJugadores);
            transicion(lbList);
        }
    }

    protected void transicion(Node nodo){
        FadeTransition ft = new FadeTransition(Duration.millis(500), nodo);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    @FXML
    private void iniciarMesa() throws IOException {
        // Modelo
        Partida modeloPartida = app.getModeloPartida();
        Ronda modeloRonda = modeloPartida.getRonda();

        // Vista y controlador MVC
        VistaMesa vistaMesa = new VistaMesa(app);
        ControladorMesaGUI controladorMesaGUI = new ControladorMesaGUI(modeloPartida, modeloRonda, vistaMesa);
        vistaMesa.setControlador(controladorMesaGUI);

        // Cargamos FXML y asociamos el controlador
        FXMLLoader fxmlLoader = new FXMLLoader(BlackjackAppGUI.class.getResource("mesa.fxml"));
        fxmlLoader.setController(vistaMesa);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 800);



        // Creamos el Stage y mostramos la ventana
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Mesa de Blackjack");
        stage.show();
    }


    @FXML
    private void ingresarUsuario() {
        if (!controlador.verificarCantidadJugadores()){
            return;
        }

        String nombre = pedirTexto("Ingrese usuario");
        if (nombre == null || nombre.isBlank()) return;

        String dineroStr = pedirTexto("Ingrese dinero inicial");
        if (dineroStr == null || dineroStr.isBlank()) return;

        try {
            double dinero = Double.parseDouble(dineroStr);
            controlador.agregarUsuario(nombre, dinero);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El valor ingresado no es un número válido.");
        }
    }





    private String pedirTexto(String titulo) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.initStyle(StageStyle.UNDECORATED);

        // Creamos el TextField con placeholder real
        TextField textField = new TextField();
        textField.setPromptText(titulo); // placeholder
        textField.setFocusTraversable(true); // permite que el foco llegue pero no borre el prompt

        // Contenedor para el TextField
        VBox vbox = new VBox(textField);
        vbox.setSpacing(10);
        dialog.getDialogPane().setContent(vbox);

        // Botones OK / Cancel
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Estilos
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/ar/edu/unlu/blackjackfx/styles/styles.css").toExternalForm()
        );
        dialog.getDialogPane().getStyleClass().add("dialog-cristal");

        Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.getStyleClass().add("boton-cristal");

        Button cancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.getStyleClass().add("boton-cristal");

        // Resultado: devolvemos el texto del TextField
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return textField.getText();
            }
            return null;
        });

        // Abrimos el diálogo y devolvemos el valor
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }




    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void salirAplicacion() {
        Platform.exit();
    }



    @FXML
    private void onTitleBarPressed(MouseEvent e) {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        xOffset = e.getSceneX();
        yOffset = e.getSceneY();
    }

    @FXML
    private void onTitleBarDragged(MouseEvent e) {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.setX(e.getScreenX() - xOffset);
        stage.setY(e.getScreenY() - yOffset);
    }






}

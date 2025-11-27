package ar.edu.unlu.blackjackfx.view;

import ar.edu.unlu.blackjackfx.BlackjackAppGUI;
import ar.edu.unlu.blackjackfx.controller.ControladorInicioGUI;
import ar.edu.unlu.blackjackfx.controller.ControladorMesaGUI;
import ar.edu.unlu.blackjackfx.model.*;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class VistaMesa {
    private int turnoMesa = 0;
    private boolean primerClickApuesta = false;
    private ControladorMesaGUI controlador;
    private BlackjackAppGUI app;

    @FXML
    Button btn50;
    @FXML
    VBox infoJugador;
    @FXML
    Label lblNombre;
    @FXML
    Label lblApuesta;
    @FXML
    Label lblSaldo;
    @FXML
    Button btnApostar;
    @FXML
    HBox cartasJugador;
    @FXML
    Label lblPuntaje;
    @FXML
    HBox fichas;
    @FXML
    HBox cartasCrupier;
    @FXML
    Label lblPuntajeCrupier;
    @FXML
    Label lblAviso;
    @FXML
    StackPane mesaJugador;
    @FXML
    Button btnSalirMesa;


    public VistaMesa(BlackjackAppGUI app) {
        this.app = app;

    }

    public void mostrarAviso(Participante participante, String mensaje){
        lblAviso.setText("El jugador " + participante.getNombre() + " " + mensaje);
        lblAviso.setVisible(true);

        transicion(lblAviso);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> lblAviso.setVisible(false));
        pause.play();
    }

    public void setControlador(ControladorMesaGUI controlador) {
        this.controlador = controlador;
    }


    @FXML
    private void initialize() {
        mostrarJugadorConTurno(app.getModeloPartida().getListaParticipantes().getFirst());
    }

    public void refrescarVista(){
        Platform.runLater(() -> cartasJugador.getChildren().clear());
        Platform.runLater(() -> cartasCrupier.getChildren().clear());
        fichas.setDisable(false);
        lblPuntaje.setVisible(false);
        lblPuntajeCrupier.setVisible(false);
    }

    public void pasar(){
        controlador.pasarFaseApuesta(turnoMesa);
    }



    public void apostar() {
        btnApostar.setVisible(false);
        primerClickApuesta = false;
        controlador.jugadorApuesta(turnoMesa);  //aumenta el turno despues de que el jugador apuesta

    }

    public void plantarse(){
        controlador.plantarse();
    }

    public void salirDeMesa(){
        Stage stage = (Stage) btnSalirMesa.getScene().getWindow();
        stage.close();
    }

    public void mostrarResultados(ArrayList<Participante> jugadores) {
        StringBuilder resultado = new StringBuilder("Resultados de la ronda:\n\n");

        for (Participante p : jugadores) {
            switch (p.getEstado()){
                case ESTADO_RONDA.GANO -> resultado.append("✔ ").append(p.getNombre()).append(" ganó\n");
                case ESTADO_RONDA.PERDIO -> resultado.append("✘ ").append(p.getNombre()).append(" perdió\n");
                case ESTADO_RONDA.EMPATO -> resultado.append("➖ ").append(p.getNombre()).append(" empató\n");
                case null, default -> {}
            }
        }

        mostrarToast(resultado.toString());
    }


    public void mostrarToast(String mensaje) {
        Label toast = new Label(mensaje);
        toast.setStyle(
                "-fx-background-color: rgba(0,0,0,0.8);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10px 20px;" +
                        "-fx-background-radius: 10px;"
        );
        toast.setOpacity(0); // Comienza invisible

        // Lo agregamos al stackPane (encima de todo)
        mesaJugador.getChildren().add(toast);

        // Animación de fade in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toast);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Pausa visible
        PauseTransition stay = new PauseTransition(Duration.seconds(2));

        // Animación de fade out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toast);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Cuando termina, lo saco del stackpane
        fadeOut.setOnFinished(e -> mesaJugador.getChildren().remove(toast));

        SequentialTransition seq = new SequentialTransition(fadeIn, stay, fadeOut);
        seq.play();
    }



    public void apuesta50() {
        if (!primerClickApuesta) {
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(50, turnoMesa);
    }

    public void apuesta100() {
        if (!primerClickApuesta) {
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(100, turnoMesa);
    }

    public void apuesta200() {
        if (!primerClickApuesta) {
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(200, turnoMesa);
    }

    public void apuesta500() {
        if (!primerClickApuesta) {
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(500, turnoMesa);
    }

    public void apuesta1000() {
        if (!primerClickApuesta) {
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(1000, turnoMesa);
    }

    public void resetTurnoMesa() {
        turnoMesa = 0;
    }

    public void turnoSiguienteMesa() {
        turnoMesa++;
    }

    public int getTurnoMesa() {
        return turnoMesa;
    }

    public void mostrarCartas(Participante participante) {
        cartasJugador.getChildren().clear();
        ArrayList<Carta> cartas = participante.getMano().getCartas();
        for (Carta carta : cartas) {
            CartaView cartaDibujada = new CartaView(carta.getCaracter(), carta.getPalo(), carta.isEsVisible());
            cartasJugador.getChildren().add(cartaDibujada);
        }
        lblPuntaje.setVisible(true);
        lblPuntaje.setText(participante.puntajeActual() + "");
    }

    public void mostrarCartasCrupier(Crupier crupier) {
        ArrayList<Carta> cartas = crupier.getMano().getCartas();
        for (Carta carta : cartas) {
            CartaView cartaDibujada = new CartaView(carta.getCaracter(), carta.getPalo(), carta.isEsVisible());
            cartasCrupier.getChildren().add(cartaDibujada);
        }
        lblPuntajeCrupier.setVisible(true);
        lblPuntajeCrupier.setText(crupier.puntajeActual()+ "");



    }

    public void ocultarApuestas() {
        fichas.setDisable(true);
        btnApostar.setVisible(false);
    }

    public void activarApuestas() {
        fichas.setDisable(false);
    }

    public void pedirCarta(){
        controlador.agregarCartaJugador();
    }




    public void mostrarJugadorConTurno(Participante participante){
        if (participante == null){
            return;
        }
        String nombre = participante.getNombre();
        double saldo = participante.getSaldoJugador();
        double apuesta = participante.getApuesta().getMonto();
        lblNombre.setText(nombre);
        lblApuesta.setText(apuesta + "");
        lblSaldo.setText(saldo + "");
        mostrarCartas(participante);
    }

    protected void transicion(Node nodo){
        FadeTransition ft = new FadeTransition(Duration.millis(500), nodo);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }







}


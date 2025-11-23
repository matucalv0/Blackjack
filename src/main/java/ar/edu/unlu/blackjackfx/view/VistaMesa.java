package ar.edu.unlu.blackjackfx.view;

import ar.edu.unlu.blackjackfx.BlackjackAppGUI;
import ar.edu.unlu.blackjackfx.model.Apuesta;
import ar.edu.unlu.blackjackfx.model.Participante;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class VistaMesa extends VistaGUI {
    private int turnoMesa = 0;
    private boolean primerClickApuesta = false;

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


    public VistaMesa(BlackjackAppGUI app) {
        super(app);
    }



    @FXML
    private void initialize(){
        mostrarJugadorConTurno(app.getModeloPartida().getListaParticipantes().getFirst());
    }


    public void apostar(){
        controlador.jugadorApuesta(turnoMesa++);  //aumenta el turno despues de que el jugador apuesta

    }


    public void apuesta50(){
        if (!primerClickApuesta){
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(50, turnoMesa);
    }

    public void apuesta100(){
        if (!primerClickApuesta){
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(100, turnoMesa);


    }

    public void apuesta200(){
        if (!primerClickApuesta){
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(200, turnoMesa);
    }

    public void apuesta500(){
        if (!primerClickApuesta){
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(500, turnoMesa);

    }

    public void apuesta1000(){
        if (!primerClickApuesta){
            btnApostar.setVisible(true);
            transicion(btnApostar);
            primerClickApuesta = true;
        }
        controlador.recibirApuesta(1000, turnoMesa);

    }

    public void resetTurnoMesa(){
        turnoMesa = 0;
    }

    public void turnoSiguienteMesa(){
        turnoMesa++;
    }

    public int getTurnoMesa(){
        return turnoMesa;
    }


    public void mostrarJugadorConTurno(Participante participante){
        String nombre = participante.getNombre();
        double saldo = participante.getSaldoJugador();
        double apuesta = participante.getApuesta().getMonto();
        lblNombre.setText(nombre);
        lblApuesta.setText(apuesta + "");
        lblSaldo.setText(saldo + "");
    }







}


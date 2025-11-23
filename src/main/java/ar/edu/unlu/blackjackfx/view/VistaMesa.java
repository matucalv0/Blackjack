package ar.edu.unlu.blackjackfx.view;

import ar.edu.unlu.blackjackfx.BlackjackAppGUI;
import ar.edu.unlu.blackjackfx.model.Apuesta;
import ar.edu.unlu.blackjackfx.model.Participante;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VistaMesa extends VistaGUI {
    private int turnoMesa = 0;

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


    public VistaMesa(BlackjackAppGUI app) {
        super(app);
    }



    @FXML
    private void initialize(){
        mostrarJugadorConTurno(app.getModeloPartida().getListaParticipantes().getFirst());
    }

    public void apuesta50(){
        controlador.recibirApuesta(50, turnoMesa);
    }

    public void apuesta100(){
        controlador.recibirApuesta(100, turnoMesa);
    }

    public void apuesta200(){
        controlador.recibirApuesta(200, turnoMesa);
    }

    public void apuesta500(){
        controlador.recibirApuesta(500, turnoMesa);

    }

    public void apuesta1000(){
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


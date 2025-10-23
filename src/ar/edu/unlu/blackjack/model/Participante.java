package ar.edu.unlu.blackjack.model;

import ar.edu.unlu.model.excepciones.PuntajeMayorA21Excepcion;

public class Participante extends ParticipanteBase  {
    Jugador jugador;
    private Apuesta apuesta;


    public Participante(Jugador jugador) {
        this.jugador = jugador;
        apuesta = new Apuesta();
    }

    public double getSaldoJugador(){
        return jugador.getSaldo();
    }


    public void dividir() {

    }

    public void doblar() {

    }


    @Override
    public void pedirCarta(Carta carta) throws PuntajeMayorA21Excepcion {
        if (this.puntajeActual() > 21){
            throw new PuntajeMayorA21Excepcion("Su puntaje es mayor a 21, no puede pedir mas cartas");
        }

        mano.addCarta(carta);
    }


    public void setApuesta(double monto){
        this.apuesta.setMonto(monto);
    }

    public void restarBanca(double n){
        jugador.getBanca().restarDinero(n);
    }

}

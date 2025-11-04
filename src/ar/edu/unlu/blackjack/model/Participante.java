package ar.edu.unlu.blackjack.model;

public class Participante extends ParticipanteBase {
    private Jugador jugador;
    private Apuesta apuesta;


    public Participante(Jugador jugador) {
        this.jugador = jugador;
        apuesta = new Apuesta();
    }

    public double getSaldoJugador() {
        return jugador.getSaldo();
    }

    public void sumarBanca(double dinero) {
        jugador.agregarDinero(dinero);
    }

    public String getNombre(){
        return jugador.getNombre();
    }


    public void dividir() {

    }

    public void doblar() {

    }



    public Apuesta getApuesta() {
        return apuesta;
    }

    public void setApuesta(double monto) {
        this.apuesta.setMonto(monto);
    }

    public void restarBanca(double n) {
        jugador.getBanca().restarDinero(n);
    }

}

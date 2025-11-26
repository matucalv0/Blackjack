package ar.edu.unlu.blackjackfx.model;

public class Participante extends ParticipanteBase {
    private Jugador jugador;
    private Apuesta apuesta;
    private ESTADO_RONDA estado;


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

    public void gano(){
        this.estado = ESTADO_RONDA.GANO;
    }

    public void perdio(){
        this.estado = ESTADO_RONDA.PERDIO;
    }

    public void empato(){
        this.estado = ESTADO_RONDA.EMPATO;
    }

    public ESTADO_RONDA getEstado(){
        return estado;
    }


    public String getNombre(){
        return jugador.getNombre();
    }

    public void doblarApuesta(){
        restarBanca(apuesta.getMonto());
        apuesta.setMonto(apuesta.getMonto() * 2);
    }

    public void incrementarIndiceMano(){
        this.manoActual++;
    }

    public void resetIndiceMano(){
        this.manoActual = 0;
    }
    public void resetMano(){
        this.getManos().clear();
        this.getManos().add(new Mano());
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

    @Override
    public String toString() {
        return "" + jugador;

    }
}

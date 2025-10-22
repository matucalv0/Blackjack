package ar.edu.unlu.blackjack.model;

public class Crupier extends ParticipanteBase {

    public Crupier() {

    }

    @Override
    public void agregarCarta(Carta carta) {
        if (mano.cantidadCartas() == 1){
            carta.ocultar();
            mano.addCarta(carta);
        } else {
            mano.addCarta(carta);
        }
    }


}

package ar.edu.unlu.blackjack.model;

public class Crupier extends ParticipanteBase {
    @Override
    public void agregarCarta(Carta carta) {
        if (mano.cantidadCartas() == 1){
            carta.ocultar();
            super.agregarCarta(carta);
        } else {
            super.agregarCarta(carta);
        }
    }



}

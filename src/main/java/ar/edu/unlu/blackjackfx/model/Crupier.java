package ar.edu.unlu.blackjackfx.model;

public class Crupier extends ParticipanteBase {
    @Override
    public void agregarCarta(Carta carta) {
        if (super.getMano().cantidadCartas() == 1){
            carta.ocultar();
            super.agregarCarta(carta);
        } else {
            super.agregarCarta(carta);
        }
    }

    public void revelarCarta(){
        super.getMano().getCartas().get(1).revelar();
    }



}

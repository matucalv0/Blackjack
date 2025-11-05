package ar.edu.unlu.blackjack.model;


public class Ronda {
    private Mazo mazo;
    private Crupier crupier;
    private Participante participante;

    public Ronda(){
        mazo = new Mazo();
        crupier = new Crupier();
    }


    public void agregarParticipante(Participante participante){
        this.participante = participante;
    }



    public void finDeRonda(){
        if (hizoBlackjack(crupier)){
            if (hizoBlackjack(participante)){
                devolverDinero(participante);
                return;
            }
        }else {
            if (hizoBlackjack(participante)){
                pagoBlackjack(participante);
                return;
            }
        }

        while (crupier.puntajeActual() < 17){
            crupier.agregarCarta(mazo.repartirCarta());
        }

        evaluarGanador();
    }

    public boolean participanteSePaso(){
        if (participante.getMano().sePaso()){
            return true;
        }

        return false;
    }


    public void participantePideCarta()  {

        if (participante == null){
            return;
        }

        participante.agregarCarta(mazo.repartirCarta());


    }

    public void evaluarGanador(){
        if (crupier.getMano().sePaso()){
            pagoNormal(participante);
            participante.getApuesta().clearApuesta();
            }
         else {
             if (participante.puntajeActual() > crupier.puntajeActual()){
                 pagoNormal(participante);
             } else if (participante.puntajeActual() == crupier.puntajeActual()){
                 devolverDinero(participante);
             }

             participante.getApuesta().clearApuesta();

        }

    }


    public void devolverDinero(Participante participante){
        participante.sumarBanca(participante.getApuesta().getMonto());
    }

    public void pagoNormal(Participante participante){
        double pagoApuesta = (participante.getApuesta().getMonto()) * 2;
        participante.sumarBanca(pagoApuesta);
    }

    public void pagoBlackjack(Participante participante){
        double pagoApuesta = (participante.getApuesta().getMonto()) * 2.25;
        participante.sumarBanca(pagoApuesta);
    }



    public boolean hizoBlackjack(ParticipanteBase participante){
        return (participante.puntajeActual() == 21) && (participante.cantidadCartasEnMano() == 2);
    }


    public void participanteSePlanta(){
        return;
    }



    public void rondaInicial() {

        crupier.agregarCarta(mazo.repartirCarta());

        participante.agregarCarta(mazo.repartirCarta());

        crupier.agregarCarta(mazo.repartirCarta());

        participante.agregarCarta(mazo.repartirCarta());


    }


    public Mazo getMazo() {
        return mazo;
    }

    public Crupier getCrupier() {
        return crupier;
    }
}

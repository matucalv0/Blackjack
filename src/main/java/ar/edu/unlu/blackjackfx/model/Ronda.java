package ar.edu.unlu.blackjackfx.model;

import ar.edu.unlu.blackjackfx.observer.Observable;
import ar.edu.unlu.blackjackfx.observer.Observador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ronda implements Observable {
    private ArrayList<Observador> observadores = new ArrayList<>();
    private Queue<Participante> colaTurnos = new LinkedList<>();
    private ArrayList<Participante> participantesActivosFinalRonda = new ArrayList<>();
    private Mazo mazo;
    private Crupier crupier;

    public Ronda(){
        mazo = new Mazo();
        crupier = new Crupier();
    }


    public void agregarJugadorRonda(Participante jugadorRonda){
        colaTurnos.add(jugadorRonda);
    }

    public void limpiarRonda(){
        participantesActivosFinalRonda.clear();
    }

    public void limpiarManoCrupier(){
        crupier.getManos().getFirst().vaciarMano();
    }

    public void gestionBlackjack(){
        ArrayList<Participante> jugadoresConBlackjack = new ArrayList<>();

        for (Participante participante: participantesActivosFinalRonda){
            if (hizoBlackjack(participante)){
                jugadoresConBlackjack.add(participante);
                if (hizoBlackjack(crupier)){
                    devolverDinero(participante);
                } else {
                    pagoBlackjack(participante);
                }
            }
        }

        for (Participante participante: jugadoresConBlackjack){
            participantesActivosFinalRonda.remove(participante);  //saco a los jugadores que hicieron blackjack
        }
    }




    public void finDeRonda(){
        pagarDivision();
        gestionBlackjack();
        evaluarGanadores();
        notificar(EVENTO_RONDA.RONDA_TERMINADA, null);
        participantesActivosFinalRonda.clear();
        colaTurnos.clear();


    }

    public void faseCrupier(){
        crupier.revelarCarta();

        while(crupier.puntajeActual() < 17){
            crupier.agregarCarta(mazo.repartirCarta());
        }
        notificar(EVENTO_RONDA.CRUPIER_JUEGA, crupier );
    }


    public void participanteSePaso(){
        notificar(EVENTO_RONDA.JUGADOR_SE_PASA, colaTurnos.poll());
    }

    public boolean jugadorTieneUnaSolaMano(){
        return participanteConTurno().getManos().size() == 1;
    }


    public void participantePideCarta()  {
        Participante participante = participanteConTurno();
        participante.agregarCarta(mazo.repartirCarta());

        if (participante.getMano().sePaso()){
            if ((participante.getManos().size() > 1) && (participante.manoActual == 0)){  //si dividio y es la mano 1, incrementa el indice
                participante.incrementarIndiceMano();
                participante.perdio();
                participanteSePaso();
                return;
            }
            participante.perdio();
            participanteSePaso();
        } else {
            notificar(EVENTO_RONDA.CARTA_REPARTIDA, null);
        }



    }

    public void pagarDivision(){
        ArrayList<Participante> participantes = new ArrayList<>();
        for (Participante participante: participantesActivosFinalRonda){
            if (participante.getManos().size() == 2){
                if (hizoBlackjack(crupier)){   //si hizo blackjack, pierde (no hay blackjack si dividis)
                    participante.perdio();
                    participantes.add(participante);
                    break;
                }
                for( Mano mano: participante.getManos()){
                    if(mano.sePaso()) {   //si se paso la mano, pierde
                        participante.perdio();
                        break;
                    }
                    if((mano.puntaje() > crupier.puntajeActual()) || crupier.getMano().sePaso()){
                        pagoNormalDivision(participante);
                        participante.gano();
                    } else if (mano.puntaje() == crupier.puntajeActual()){
                        devolverDineroDivision(participante);
                        participante.empato();
                    }
                }

                participantes.add(participante);  //agrego a los jugadores que dividieron para despues borrarlos
                participante.getApuesta().clearApuesta();
            }
        }

        for (Participante participante: participantes){
            participante.resetIndiceMano();
            participante.resetMano();
            participantesActivosFinalRonda.remove(participante);  //elimino a todos los jugadores que dividieron
        }

    }

    public void evaluarGanadores(){
        if (participantesActivosFinalRonda.isEmpty()){
            return;
        }

        if (crupier.getMano().sePaso()){
            for (Participante participante: participantesActivosFinalRonda){
                pagoNormal(participante);
                participante.getApuesta().clearApuesta();
                participante.gano();
            }
        } else {
            for (Participante participante: participantesActivosFinalRonda){
                if (participante.puntajeActual() > crupier.puntajeActual()){
                    pagoNormal(participante);
                    participante.gano();
                } else if (participante.puntajeActual() == crupier.puntajeActual()){
                    devolverDinero(participante);
                    participante.empato();
                } else{
                    participante.perdio();
                }
                participante.getApuesta().clearApuesta();
            }
        }


    }


    public void devolverDinero(Participante participante){
        participante.sumarBanca(participante.getApuesta().getMonto());
    }

    public void devolverDineroDivision(Participante participante){
        participante.sumarBanca(participante.getApuesta().getMonto() / 2);
    }

    public void pagoNormal(Participante participante){
        double pagoApuesta = (participante.getApuesta().getMonto()) * 2;
        participante.sumarBanca(pagoApuesta);
    }



    public void pagoNormalDivision(Participante participante) {
        double pagoApuesta = ((participante.getApuesta().getMonto()) * 2) / 2;
        participante.sumarBanca(pagoApuesta);
    }

    public void pagoBlackjack(Participante participante){
        double pagoApuesta = (participante.getApuesta().getMonto()) * 2.50;
        participante.sumarBanca(pagoApuesta);
    }



    public boolean hizoBlackjack(ParticipanteBase participante){
        return (participante.getMano().esBlackjack());
    }


    public void participanteSePlanta(){
        Participante participante = participanteConTurno();
        if ((participante.getManos().size() > 1) && (participante.manoActual == 0)) {  //si dividio y es la mano 1, incrementa el indice
            participante.incrementarIndiceMano();
            notificar(EVENTO_RONDA.JUGADOR_SE_PLANTA, participante);
            return;
        }

        Participante participanteAux = colaTurnos.poll();
        participantesActivosFinalRonda.add(participanteAux);
        notificar(EVENTO_RONDA.JUGADOR_SE_PLANTA, participanteAux);
    }

    public Participante participanteConTurno(){
        return colaTurnos.peek();
    }


    public ArrayList<Participante> getParticipantesActivosFinalRonda() {
        return participantesActivosFinalRonda;
    }

    public void rondaInicial()  {
        crupier.agregarCarta(mazo.repartirCarta());

        for (Participante participante : colaTurnos) {
            participante.agregarCarta(mazo.repartirCarta());

        }

        crupier.agregarCarta(mazo.repartirCarta());

        for (Participante participante : colaTurnos) {
            participante.agregarCarta(mazo.repartirCarta());
        }

        notificar(EVENTO_PARTIDA.PARTIDA_INICIADA, colaTurnos);

    }

    public void dividirMano(){
        Participante participante = participanteConTurno();
        Carta c1 = participante.getMano().getCartas().removeFirst();

        Mano manoNueva = new Mano();
        manoNueva.addCarta(c1);

        participante.agregarMano(manoNueva);
    }

    public void jugadorDivide(){
        Participante participante = participanteConTurno();
        participante.doblarApuesta();
        dividirMano();
        notificar(EVENTO_RONDA.MANO_DIVIDIDA, null);
    }

    public boolean verificarDividir(){
        Participante participante = participanteConTurno();
        ArrayList<Carta> cartas = participante.getMano().getCartas();

        if (cartas.size() != 2){
            return false;
        }

        if (Double.compare(participante.getSaldoJugador(), participante.getApuesta().getMonto()) < 0){  //no puede dividir porque no puede doblar la apuesta
            return false;
        }

        int carta1 = cartas.getFirst().getValor();
        int carta2 = cartas.get(1).getValor();

        return carta1 == carta2;

    }

    public Queue<Participante> getColaTurnos(){
        return colaTurnos;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public Crupier getCrupier() {
        return crupier;
    }

    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);


    }

    @Override
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);

    }

    @Override
    public void notificar(Object evento, Object data) {
        for (Observador observador: observadores){
            observador.actualizar(evento, data);
        }

    }
}

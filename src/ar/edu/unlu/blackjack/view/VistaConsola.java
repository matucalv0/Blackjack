package ar.edu.unlu.blackjack.view;

import ar.edu.unlu.blackjack.model.Carta;
import ar.edu.unlu.blackjack.model.Crupier;
import ar.edu.unlu.blackjack.model.Mano;
import ar.edu.unlu.blackjack.model.Participante;
import ar.edu.unlu.blackjack.observer.Observador;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("------- Blackjack -------");
        System.out.println("1. Unirme a la mesa ");
        System.out.println("2. Salir");
        System.out.println("Seleccione una opcion: ");

    }

    public int obtenerOpcion() {
        return sc.nextInt();
    }

    public void mostrarManoJugadores(Queue<Participante> jugadoresRonda) {
        int i = 0;
        for (Participante participante : jugadoresRonda) {
            i++;
            Mano mano = participante.getMano();
            System.out.printf("Jugador %d: ", i);
            for (int j = 0; j < mano.getCartas().size(); j++) {
                System.out.printf("%s de %s", mano.getCartas().get(j).getCaracter(), mano.getCartas().get(j).getPalo());
            }
            System.out.printf("[%d]", mano.puntaje());
        }
    }

    public void mostrarManoCrupier(Crupier crupier) {
        Mano mano = crupier.getMano();
        System.out.println("Crupier: ");
        for (int j = 0; j < mano.getCartas().size(); j++) {
            System.out.printf("%s de %s", mano.getCartas().get(j).getCaracter(), mano.getCartas().get(j).getPalo());
        }
        System.out.printf("[%d]", mano.puntaje());

    }



    public String solicitarDato(String nombreDelDato){
        System.out.println("Ingrese su" + nombreDelDato);
        return sc.nextLine();
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }



}

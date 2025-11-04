package ar.edu.unlu.blackjack.view;

import ar.edu.unlu.blackjack.LimpiarConsola;
import ar.edu.unlu.blackjack.model.Carta;
import ar.edu.unlu.blackjack.model.Crupier;
import ar.edu.unlu.blackjack.model.Mano;
import ar.edu.unlu.blackjack.model.Participante;
import ar.edu.unlu.blackjack.observer.Observador;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);

    public void login(){
        LimpiarConsola.limpiar();
        System.out.println("---------- LOGIN ---------");
        System.out.println("1. Loguearme");
        System.out.println("0. Salir");
        System.out.println("Seleccione una opcion: ");
    }

    public void mostrarMenu() {
        LimpiarConsola.limpiar();
        System.out.println("------- Blackjack -------");
        System.out.println("1. Unirme a la mesa ");
        System.out.println("2. Ingresar dinero ");
        System.out.println("3. Mis datos ");
        System.out.println("0. Salir");
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
            System.out.printf("\n %s: ", participante.getNombre());
            for (int j = 0; j < mano.getCartas().size(); j++) {
                System.out.printf("|%s de %s| ", mano.getCartas().get(j).getCaracter(), mano.getCartas().get(j).getPalo());
            }
            System.out.printf("[%d] ", mano.puntaje());
        }
    }

    public void mostrarManoCrupier(Crupier crupier) {
        Mano mano = crupier.getMano();
        System.out.println("Crupier: ");
        for (int j = 0; j < mano.getCartas().size(); j++) {
            if (mano.getCartas().get(j).isEsVisible()){
                System.out.printf("|%s de %s| ", mano.getCartas().get(j).getCaracter(), mano.getCartas().get(j).getPalo());
            } else {
                System.out.println("|??| ");
            }

        }
        System.out.printf("[%d]", mano.puntaje());

    }

    public void mostrarDatos(Participante participante){
        LimpiarConsola.limpiar();
        System.out.printf("Usuario: %s \n Dinero: %f%n", participante.getNombre(), participante.getSaldoJugador());
        esperarEnter();
        sc.nextLine();

    }

    public void mostrarMesa(){
        System.out.println("--------------- MESA ----------------");
        System.out.println("1. Ingresar apuesta ");
        System.out.println("0. Salir de la mesa");
        System.out.println("Seleccione una opcion: ");
    }

    public void mostrarJugadoresEnLaMesa(ArrayList<Participante> participantes){
        for (Participante participante: participantes){
            System.out.println(participante.getNombre());
        }

    }

    public void vistaRonda(){
        System.out.println("\n1. Pedir carta ");
        System.out.println("2. Plantarse \n");
    }


    public String solicitarDato(String nombreDelDato){
        LimpiarConsola.limpiar();
        System.out.println("Ingrese su " + nombreDelDato);
        sc.nextLine();
        return sc.nextLine();
    }

    public Double solicitarDato(){
        LimpiarConsola.limpiar();
        System.out.println("Cuanto dinero desea ingresar? ");
        sc.nextLine();
        return sc.nextDouble();
    }

    public void mostrarMensaje(String mensaje){
        LimpiarConsola.limpiar();
        System.out.println(mensaje);
    }

    public void esperarEnter(){
        System.out.println("Presioná enter para continuar...");

        sc.nextLine();

    }



}

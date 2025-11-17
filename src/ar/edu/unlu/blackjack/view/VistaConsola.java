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
        System.out.println("---------- REGISTRO JUGADORES ---------");
        System.out.println("1. Ingresar usuario ");
        System.out.println("2. Ir a la mesa");
        System.out.println("0. Salir");
        System.out.println("Seleccione una opcion: ");
    }

    public void mostrarMenu() {
        System.out.println("------- Blackjack -------");
        System.out.println("1. Unirme a la mesa ");
        System.out.println("2. Ingresar dinero ");
        System.out.println("3. Mis datos ");
        System.out.println("0. Salir");
        System.out.println("Seleccione una opcion: ");

    }

    public void vistaMesa(ArrayList<Participante> jugadores, Crupier crupier){
        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│                    MESA                         │");
        System.out.println("├─────────────────────────────────────────────────┤");

        mostrarJugadoresEnLaMesa(jugadores);

        System.out.println("└─────────────────────────────────────────────────┘");

        System.out.println("1. Iniciar ronda de apuestas");
        System.out.println("0. Salir");
    }


    public int obtenerOpcion() {
        return sc.nextInt();
    }

    public void mostrarJugadoresFinales(ArrayList<Participante> jugadoresFinal){
        for (Participante participante : jugadoresFinal) {
            ArrayList<Mano> manos = participante.getManos();
            for (Mano mano : manos) {
                System.out.println();
                System.out.println("╔══════════════════════════════════════════════╗");
                System.out.printf("║ %-44s║%n",
                        participante.getNombre());
                System.out.println("╠══════════════════════════════════════════════╣");
                System.out.println("║ Cartas:                                      ║");
                System.out.println("║                                              ║");

                DibujarCarta.renderCartasEnFila(mano.getCartas());

                System.out.println("║                                              ║");
                System.out.println("╠══════════════════════════════════════════════╣");
                System.out.printf("║ Valor: %-36s║%n", mano.puntaje());
                System.out.println("╚══════════════════════════════════════════════╝");
            }
        }


    }

    public void mostrarManoJugadores(Queue<Participante> jugadoresRonda) {
        for (Participante participante : jugadoresRonda) {
            ArrayList<Mano> manos = participante.getManos();
            for (Mano mano : manos) {
                System.out.println();
                System.out.println("╔══════════════════════════════════════════════╗");
                System.out.printf("║ %-44s║%n",
                        participante.getNombre());
                System.out.println("╠══════════════════════════════════════════════╣");
                System.out.println("║ Cartas:                                      ║");
                System.out.println("║                                              ║");

                DibujarCarta.renderCartasEnFila(mano.getCartas());

                System.out.println("║                                              ║");
                System.out.println("╠══════════════════════════════════════════════╣");
                System.out.printf("║ Valor: %-36s║%n", mano.puntaje());
                System.out.println("╚══════════════════════════════════════════════╝");
            }
        }
    }

    public void mostrarManoCrupier(Crupier crupier) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                   CRUPIER                    ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║ Cartas:                                      ║");
        System.out.println("║                                              ║");

        DibujarCarta.renderCartasEnFila(crupier.getManos().getFirst().getCartas());

        System.out.println("║                                              ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf ("║ Valor actual: %-28s   ║%n", crupier.puntajeActual());
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    public void mostrarDatos(Participante participante){
        System.out.printf("Usuario: %s \n Dinero: %f%n", participante.getNombre(), participante.getSaldoJugador());

    }

    public void mostrarMesa(){
        System.out.println("--------------- MESA ----------------");
        System.out.println("1. Ingresar apuesta ");
        System.out.println("2. Pasar");
        System.out.println("Seleccione una opcion: ");
    }

    public void mostrarJugadoresEnLaMesa(ArrayList<Participante> participantes){
        for (Participante p : participantes){
            System.out.println("│                                                 │");
            System.out.println("│   ┌───────────────────────────────┐             │");
            System.out.printf ("│   │ %-30s│             │%n", p.getNombre());
            System.out.printf ("│   │ Dinero: $%-21s│             │%n",
                    String.format("%,.0f", p.getSaldoJugador()));
            System.out.println("│   └───────────────────────────────┘             │");
        }
    }


    public void vistaRonda(Participante participante){
        System.out.printf("Turno jugador: %s ", participante.getNombre());
        System.out.println("\n1. Pedir carta ");
        System.out.println("2. Plantarse \n");
    }


    public void vistaRondaExtendida(Participante participante){
        System.out.printf("Turno jugador: %s ", participante.getNombre());
        System.out.println("\n1. Pedir carta ");
        System.out.println("2. Plantarse \n");
        System.out.println("3. Dividir \n");

    }


    public String solicitarDato(String nombreDelDato){
        System.out.println("Ingrese su " + nombreDelDato);
        sc.nextLine();
        return sc.nextLine();
    }

    public Double solicitarDato(){
        System.out.println("Cuanto dinero desea ingresar? ");
        return sc.nextDouble();
    }

    public Double solicitarDatoApuesta(){
        System.out.println("Cuanto dinero desea apostar? ");
        return sc.nextDouble();
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }

    public void esperarEnter(){
        System.out.println("Presioná enter para continuar...");

        sc.nextLine();

    }



}

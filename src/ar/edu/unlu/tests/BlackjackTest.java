package ar.edu.unlu.tests;


import ar.edu.unlu.blackjack.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


class BlackjackTest {


    private Jugador jugador;
    private Carta carta;
    private Mazo mazo;
    private Mano mano;
    private Bankroll banca;
    private Crupier crupier;
    private Participante participante;


    @BeforeEach
    void setUp() {
        jugador = new Jugador("Mateo");
        carta = new Carta();
        mazo = new Mazo();
        mano = new Mano();
        participante = new Participante(mano, jugador);

    }


    @Test
    @DisplayName("El jugador debe tener un nombre")
    void verificarQueJugadorTengaNombre() {
        boolean tieneNombre = jugador.getNombre().isEmpty(); //si es true, quiere decir que nombre esta vacio
        assertFalse(tieneNombre);
    }

    @Test
    @DisplayName("El mazo debe tener 52 cartas")
    void verificarQueELMazoTiene52Cartas(){
        assertEquals(52, mazo.getMazo().size());
    }

    @Test
    @DisplayName("El mazo debe contener 13 cartas de trebol, del 1 al 13")
    void verificarTrebolesEnMazo(){
        List<Carta> treboles = new ArrayList<Carta>();
        for (int i = 1; i < 14; i++) {
            treboles.add(new Carta(i, Palo.TREBOL));
        }

        assertTrue(mazo.getMazo().containsAll(treboles));
    }

    @Test
    @DisplayName("El mazo debe contener 13 cartas de corazones, del 1 al 13")
    void verificarCorazonesEnMazo(){
        List<Carta> corazones = new ArrayList<Carta>();
        for (int i = 1; i < 14; i++) {
            corazones.add(new Carta(i, Palo.CORAZON));
        }

        assertTrue(mazo.getMazo().containsAll(corazones));
    }

    @Test
    @DisplayName("El mazo debe contener 13 cartas de picas, del 1 al 13")
    void verificarPicasEnMazo(){
        List<Carta> picas = new ArrayList<Carta>();
        for (int i = 1; i < 14; i++) {
            picas.add(new Carta(i, Palo.TREBOL));
        }

        assertTrue(mazo.getMazo().containsAll(picas));
    }

    @Test
    @DisplayName("El mazo debe contener 13 cartas de diamante, del 1 al 13")
    void verificarDiamantesEnMazo(){
        List<Carta> diamantes = new ArrayList<Carta>();
        for (int i = 1; i < 14; i++) {
            diamantes.add(new Carta(i, Palo.TREBOL));
        }

        assertTrue(mazo.getMazo().containsAll(diamantes));
    }

    @Test
    @DisplayName("Un mazo mezclado debe ser diferente a un mazo inicializado")
    void verificarMazoMezclado(){
        Mazo mazoTemp = new Mazo();
        Stack<Carta> mazoSinMezclar = mazoTemp.getMazo();

        System.out.println(mazoTemp.toString());


        mazo.mezclarMazo(); //mezclo el mazo original

        System.out.println(mazo.toString());


        assertNotEquals(mazo.getMazo(), mazoSinMezclar); //verifico que NO sean iguales


    }

    @Test
    @DisplayName("Cuando un jugador pide una carta, la mano deberia incrementar en 1 su longitud")
    void verificarQueLaManoIncrementaCuandoElJugadorPideUnaCarta(){
        int longitudDeManoAntesDePedir = participante.getMano().getCartas().size();

        participante.pedirCarta(mazo.getMazo().pop());

        int longitudDeManoDespuesDePedir = participante.getMano().getCartas().size();
        System.out.println(participante.getMano());

        assertTrue(longitudDeManoDespuesDePedir > longitudDeManoAntesDePedir);
    }





}

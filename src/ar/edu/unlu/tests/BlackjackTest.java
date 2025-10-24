package ar.edu.unlu.tests;


import ar.edu.unlu.blackjack.model.*;
import ar.edu.unlu.model.excepciones.*;
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
    private Partida partida;


    @BeforeEach
    void setUp() {
        jugador = new Jugador("Mateo");
        carta = new Carta();
        mazo = new Mazo();
        mano = new Mano();
        participante = new Participante(jugador);
        partida = new Partida();

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
    @DisplayName("Cuando se crea una mesa, esta debe tener como minimo un Crupier y un Mazo")
    void verificarExistenciaDeCrupierYmazoCuandoSeCreaUnaMesa(){

        assertTrue(partida.getRonda().getCrupier() != null && partida.getRonda().getMazo() != null);
    }

    @Test
    @DisplayName("Cuando se une un jugador a la partida, deberia incrementar en 1 la lista de jugadores")
    void verificarListaDeJugadoresCuandoUnoSeUne(){
        int numeroJugadores = partida.getListaParticipantes().size();

        partida.jugadorSeUne(jugador);

        int numeroJugadoresDespuesDeAgregarAuno = partida.getListaParticipantes().size();

        assertTrue(numeroJugadoresDespuesDeAgregarAuno > numeroJugadores);

    }

    @Test
    @DisplayName("Si no hay jugadores, lanza una excepcion")
    void verificarQueLanzaExcepcionSiNoHayJugadores(){


        Partida partida1 = new Partida(); // mesa sin jugadores

        assertThrows(PartidaSinJugadoresExcepcion.class, () -> {
            partida.iniciarPartida();});


    }

    @Test
    @DisplayName("Si ningun participante realizo una apuesta, no se puede iniciar la ronda")
    void verificarExcepcionSiNingunJugadorRealizoApuestas() {
        partida.jugadorSeUne(jugador);

        assertThrows(PartidaSinApuestasExcepcion.class, () -> {
            partida.iniciarPartida();});


    }

    @Test
    @DisplayName("Cuando se inicia una partida en una determinada mesa, cada jugador recibe 2 cartas")
    void verificarQueCuandoSeIniciaUnaPartidaCadaJugadorRecibaDosCartas() throws PartidaSinJugadoresExcepcion, PartidaSinApuestasExcepcion, ApuestaMayorAlSaldoExcepcion, RondaVaciaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");
        Jugador jugador2 = new Jugador("Carlos");

        partida.jugadorSeUne(jugador);
        partida.jugadorSeUne(jugador1);
        partida.jugadorSeUne(jugador2);//se unen 3 jugadores

        jugador.getBanca().agregarDinero(200);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 100);



        partida.iniciarPartida();   //inicio la partida

        assertEquals(2, partida.getListaParticipantes().getFirst().getMano().cantidadCartas());

        //verifico que cada jugador tenga 2 cartas

    }


    @Test
    @DisplayName("Si se inicia una ronda, cada jugador debe tener 2 cartas")
    void verificarInicioDeRonda() throws ApuestaMayorAlSaldoExcepcion, PartidaSinJugadoresExcepcion, RondaVaciaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");

        jugador.agregarDinero(500);    //añaden 500 a su cartera
        jugador1.agregarDinero(500);

        partida.jugadorSeUne(jugador);   // se unen los jugadores a la mesa
        partida.jugadorSeUne(jugador1);


        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 250);  // apuestan
        partida.recibirApuesta(partida.getListaParticipantes().get(1), 100 );

        partida.iniciarPartida();

        assertEquals(2, partida.getRonda().getColaTurnos().poll().getMano().cantidadCartas());
        assertEquals(2, partida.getRonda().getColaTurnos().poll().getMano().cantidadCartas());
        assertEquals(2, partida.getRonda().getCrupier().getMano().cantidadCartas());



    }



    @Test
    @DisplayName("Si la mano de un jugador se pasa de 21 pero tiene un As, este mismo debe actuar como un 1 (restar 10)")
    void verificarQueElAsActuaComo1SiLaManoSePasaDe21() throws PuntajeMayorA21Excepcion {


        assertEquals(21, participante.puntajeActual());

    }












}

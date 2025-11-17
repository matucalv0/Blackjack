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

    private Jugador crearJugador(String nombre, double dinero){
        Jugador jugador = new Jugador(nombre);
        jugador.agregarDinero(dinero);
        return jugador;
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
    @DisplayName("Cuando se inicia una partida en una determinada mesa, cada jugador recibe 2 cartas")
    void verificarQueCuandoSeIniciaUnaPartidaCadaJugadorRecibaDosCartas() throws  AccionNoPermitidaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");
        Jugador jugador2 = new Jugador("Carlos");

        partida.jugadorSeUne(jugador);
        partida.jugadorSeUne(jugador1);
        partida.jugadorSeUne(jugador2);//se unen 3 jugadores

        jugador.getBanca().agregarDinero(200);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 100);





        assertEquals(2, partida.getListaParticipantes().getFirst().getMano().cantidadCartas());

        //verifico que cada jugador tenga 2 cartas

    }


    @Test
    @DisplayName("Si se inicia una ronda, cada jugador debe tener 2 cartas")
    void verificarInicioDeRonda() throws AccionNoPermitidaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");

        jugador.agregarDinero(500);    //añaden 500 a su cartera
        jugador1.agregarDinero(500);

        partida.jugadorSeUne(jugador);   // se unen los jugadores a la mesa
        partida.jugadorSeUne(jugador1);


        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 250);  // apuestan
        partida.recibirApuesta(partida.getListaParticipantes().get(1), 100 );



        assertEquals(2, partida.getRonda().getColaTurnos().poll().getMano().cantidadCartas());
        assertEquals(2, partida.getRonda().getColaTurnos().poll().getMano().cantidadCartas());
        assertEquals(2, partida.getRonda().getCrupier().getMano().cantidadCartas());

    }



    @Test
    @DisplayName("Si la mano de un jugador se pasa de 21 pero tiene un As, este mismo debe actuar como un 1 (restar 10)")
    void verificarQueElAsActuaComo1SiLaManoSePasaDe21()  {
        participante.agregarCarta(new Carta(1 , Palo.TREBOL ));
        participante.agregarCarta(new Carta(5, Palo.PICAS));
        participante.agregarCarta(new Carta(10, Palo.CORAZON)); // se pasa de 21, el puntaje = 26. Pero como tiene un As, puntaje = 16



        assertEquals(16, participante.puntajeActual());

    }

    @Test
    @DisplayName("Si el jugador le gana al crupier, se le paga la apuesta")
    void verificarQueIncrementaElSaldoDelJugadorGanador() throws AccionNoPermitidaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");


        partida.jugadorSeUne(jugador1);


        jugador1.getBanca().agregarDinero(1000);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 500);

        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.DIAMANTE));
        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.TREBOL));

        partida.getRonda().getCrupier().agregarCarta(new Carta(10, Palo.CORAZON));
        partida.getRonda().getCrupier().agregarCarta(new Carta(5, Palo.CORAZON));

        partida.getRonda().getParticipantesActivosFinalRonda().add(partida.getListaParticipantes().getFirst()); //agrego el participante para que el metodo evaluarGanadores funcione

        partida.getRonda().evaluarGanadores();  // el jugador gano, asi que su saldo debe haber incrementado a 1500.

        assertEquals(1500, jugador1.getSaldo());

    }


    @Test
    @DisplayName("Si el jugador pierde se le resta su apuesta al saldo")
    void verificarQueDecrementaElSaldoDelJugadorPerdedor() throws AccionNoPermitidaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");


        partida.jugadorSeUne(jugador1);


        jugador1.getBanca().agregarDinero(1000);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 500);

        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.DIAMANTE));
        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.TREBOL));

        partida.getRonda().getCrupier().agregarCarta(new Carta(10, Palo.CORAZON));
        partida.getRonda().getCrupier().agregarCarta(new Carta(1, Palo.CORAZON));

        partida.getRonda().getParticipantesActivosFinalRonda().add(partida.getListaParticipantes().getFirst()); //agrego el participante para que el metodo evaluarGanadores funcione

        partida.getRonda().evaluarGanadores();  // el jugador perdio, su saldo queda igual. Es decir saldo previo a la apuesta - apuesta.

        assertEquals(500, jugador1.getSaldo());

    }


    @Test
    @DisplayName("Si el jugador le empata al crupier, se devuelve la apuesta")
    void verificarQueElSaldoNoSeModificaDelJugadorQueEmpato() throws AccionNoPermitidaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");


        partida.jugadorSeUne(jugador1);


        jugador1.getBanca().agregarDinero(1000);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 500);

        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.DIAMANTE));
        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.TREBOL));

        partida.getRonda().getCrupier().agregarCarta(new Carta(10, Palo.CORAZON));
        partida.getRonda().getCrupier().agregarCarta(new Carta(10, Palo.PICAS));

        partida.getRonda().getParticipantesActivosFinalRonda().add(partida.getListaParticipantes().getFirst()); //agrego el participante para que el metodo evaluarGanadores funcione

        partida.getRonda().evaluarGanadores();  // el jugador empato, se le devuelve la apuesta.

        assertEquals(1000, jugador1.getSaldo());

    }

    @Test
    @DisplayName("SI el jugador hace blackjack, automaticamente no participa mas de la ronda")
    void verificarQueSiUnJugadorHaceBlackjackSeLePagaMasySaleDeLaRonda() throws AccionNoPermitidaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");


        partida.jugadorSeUne(jugador1);


        jugador1.getBanca().agregarDinero(1000);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 500);

        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(1, Palo.DIAMANTE));
        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.TREBOL));

        partida.getRonda().getCrupier().agregarCarta(new Carta(10, Palo.CORAZON));
        partida.getRonda().getCrupier().agregarCarta(new Carta(10, Palo.PICAS));

        partida.getRonda().participanteSePlanta();

        partida.getRonda().finDeRonda();

        assertEquals(1625, jugador1.getSaldo());
        assertEquals(0, partida.getRonda().getColaTurnos().size());

    }


    @Test
    @DisplayName("Si el jugador tiene 21, pero la banca hizo blackjack, gana la banca")
    void verificarQueGanaLaBancaSiHaceBlackjackYAmbosTienen21() throws AccionNoPermitidaExcepcion {
        Jugador jugador1 = new Jugador("Eduardo");


        partida.jugadorSeUne(jugador1);


        jugador1.getBanca().agregarDinero(1000);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 500);

        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.DIAMANTE));
        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.TREBOL));
        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(1, Palo.TREBOL));  //21, no blackjack

        partida.getRonda().getCrupier().agregarCarta(new Carta(10, Palo.CORAZON));  //blackjack
        partida.getRonda().getCrupier().agregarCarta(new Carta(1, Palo.PICAS));

        partida.getRonda().getParticipantesActivosFinalRonda().add(partida.getListaParticipantes().getFirst()); //agrego el participante para que el metodo evaluarGanadores funcione

        partida.getRonda().finDeRonda();  // todos los jugadores que no hayan hecho blackjack deben perder

        assertEquals(500, jugador1.getSaldo());

    }

    @Test
    @DisplayName("Simular escenario: Dos jugadores con blackjack")
    void simularEscenarioDosJugadoresConBlackjack() throws AccionNoPermitidaExcepcion {
        Jugador jugador1 = crearJugador("Eduardo", 1000);
        Jugador jugador2 = crearJugador("Carlos", 1000);

        partida.jugadorSeUne(jugador1);
        partida.jugadorSeUne(jugador2);

        partida.recibirApuesta(partida.getListaParticipantes().getFirst(), 500);
        partida.recibirApuesta(partida.getListaParticipantes().get(1), 500);

        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(1, Palo.CORAZON));
        partida.getListaParticipantes().getFirst().agregarCarta(new Carta(10, Palo.CORAZON));

        partida.getListaParticipantes().get(1).agregarCarta(new Carta(1, Palo.DIAMANTE));
        partida.getListaParticipantes().get(1).agregarCarta(new Carta(10, Palo.DIAMANTE));


        partida.getRonda().participanteSePlanta();
        partida.getRonda().participanteSePlanta();

        partida.getRonda().finDeRonda();

        assertEquals(1625, jugador1.getSaldo());
        assertEquals(1625, jugador2.getSaldo());






    }

















}

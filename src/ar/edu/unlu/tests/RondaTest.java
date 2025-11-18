package ar.edu.unlu.tests;

import ar.edu.unlu.blackjack.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class RondaTest {

    private Partida partida;
    private Ronda ronda;
    private Crupier crupier;
    private Jugador jugador1;
    private Participante participante1;


    private Carta asPicas;
    private Carta reyPicas;
    private Carta diezPicas;
    private Carta diezCorazones;
    private Carta asCorazones;
    private Carta reinaCorazones;
    private Carta sietePicas;
    private Carta cincoPicas;


    @BeforeEach
    void setUp() {

        jugador1 = new Jugador("Nacho");
        jugador1.agregarDinero(500.0);


        partida = new Partida();
        ronda = partida.getRonda();
        crupier = ronda.getCrupier();
        partida.jugadorSeUne(jugador1);
        participante1 = partida.getListaParticipantes().getFirst();



        asPicas = new Carta(1, Palo.PICAS);
        reyPicas = new Carta(13, Palo.PICAS);
        diezPicas = new Carta(10, Palo.PICAS);
        diezCorazones = new Carta(10, Palo.CORAZON);
        asCorazones = new Carta(1, Palo.CORAZON);
        reinaCorazones = new Carta(12, Palo.CORAZON);
        sietePicas = new Carta(7, Palo.PICAS);
        cincoPicas = new Carta(5, Palo.PICAS);
    }

    @Test
    void testDobleBlackjackEsEmpate() {
        // 1. Jugador hace apuesta.
        // Asumo que tu modelo Partida/Ronda se encarga de restar del bankroll
        // y ponerlo en la apuesta.
        partida.recibirApuesta(participante1, 100);
        assertEquals(400.0, jugador1.getSaldo());

        // 2. Forzamos las manos (asumo que ParticipanteBase tiene recibirCarta(Carta))
        participante1.getMano().addCarta(asPicas);
        participante1.getMano().addCarta(reyPicas); // Blackjack Jugador

        crupier.getMano().addCarta(asCorazones);
        crupier.getMano().addCarta(reinaCorazones); // Blackjack Crupier

        // 3. Ejecutamos el fin de ronda (el método que paga)
        ronda.getParticipantesActivosFinalRonda().add(participante1); //lo agrego a la lista final
        ronda.finDeRonda();

        // 4. Verificamos. Es empate (push), se devuelve la apuesta.
        // El jugador tenía 400 + 100 devueltos = 500
        assertEquals(500.0, jugador1.getSaldo());
    }

    @Test
    void testJugadorGanaConBlackjack() {
        // 1. Apuesta
        partida.recibirApuesta(participante1, 100);
        assertEquals(400.0, jugador1.getSaldo());

        // 2. Forzamos manos
        participante1.getMano().addCarta(asPicas);
        participante1.getMano().addCarta(reyPicas); // Blackjack Jugador

        crupier.getMano().addCarta(diezPicas);
        crupier.getMano().addCarta(sietePicas);     // Crupier (17)

        // 3. Fin de ronda
        ronda.getParticipantesActivosFinalRonda().add(participante1); //lo agrego a la lista final
        ronda.finDeRonda();

        // 4. Verificamos. Gana 3:2.
        // Tenía 400. Recupera su apuesta (100) + gana 150 (1.5 * 100).
        // Total = 400 + 100 + 150 = 650
        assertEquals(650.0, jugador1.getSaldo());
    }

    @Test
    void testDividirDiezYAsNOEsBlackjack() {
        // 1. Apuesta
        partida.recibirApuesta(participante1, 100); // Apuesta inicial
        assertEquals(400.0, jugador1.getSaldo());

        // 2. Forzamos mano para dividir
        participante1.getMano().addCarta(diezPicas);
        participante1.getMano().addCarta(diezCorazones);

        // 3. Jugador divide
        ronda.jugadorDivide(); // Asumo que esto detecta al participante

        // Bankroll ahora es 300 (500 - 100 apuesta1 - 100 apuesta2)
        assertEquals(300.0, jugador1.getSaldo());

        // Asumo que participante ahora tiene 2 manos.
        // Mano 1: [10], Mano 2: [10]

        // 4. Forzamos carta de la Mano 1
        participante1.getManos().get(0).addCarta(asPicas); // Mano 1 = 21

        // 6. Forzamos carta de la Mano 2
        participante1.getManos().get(1).addCarta(cincoPicas); // Mano 2 = 15



        // 8. Turno del crupier
        crupier.agregarCarta(diezPicas);
        crupier.agregarCarta(sietePicas);
        crupier.revelarCarta();        // Crupier (17)

        // 9. Fin de ronda
        ronda.getParticipantesActivosFinalRonda().add(participante1); //lo agrego a la lista final
        ronda.finDeRonda();

        // 10. Verificamos pagos:
        // Mano 1 (21): Gana normal. Recupera 100 + gana 100 = 200
        // Mano 2 (15): Pierde contra 17. Recupera 0.
        // Total Bankroll = 300 (después de apostar) + 200 (pago mano 1) = 500
        assertEquals(500.0, jugador1.getSaldo());

    }
}
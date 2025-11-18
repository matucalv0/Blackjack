package ar.edu.unlu.tests;

import ar.edu.unlu.blackjack.model.Bankroll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankrollTest {

    private Bankroll bankroll;

    @BeforeEach
    void setUp() {
        bankroll = new Bankroll();
        bankroll.agregarDinero(500);
    }

    @Test
    void testGetDineroInicial() {
        assertEquals(500.0, bankroll.getDinero());
    }

    @Test
    void testAgregarDinero() {
        bankroll.agregarDinero(150.0);
        assertEquals(650.0, bankroll.getDinero());
    }

    @Test
    void testRestarDinero() {
        bankroll.restarDinero(100.0);
        assertEquals(400.0, bankroll.getDinero());
    }

    @Test
    void testRestarDineroExacto() {
        bankroll.restarDinero(500.0);
        assertEquals(0.0, bankroll.getDinero());
    }

    @Test
    void testRestarMasDeLoQueTiene() {
        bankroll.restarDinero(600.0);

        assertEquals(500.0, bankroll.getDinero());
    }

    @Test
    void testRestarDineroNegativo() {
        bankroll.restarDinero(-100.0);

        assertEquals(500.0, bankroll.getDinero());
    }
}
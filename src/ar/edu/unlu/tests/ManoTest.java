package ar.edu.unlu.tests;

import ar.edu.unlu.blackjack.model.Carta;
import ar.edu.unlu.blackjack.model.Mano;
import ar.edu.unlu.blackjack.model.Palo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Asumo que tienes un Enum 'Palo'
// Asumo que la clase Carta recibe Palo y Valor (String)
// Asumo que tu clase Mano tiene un método getValor() que calcula el total

class ManoTest {

    private Mano mano;
    private Carta asPicas;
    private Carta reyPicas;
    private Carta cincoPicas;
    private Carta sietePicas;
    private Carta asCorazones;

    @BeforeEach
    void setUp() {
        mano = new Mano();

        // Asumo que la clase Carta se inicializa así. 
        // ¡Adapta esto a tu constructor!
        asPicas = new Carta(1, Palo.PICAS);
        reyPicas = new Carta(13, Palo.PICAS);
        cincoPicas = new Carta(5, Palo.PICAS);
        sietePicas = new Carta(7, Palo.PICAS);
        asCorazones = new Carta(1, Palo.CORAZON);
    }

    @Test
    void testManoSimpleSinAs() {
        mano.addCarta(reyPicas);   // 10
        mano.addCarta(cincoPicas); // 5
        assertEquals(15, mano.puntaje());
    }

    @Test
    void testManoConAsComoOnce() {
        mano.addCarta(asPicas);    // 11
        mano.addCarta(cincoPicas); // 5
        assertEquals(16, mano.puntaje());
    }

    @Test
    void testManoConAsQueDebeValerUno() {
        // As (11) + 7 (7) = 18
        mano.addCarta(asPicas);
        mano.addCarta(sietePicas);
        assertEquals(18, mano.puntaje());

        // 18 + 5 (5) = 23. El As debe cambiar a 1.
        // As (1) + 7 (7) + 5 (5) = 13
        mano.addCarta(cincoPicas);
        assertEquals(13, mano.puntaje());
    }

    @Test
    void testManoConDosAses() {
        mano.addCarta(asPicas);     // 11
        mano.addCarta(asCorazones); // 1
        assertEquals(12, mano.puntaje());
    }

    @Test
    void testManoConDosAsesQueDebenValerUno() {
        mano.addCarta(asPicas);     // 11
        mano.addCarta(asCorazones); // 1
        mano.addCarta(reyPicas);    // 10
        // Total debería ser 1 + 1 + 10 = 12
        assertEquals(12, mano.puntaje());
    }

    @Test
    void testManoSePasa() {
        mano.addCarta(reyPicas);    // 10
        mano.addCarta(sietePicas);  // 7
        mano.addCarta(cincoPicas);  // 5
        // Total 22
        assertEquals(22, mano.puntaje());
        assertTrue(mano.sePaso()); // Asumo que tienes este método
    }

    @Test
    void testBlackjack() {
        mano.addCarta(asPicas);
        mano.addCarta(reyPicas);
        assertEquals(21, mano.puntaje());
        assertTrue(mano.esBlackjack()); // Asumo que tienes este método
    }
}
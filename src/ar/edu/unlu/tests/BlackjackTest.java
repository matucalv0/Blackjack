package ar.edu.unlu.tests;


import ar.edu.unlu.blackjack.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BlackjackTest {


    private Jugador jugador;
    private Carta carta;
    private Mazo mazo;
    private Mano mano;



    @BeforeEach
    void setUp(){
        jugador = new Jugador("Mateo");
        carta = new Carta();
        mazo = new Mazo();
        mano = new Mano();
    }



    @Test
    @DisplayName("El jugador debe tener un nombre")
    void verificarQueJugadorTengaNombre(){
        boolean tieneNombre = jugador.getNombre().isEmpty(); //si es true, quiere decir que nombre esta vacio
        assertFalse(tieneNombre);
    }

}

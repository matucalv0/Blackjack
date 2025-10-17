package ar.edu.unlu.blackjack.model;

import java.util.HashSet;
import java.util.Set;

public class Mesa {
    Set<Participante> participantes = new HashSet<>();
    Crupier crupier;
    Mazo mazo;

    public Mesa(Crupier crupier, Mazo mazo){
        this.crupier = crupier;
        this.mazo = mazo;
    }

    public void agregarParticipante(Participante participante){
        participantes.add(participante);
    }
}

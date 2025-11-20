package ar.edu.unlu.blackjackfx.utils;

import ar.edu.unlu.blackjackfx.model.Carta;
import ar.edu.unlu.blackjackfx.model.Palo;

import java.util.List;

public class DibujarCarta {

    public static String paloUnicode(Palo palo) {
        return switch (palo) {
            case Palo.CORAZON -> "♥";
            case Palo.DIAMANTE -> "♦";
            case Palo.PICAS -> "♠";
            case Palo.TREBOL -> "♣";
            default -> "?";
        };
    }

    public static String[] dibujar(String valor, Palo palo) {
        String p = paloUnicode(palo);
        // Valor siempre 2 caracteres mínimo (A, K, Q, J, 10, etc.)
        String v = valor.length() == 1 ? valor + " " : valor;

        return new String[]{
                "┌────┐",
                String.format("│%s%s │", v, p),
                "└────┘"
        };
    }

    public static void renderCartasEnFila(List<Carta> cartas) {
        List<String[]> renders = cartas.stream()
                .map(c -> !c.isEsVisible()
                        ? DibujarCarta.dibujarReverso()  // dibuja carta boca abajo
                        : DibujarCarta.dibujar(c.getCaracter(), c.getPalo())
                )
                .toList();

        imprimirCartas(renders);





    }

    public static String[] dibujarReverso() {
        return new String[] {
                "┌────┐",
                "│░░░░│",
                "└────┘"
        };
    }

    public static void imprimirCartas(List<String[]> renders) {
        int filas = renders.get(0).length; // cantidad de líneas por carta

        for (int i = 0; i < filas; i++) {
            // NO pongas bordes acá
            System.out.print("   "); // un pequeño margen

            for (String[] carta : renders) {
                System.out.print(carta[i] + "  "); // carta[i] = línea 0, 1, 2, 3
            }

            System.out.println();
        }
    }


}

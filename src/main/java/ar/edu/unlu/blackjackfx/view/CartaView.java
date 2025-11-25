package ar.edu.unlu.blackjackfx.view;

import ar.edu.unlu.blackjackfx.model.Palo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class CartaView extends StackPane {
    private final ImageView imageView;
    private final String valor;
    private String palo;

    public CartaView(String valor, Palo palo, boolean isVisible) {
        this.valor = valor;
        this.getStyleClass().add("carta");

        switch (palo){
            case Palo.CORAZON -> this.palo = "H";
            case Palo.DIAMANTE -> this.palo = "D";
            case Palo.PICAS -> this.palo = "S";
            case Palo.TREBOL -> this.palo = "C";
        }

        String nombreArchivo = valor + this.palo + ".png";

        if (!isVisible){
            nombreArchivo = "1B.png";
        }

        String ruta = "/ar/edu/unlu/blackjackfx/images/cartas/" + nombreArchivo;
        Image imagen = new Image(getClass().getResourceAsStream(ruta));

        if (imagen.isError()) {
            System.out.println("ERROR cargando carta: " + ruta);
        }

        imageView = new ImageView(imagen);
        imageView.setFitWidth(90);
        imageView.setPreserveRatio(true);

        Rectangle clip = new Rectangle(90, 130);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);


        getChildren().add(imageView);

    }

    public String getValor() {
        return valor;
    }


    public String getPalo() {
        return palo;
    }
}

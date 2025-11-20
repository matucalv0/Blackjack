module ar.edu.unlu.blackjackfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens ar.edu.unlu.blackjackfx to javafx.fxml;
    exports ar.edu.unlu.blackjackfx;
}
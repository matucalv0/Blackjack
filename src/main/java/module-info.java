module ar.edu.unlu.blackjackfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;



    opens ar.edu.unlu.blackjackfx to javafx.fxml;
    opens ar.edu.unlu.blackjackfx.view to javafx.fxml;

    exports ar.edu.unlu.blackjackfx;
}
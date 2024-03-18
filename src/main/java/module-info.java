module it.polimi.ingsw.progetto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;

    exports it.polimi.ingsw.client;
    opens it.polimi.ingsw.client to javafx.fxml;
}
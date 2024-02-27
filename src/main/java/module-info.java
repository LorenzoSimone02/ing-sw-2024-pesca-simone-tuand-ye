module it.polimi.ingsw.progetto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens it.polimi.ingsw.client to javafx.fxml;
    exports it.polimi.ingsw.client;
}
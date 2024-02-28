module it.polimi.ingsw.progetto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens it.polimi.sw.client to javafx.fxml;
    exports it.polimi.sw.client;
}
module it.polimi.ingsw.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.rmi;

    exports it.polimi.ingsw.client;
    exports it.polimi.ingsw.network.rmi;
    exports it.polimi.ingsw.network;
    exports it.polimi.ingsw.network.packets;
    exports it.polimi.ingsw.server.model.resources to com.google.gson;

    opens it.polimi.ingsw.client to javafx.fxml;
    opens it.polimi.ingsw.server.model.card to com.google.gson;
    opens it.polimi.ingsw.server.model.card.corner to com.google.gson;
}
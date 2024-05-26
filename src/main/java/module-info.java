module it.polimi.ingsw.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.rmi;

    exports it.polimi.ingsw.client;
    exports it.polimi.ingsw.client.view;
    exports it.polimi.ingsw.client.view.gui to javafx.graphics;
    exports it.polimi.ingsw.client.view.gui.controllers;
    exports it.polimi.ingsw.network.rmi;
    exports it.polimi.ingsw.network;
    exports it.polimi.ingsw.network.packets;
    exports it.polimi.ingsw.client.controller;
    exports it.polimi.ingsw.server.controller.save;
    exports it.polimi.ingsw.server.controller.packethandling;
    exports it.polimi.ingsw.client.controller.clientstate;
    exports it.polimi.ingsw.client.controller.packethandlers;
    exports it.polimi.ingsw.server.model.game;
    exports it.polimi.ingsw.server.controller.exceptions;
    exports it.polimi.ingsw.server.model.player;
    exports it.polimi.ingsw.server.model.card;
    exports it.polimi.ingsw.server.model.card.corner;
    exports it.polimi.ingsw.server.controller;
    exports it.polimi.ingsw.server.model.card.goldstrategies;
    exports it.polimi.ingsw.server.model.resources to com.google.gson;
    exports it.polimi.ingsw.server.model.objectives to com.google.gson;
    exports it.polimi.ingsw.server.model.objectives.strategies to com.google.gson;

    opens it.polimi.ingsw.client to javafx.fxml;
    opens it.polimi.ingsw.server.model.card to com.google.gson;
    opens it.polimi.ingsw.server.model.card.corner to com.google.gson;
    opens it.polimi.ingsw.server.model.objectives.strategies to com.google.gson;
    opens it.polimi.ingsw.client.controller.packethandlers to javafx.fxml;
    opens it.polimi.ingsw.client.view.gui to javafx.fxml;
    opens it.polimi.ingsw.client.view to javafx.fxml;
    opens it.polimi.ingsw.client.controller to javafx.fxml;
    opens it.polimi.ingsw.client.controller.clientstate to javafx.fxml;
    opens it.polimi.ingsw.server.model.card.goldstrategies to com.google.gson;
    opens it.polimi.ingsw.client.view.gui.controllers to javafx.fxml;
}
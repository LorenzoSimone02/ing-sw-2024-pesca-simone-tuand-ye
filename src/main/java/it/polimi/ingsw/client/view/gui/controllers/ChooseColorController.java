package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.ChooseColorPacket;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseColorController implements SceneController, Initializable {

    @FXML
    private BorderPane pane;
    @FXML
    private HBox colors;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setOpacity(0.3);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), pane);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        for (Node node : colors.getChildren()) {
            node.setOpacity(0.75);
            node.setOnMouseEntered(e -> node.setOpacity(1));
            node.setOnMouseExited(e -> node.setOpacity(0.75));
        }
    }

    public void chooseColorRed(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "RED"));
    }

    public void chooseColorBlue(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "BLUE"));
    }

    public void chooseColorGreen(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "GREEN"));
    }

    public void chooseColorYellow(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "YELLOW"));
    }

    @Override
    public void updateScene(String data) {
        for (Node node : colors.getChildren()) {
            if (!node.getId().contains(ClientManager.getInstance().getGameState().getTokenColor().toLowerCase())) {
                node.setVisible(false);
            } else {
                node.setOnMouseEntered(null);
                node.setOnMouseExited(null);
                node.setOpacity(1);
            }
        }
    }
}

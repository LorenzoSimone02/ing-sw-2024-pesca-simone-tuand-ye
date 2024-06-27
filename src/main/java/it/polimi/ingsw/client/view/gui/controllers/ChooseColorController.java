package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.ChooseColorPacket;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the color choosing scene controller
 */
public class ChooseColorController implements SceneController, Initializable {

    /**
     * The pane of the scene
     */
    @FXML
    private BorderPane pane;

    /**
     * The HBox of the colors
     */
    @FXML
    private HBox colors;

    /**
     * The method initializes the scene
     * @param url the url
     * @param resourceBundle the resource bundle
     */
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

    /**
     * The method chooses the color red
     * @param mouseEvent the mouse event
     */
    public void chooseColorRed(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "RED"));
    }

    /**
     * The method chooses the color blue
     * @param mouseEvent the mouse event
     */
    public void chooseColorBlue(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "BLUE"));
    }

    /**
     * The method chooses the color green
     * @param mouseEvent the mouse event
     */
    public void chooseColorGreen(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "GREEN"));
    }

    /**
     * The method chooses the color yellow
     * @param mouseEvent the mouse event
     */
    public void chooseColorYellow(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getTokenColor() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen a Token Color");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseColorPacket(username, "YELLOW"));
    }

    /**
     * The method updates the scene
     * @param data the data to update
     */
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

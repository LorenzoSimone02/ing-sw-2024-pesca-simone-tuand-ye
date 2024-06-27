package it.polimi.ingsw.client.view.gui.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the lobby scene controller
 */
public class LobbyController implements SceneController, Initializable {

    /**
     * The label of the players
     */
    @FXML
    private Label playersLabel;

    /**
     * The pane of the lobby
     */
    @FXML
    private BorderPane lobbyPane;

    /**
     * The method initializes the scene
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbyPane.setOpacity(0.3);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), lobbyPane);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        playersLabel.setText("\nWaiting for other Players to join...");
    }

    /**
     * The method updates the scene
     * @param data the data to be updated
     */
    @Override
    public void updateScene(String data) {

    }
}

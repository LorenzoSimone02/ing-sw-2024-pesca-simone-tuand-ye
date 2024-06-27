package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.CreateGamePacket;
import it.polimi.ingsw.network.packets.JoinPacket;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the game creation scene controller
 */
public class CreateGameController implements SceneController, Initializable {

    /**
     * The label of the welcome message
     */
    @FXML
    private Label welcoleLabel;

    /**
     * The radio buttons of the number of players
     */
    @FXML
    private RadioButton rButton2, rButton3, rButton4;

    /**
     * The pane of the scene
     */
    @FXML
    private BorderPane createGamePane;

    /**
     * The number of players
     */
    @FXML
    private int numberOfPlayers = 2;

    /**
     * The method initializes the scene
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createGamePane.setOpacity(0.3);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), createGamePane);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        String username = ClientManager.getInstance().getGameState().getUsername();
        welcoleLabel.setText("Welcome " + username + "\n\nSelect the number of Players to create a new Game\nOr try to join an existing one!");
    }

    /**
     * The method sets the number of players
     */
    public void setNumberOfPlayers() {
        if (rButton2.isSelected()) {
            this.numberOfPlayers = 2;
        } else if (rButton3.isSelected()) {
            this.numberOfPlayers = 3;
        } else if (rButton4.isSelected()) {
            this.numberOfPlayers = 4;
        }
    }

    /**
     * The method creates a new game
     */
    public void createGame() {
        ClientManager.getInstance().getNetworkHandler().sendPacket(new CreateGamePacket(numberOfPlayers));
    }

    /**
     * The method allows a player to join an existing game
     */
    public void joinGame() {
        ClientManager.getInstance().getNetworkHandler().sendPacket(new JoinPacket());
    }

    /**
     * The method updates the scene
     * @param data the data to be updated
     */
    @Override
    public void updateScene(String data) {

    }
}


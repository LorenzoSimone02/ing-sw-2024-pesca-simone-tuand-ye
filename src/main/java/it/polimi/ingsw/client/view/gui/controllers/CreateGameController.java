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

public class CreateGameController implements SceneController, Initializable {

    @FXML
    private Label welcoleLabel;
    @FXML
    private RadioButton rButton2, rButton3, rButton4;
    @FXML
    private BorderPane createGamePane;
    @FXML
    private int numberOfPlayers = 2;

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

    public void setNumberOfPlayers() {
        if (rButton2.isSelected()) {
            this.numberOfPlayers = 2;
        } else if (rButton3.isSelected()) {
            this.numberOfPlayers = 3;
        } else if (rButton4.isSelected()) {
            this.numberOfPlayers = 4;
        }
    }

    public void createGame() {
        ClientManager.getInstance().getNetworkHandler().sendPacket(new CreateGamePacket(numberOfPlayers));
    }

    public void joinGame() {
        ClientManager.getInstance().getNetworkHandler().sendPacket(new JoinPacket());
    }

    @Override
    public void updateScene(String data) {

    }
}


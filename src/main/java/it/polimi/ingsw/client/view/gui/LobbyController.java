package it.polimi.ingsw.client.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyController{
    @FXML Label lobbyUsernameLabel;

    public void displayName(String username){
        lobbyUsernameLabel.setText("Hello: " + username + "\nWait for other players to join");
    }
}

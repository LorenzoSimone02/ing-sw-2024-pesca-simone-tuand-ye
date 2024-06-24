package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EndGameController implements SceneController, Initializable {

    @FXML
    private Label player1, player2, player3, player4;
    @FXML
    private Pane pane;
    @FXML
    private Button menuButton;

    @Override
    public void updateScene(String data) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setOpacity(0.3);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), pane);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        HashMap<String, Integer> players = new HashMap<>();
        players.put(ClientManager.getInstance().getGameState().getUsername(), ClientManager.getInstance().getGameState().getScore());
        for(PlayerState state : ClientManager.getInstance().getGameState().getPlayerStates()) {
            players.put(state.getUsername(), state.getScore());
        }

        ArrayList<String> orderedPlayers = new ArrayList<>();
        players.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).forEach(e -> orderedPlayers.add(e.getKey()));

        player1.setText(orderedPlayers.get(0) + "   " + players.get(orderedPlayers.get(0) + " points"));
        player2.setText(orderedPlayers.get(1) + "   " + players.get(orderedPlayers.get(1)) + " points");
        if(orderedPlayers.size() == 3){
            player3.setVisible(true);
            player3.setText(orderedPlayers.get(2) + "   " + players.get(orderedPlayers.get(2)) + " points");
        }
        if(orderedPlayers.size() == 4){
            player3.setVisible(true);
            player4.setVisible(true);
            player3.setText(orderedPlayers.get(2) + "   " + players.get(orderedPlayers.get(2)) + " points");
            player4.setText(orderedPlayers.get(3) + "   " + players.get(orderedPlayers.get(3)) + " points");
        }
    }
}

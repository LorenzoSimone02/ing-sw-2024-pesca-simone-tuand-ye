package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateGameController {

    @FXML Label usernameLabel;
    @FXML RadioButton rButton2,rButton3,rButton4;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private int numberOfPlayers = 2;
    private String username;

    public void displayName(String username){
        usernameLabel.setText("Hello: " + username + "\nWait for other players to join");
        this.username = username;
    }

    public void getNumberOfPlayers(){
        System.out.println(this.numberOfPlayers);
    }

    public void setNumberOfPlayers(ActionEvent event) throws IOException{
        if(rButton2.isSelected()){
            this.numberOfPlayers = 2;
        } else if (rButton3.isSelected()) {
            this.numberOfPlayers = 3;
        } else if (rButton4.isSelected()) {
            this.numberOfPlayers = 4;
        }
    }

    public void createGame(ActionEvent event) throws IOException {
        getNumberOfPlayers();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
        root = loader.load();
        LobbyController lobbyController = loader.getController();
        lobbyController.displayName(this.username);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


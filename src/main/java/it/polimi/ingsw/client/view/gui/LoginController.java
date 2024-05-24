package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController{

    @FXML TextField loginUsernameTextField;

    boolean thereIsAGame = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Login(ActionEvent event) throws IOException {
        String username = loginUsernameTextField.getText();
        if(thereIsAGame)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            root = loader.load();

            LobbyController lobbyController = loader.getController();
            lobbyController.displayName(username);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateGame.fxml"));
            root = loader.load();

            CreateGameController createGameController = loader.getController();
            createGameController.displayName(username);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}


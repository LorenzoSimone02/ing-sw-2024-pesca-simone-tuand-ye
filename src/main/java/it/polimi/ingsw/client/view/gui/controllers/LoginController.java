package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.LoginPacket;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements SceneController, Initializable {

    @FXML
    private TextField loginUsernameTextField;
    @FXML
    private BorderPane loginPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), loginPane);
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void login() {
        String username = loginUsernameTextField.getText();
        if (username.isBlank()) {
            return;
        }
        ClientManager.getInstance().getNetworkHandler().sendPacket(new LoginPacket(username));
    }

    @Override
    public void updateScene(String data) {

    }
}


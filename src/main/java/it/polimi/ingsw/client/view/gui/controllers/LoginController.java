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

/**
 * JavaFX controller for the login scene controller
 */
public class LoginController implements SceneController, Initializable {

    /**
     * The text field of the login username
     */
    @FXML
    private TextField loginUsernameTextField;

    /**
     * The pane of the login
     */
    @FXML
    private BorderPane loginPane;

    /**
     * The method initializes the scene
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), loginPane);
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * The method logs the user in
     */
    public void login() {
        String username = loginUsernameTextField.getText();
        if (username.isBlank()) {
            return;
        }
        ClientManager.getInstance().getNetworkHandler().sendPacket(new LoginPacket(username));
    }

    /**
     * The method updates the scene
     * @param data the data to be updated
     */
    @Override
    public void updateScene(String data) {

    }
}


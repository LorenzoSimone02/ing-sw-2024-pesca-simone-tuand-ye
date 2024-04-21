package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Codex Naturalis!");
    }
}
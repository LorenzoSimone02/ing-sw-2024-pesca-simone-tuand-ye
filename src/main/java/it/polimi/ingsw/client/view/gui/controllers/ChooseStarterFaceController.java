package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.ChooseStarterFacePacket;
import it.polimi.ingsw.server.model.card.FaceEnum;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the starter card face choosing scene controller
 */
public class ChooseStarterFaceController implements SceneController, Initializable {

    /**
     * The pane of the scene
     */
    @FXML
    private BorderPane pane;

    /**
     * The image views of the card's front and back
     */
    @FXML
    private ImageView front, back;

    /**
     * The method initializes the scene
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setOpacity(0.3);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), pane);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        front.setOpacity(0.75);
        front.setOnMouseEntered(e -> front.setOpacity(1));
        front.setOnMouseExited(e -> front.setOpacity(0.75));

        back.setOpacity(0.75);
        back.setOnMouseEntered(e -> back.setOpacity(1));
        back.setOnMouseExited(e -> back.setOpacity(0.75));

        Platform.runLater(() -> {
            try {
                int id = ClientManager.getInstance().getGameState().getGivenStarter().getId();
                front.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + id + ".png")).toURI().toString()));
                back.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/back" + id + ".png")).toURI().toString()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * The method chooses the front face of the card
     * @param mouseEvent the mouse event
     */
    public void chooseFront(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getStarterCard() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen the Starter Card face");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        int id1 = ClientManager.getInstance().getGameState().getGivenStarter().getId();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseStarterFacePacket(FaceEnum.FRONT, id1, username));
    }

    /**
     * The method chooses the back face of the card
     * @param mouseEvent the mouse event
     */
    public void chooseBack(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getStarterCard() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen the Starter Card face");
        }
        String username = ClientManager.getInstance().getGameState().getUsername();
        int id = ClientManager.getInstance().getGameState().getGivenStarter().getId();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseStarterFacePacket(FaceEnum.BACK, id, username));
    }

    /**
     * The method updates the scene
     * @param data the data to be updated
     */
    @Override
    public void updateScene(String data) {
        if (ClientManager.getInstance().getGameState().getStarterCard().getFace() == FaceEnum.FRONT) {
            back.setVisible(false);
            front.setOnMouseEntered(null);
            front.setOnMouseExited(null);
            front.setOpacity(1);
        } else {
            front.setVisible(false);
            back.setOnMouseEntered(null);
            back.setOnMouseExited(null);
            back.setOpacity(1);
        }
    }
}

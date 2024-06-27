package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.ChooseObjectivePacket;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the objective choosing scene controller
 */
public class ChooseObjectiveController implements SceneController, Initializable {

    /**
     * The image view of the two objective cards
     */
    @FXML
    private ImageView obj1, obj2;

    /**
     * The pane of the scene
     */
    @FXML
    private BorderPane pane;

    /**
     * The HBox of the objectives
     */
    @FXML
    private HBox objectives;

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

        for (Node node : objectives.getChildren()) {
            node.setOpacity(0.75);
            node.setOnMouseEntered(e -> node.setOpacity(1));
            node.setOnMouseExited(e -> node.setOpacity(0.75));
        }
        Platform.runLater(() -> {
            try {
                ObjectiveCard card1 = ClientManager.getInstance().getGameState().getProposedCards().getFirst();
                ObjectiveCard card2 = ClientManager.getInstance().getGameState().getProposedCards().get(1);

                int id1 = card1.getId();
                obj1.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + id1 + ".png")).toURI().toString()));
                Tooltip tooltip1 = new Tooltip(card1.getObjectiveDescription());
                tooltip1.setShowDelay(Duration.millis(300));
                Tooltip.install(obj1, tooltip1);
                obj1.setId(id1 + "");
                int id2 = card2.getId();
                Tooltip tooltip2 = new Tooltip(card2.getObjectiveDescription());
                tooltip2.setShowDelay(Duration.millis(300));
                Tooltip.install(obj2, tooltip2);
                obj2.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + id2 + ".png")).toURI().toString()));
                obj2.setId(id2 + "");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * The method chooses the first objective card
     * @param mouseEvent the mouse event
     */
    public void chooseObj1(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getObjectiveCard() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen an Objective Card");
        }
        int id1 = ClientManager.getInstance().getGameState().getProposedCards().getFirst().getId();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseObjectivePacket(id1));
    }

    /**
     * The method chooses the second objective card
     * @param mouseEvent the mouse event
     */
    public void chooseObj2(MouseEvent mouseEvent) {
        if (ClientManager.getInstance().getGameState().getObjectiveCard() != null) {
            ClientManager.getInstance().getUserInterface().showMessage("You have already chosen an Objective Card");
        }
        int id2 = ClientManager.getInstance().getGameState().getProposedCards().get(1).getId();
        ClientManager.getInstance().getNetworkHandler().sendPacket(new ChooseObjectivePacket(id2));
    }

    /**
     * The method updates the scene
     * @param data the data to update
     */
    @Override
    public void updateScene(String data) {
        for(Node node : objectives.getChildren()) {
            if (!node.getId().equals(ClientManager.getInstance().getGameState().getObjectiveCard().getId() + "")) {
                node.setVisible(false);
            }else{
                node.setOnMouseEntered(null);
                node.setOnMouseExited(null);
                node.setOpacity(1);
            }
        }
    }
}

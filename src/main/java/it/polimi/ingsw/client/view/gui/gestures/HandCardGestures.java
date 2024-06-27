package it.polimi.ingsw.client.view.gui.gestures;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.network.packets.TurnCardPacket;
import it.polimi.ingsw.server.model.card.ResourceCard;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class HandCardGestures {

    public double mouseAnchorX;
    public double mouseAnchorY;
    public double translateAnchorX;
    public double translateAnchorY;
    public double startX;
    public double startY;

    private Pane pane;
    private Node node;
    private TabPane tabPane;

    public HandCardGestures(Pane pane, Node node, TabPane tabPane) {
        this.node = node;
        this.pane = pane;
        this.tabPane = tabPane;
        this.startX = node.getTranslateX();
        this.startY = node.getTranslateY();
        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseClicked(onMouseClickedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
    }

    private final EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
        if (!ClientManager.getInstance().getGameState().isActivePlayer())
            return;
        mouseAnchorX = event.getSceneX();
        mouseAnchorY = event.getSceneY();
        translateAnchorX = node.getTranslateX();
        translateAnchorY = node.getTranslateY();
    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
        if (!ClientManager.getInstance().getGameState().isActivePlayer() || !event.getButton().equals(MouseButton.PRIMARY) || !tabPane.getSelectionModel().isSelected(0))
            return;

        double newX = translateAnchorX + event.getSceneX() - mouseAnchorX;
        double newY = translateAnchorY + event.getSceneY() - mouseAnchorY;

        if ((node.getBoundsInParent().getMinX() > 0 || newX > node.getTranslateX()) && (node.getBoundsInParent().getMaxX() <= ((HBox) node.getParent()).getWidth() || newX < node.getTranslateX())) {
            node.setTranslateX(newX);
        }
        Point2D inScene = node.localToScene(node.getBoundsInLocal().getMinX(), node.getBoundsInLocal().getMinY());
        if ((inScene.getY() > 0 || newY > node.getTranslateY()) && (node.getBoundsInParent().getMaxY() <= ((HBox) node.getParent()).getHeight() || newY < node.getTranslateY())) {
            node.setTranslateY(newY);
        }

        if (isColliding((ImageView) node)) {
            node.setScaleX(pane.getScaleX() / 2);
            node.setScaleY(pane.getScaleY() / 2);
        } else {
            node.setScaleX(1);
            node.setScaleY(1);
        }
        event.consume();
    };

    private final EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
        if (!ClientManager.getInstance().getGameState().isActivePlayer() || !tabPane.getSelectionModel().isSelected(0))
            return;
        node.setScaleX(1);
        node.setScaleY(1);
        if (isColliding((ImageView) node) && ClientManager.getInstance().getGameState().getCardsInHand().size() == 3) {
            Point2D centerInScene = node.localToScene(node.getBoundsInLocal().getCenterX(), node.getBoundsInLocal().getCenterY());
            Point2D centerInTargetPane = pane.sceneToLocal(centerInScene);
            int gridX = (int) Math.round(centerInTargetPane.getY() / 29);
            int gridY = (int) Math.round(centerInTargetPane.getX() / 55);
            int cardID = Integer.parseInt(node.getId());
            ResourceCard card = (ResourceCard) ClientManager.getInstance().getGameState().getCardById(cardID);
            if (ClientManager.getInstance().getGameState().canPlaceCard(gridX, gridY, card)) {
                ClientManager.getInstance().getNetworkHandler().sendPacket(new PlaceCardPacket(cardID, gridX, gridY));
            } else {
                node.setTranslateX(startX);
                node.setTranslateY(startY);
            }
        } else {
            node.setTranslateX(startX);
            node.setTranslateY(startY);
        }
    };

    private final EventHandler<MouseEvent> onMouseClickedEventHandler = event -> {
        if (!ClientManager.getInstance().getGameState().isActivePlayer() || !event.getButton().equals(MouseButton.SECONDARY) || !tabPane.getSelectionModel().isSelected(0))
            return;
        ClientManager.getInstance().getNetworkHandler().sendPacket(new TurnCardPacket(Integer.parseInt(node.getId())));
    };

    private boolean isColliding(ImageView imageView) {
        Bounds imageViewBounds = imageView.getBoundsInParent();
        return imageViewBounds.getCenterY() <= 0;
    }
}

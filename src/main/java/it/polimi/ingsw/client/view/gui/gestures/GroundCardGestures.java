package it.polimi.ingsw.client.view.gui.gestures;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GroundCardGestures {

    private Node node;

    public GroundCardGestures(Node node) {
        this.node = node;
        node.setOnMouseClicked(onMouseClickedEventHandler);
    }

    private final EventHandler<MouseEvent> onMouseClickedEventHandler = event -> {
        if (!ClientManager.getInstance().getGameState().isActivePlayer() || ClientManager.getInstance().getGameState().getCardsInHand().size() != 2)
            return;
        if (node.getId().contains("commObj")) return;
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (node.getId().equals("resDeck")) {
                ClientManager.getInstance().getNetworkHandler().sendPacket(new DrawCardPacket(false));
                ClientManager.getInstance().getNetworkHandler().sendPacket(new PeekDeckPacket(false));
            } else if (node.getId().equals("goldDeck")) {
                ClientManager.getInstance().getNetworkHandler().sendPacket(new DrawCardPacket(true));
                ClientManager.getInstance().getNetworkHandler().sendPacket(new PeekDeckPacket(true));
            } else {
                ClientManager.getInstance().getNetworkHandler().sendPacket(new DrawCardPacket(Integer.parseInt(node.getId())));
            }
        }
    };
}

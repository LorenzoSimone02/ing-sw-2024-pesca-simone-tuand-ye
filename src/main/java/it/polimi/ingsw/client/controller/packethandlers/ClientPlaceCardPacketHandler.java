package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.client.view.gui.controllers.GameGuiController;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import javafx.application.Platform;

/**
 * The class that handles the card placing packets from the server
 */
public class ClientPlaceCardPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the card placing packet
     * @param packet the card placing packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        PlaceCardPacket placeCardPacket = (PlaceCardPacket) packet;
        if (placeCardPacket.getPlayer().equals(clientManager.getGameState().getUsername())) {
            ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(placeCardPacket.getCardId());
            clientManager.getGameState().setCardPlaced(card, placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
            clientManager.getGameState().removeCardInHand(card);
            clientManager.getGameState().setScore(placeCardPacket.getNewScore());
            clientManager.getGameState().setResources(placeCardPacket.getResources());
            clientManager.getGameState().addOrderedCard(card);
            if(clientManager.getViewMode() == ViewModeEnum.GUI){
                Platform.runLater(() -> {
                    GameGuiController gameGuiController = (GameGuiController) ((GUIClient) clientManager.getUserInterface()).getControllersMap().get(clientManager.getGameState().getClientStatus());
                    gameGuiController.placeCard(card, placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
                    gameGuiController.updateResources();
                    gameGuiController.updatePoints();
                });
            }
            System.out.println(Printer.GREEN + "Card placed successfully\nNow draw a card with /drawCard" + Printer.RESET);
        } else {
            for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
                if (playerState.getUsername().equals(placeCardPacket.getPlayer())) {
                    ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(placeCardPacket.getCardId());
                    card.setFace(FaceEnum.valueOf(placeCardPacket.getCardFace()));
                    playerState.setScore(placeCardPacket.getNewScore());
                    playerState.setCardPlaced(card, placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
                    playerState.addOrderedCard(card);
                    if(clientManager.getViewMode() == ViewModeEnum.GUI){
                        Platform.runLater(() -> {
                            GameGuiController gameGuiController = (GameGuiController) ((GUIClient) clientManager.getUserInterface()).getControllersMap().get(clientManager.getGameState().getClientStatus());
                            gameGuiController.updatePoints();
                        });
                    }
                    break;
                }
            }
        }
    }
}

package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class ClientPlaceCardPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        PlaceCardPacket placeCardPacket = (PlaceCardPacket) packet;
        if (placeCardPacket.getPlayer().equals(clientManager.getGameState().getUsername())) {
            ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(placeCardPacket.getCardId());
            clientManager.getGameState().setCardPlaced(card, placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
            clientManager.getGameState().removeCardInHand(card);
            System.out.println(Printer.GREEN + "Card placed successfully" + Printer.RESET);
        } else {
            for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
                if (playerState.getUsername().equals(placeCardPacket.getPlayer())) {
                    ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(placeCardPacket.getCardId());
                    playerState.setCardPlaced(card, placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
                    break;
                }
            }
        }
    }
}

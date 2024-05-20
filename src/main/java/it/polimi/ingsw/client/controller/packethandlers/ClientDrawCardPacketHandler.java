package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class ClientDrawCardPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        DrawCardPacket drawCardPacket = (DrawCardPacket) packet;
        ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(drawCardPacket.getCardID());
        clientManager.getGameState().addCardInHand(card);
        if (drawCardPacket.getNewCardID() > 0) {
            Card newCard = clientManager.getGameState().getCardById(drawCardPacket.getNewCardID());
            clientManager.getGameState().addCardOnGround(newCard);
        }
    }
}

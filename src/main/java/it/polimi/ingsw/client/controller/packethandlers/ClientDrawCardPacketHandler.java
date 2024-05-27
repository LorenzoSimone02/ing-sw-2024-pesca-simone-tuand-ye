package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class ClientDrawCardPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        DrawCardPacket drawCardPacket = (DrawCardPacket) packet;
        ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(drawCardPacket.getCardID());

        if (drawCardPacket.getNewCardID() > 0) {
            Card newCard = clientManager.getGameState().getCardById(drawCardPacket.getNewCardID());
            clientManager.getGameState().removeCardOnGround(card);
            clientManager.getGameState().addCardOnGround(newCard);
        }

        if (clientManager.getGameState().getActivePlayer().equals(clientManager.getGameState().getUsername())) {
            clientManager.getGameState().addCardInHand(card);
            System.out.println(Printer.GREEN + "Card drawn successfully, your turn has ended." + Printer.RESET);
            new Thread(() -> clientManager.getNetworkHandler().sendPacket(new EndTurnPacket(clientManager.getGameState().getUsername()))).start();
        }

    }
}

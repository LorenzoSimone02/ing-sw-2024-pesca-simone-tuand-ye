package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.FaceEnum;

public class ClientPeekDeckPacketHandler extends ClientPacketHandler{
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {

        PeekDeckPacket peekDeckPacket = (PeekDeckPacket) packet;
        int topCardID = peekDeckPacket.getCardId();

        if (topCardID > 0) {
            Card topCard = clientManager.getGameState().getCardById(topCardID);

            topCard.setFace(FaceEnum.BACK);
            Printer.printCard(topCard);
            topCard.setFace(FaceEnum.FRONT);

        } else {
            System.err.println(Printer.GREEN + "Invalid card." + Printer.RESET);

        }

    }
}

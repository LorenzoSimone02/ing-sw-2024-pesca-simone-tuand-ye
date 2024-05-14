package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChooseObjectivePacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.model.card.ObjectiveCard;

public class ClientChooseObjectivePacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ChooseObjectivePacket chooseObjectivePacket = (ChooseObjectivePacket) packet;
        try {
            if (chooseObjectivePacket.getChoosenCardID() > 0) {
                ObjectiveCard card = (ObjectiveCard) clientManager.getGameState().getCardById(chooseObjectivePacket.getChoosenCardID());
                clientManager.getGameState().setObjectiveCard(card);
            } else {
                clientManager.getGameState().setClientStatus(ClientStatusEnum.CHOOSING_OBJECTIVE);
                ObjectiveCard card1 = (ObjectiveCard) clientManager.getGameState().getCardById(chooseObjectivePacket.getCardID1());
                ObjectiveCard card2 = (ObjectiveCard) clientManager.getGameState().getCardById(chooseObjectivePacket.getCardID2());
                System.out.println("Choose one of the following cards:");
                System.out.println("1. " + card1.getObjectiveType() + " - 2. " + card2.getObjectiveType());
            }
        } catch (Exception ignored) {
        }
    }
}

package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.TurnCardPacket;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.FaceEnum;

public class ClientTurnCardPacketHandler extends ClientPacketHandler {

    public void handlePacket(Packet packet, ClientManager clientManager) {
        TurnCardPacket turnCardPacket = (TurnCardPacket) packet;
        Card card = clientManager.getGameState().getCardById(turnCardPacket.getCardId());
        if (card.getFace() == FaceEnum.FRONT) {
            card.setFace(FaceEnum.BACK);
        } else {
            card.setFace(FaceEnum.FRONT);
        }
    }
}

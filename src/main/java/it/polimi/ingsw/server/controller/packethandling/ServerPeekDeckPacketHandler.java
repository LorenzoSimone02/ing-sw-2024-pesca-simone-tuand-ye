package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.GoldCard;

public class ServerPeekDeckPacketHandler extends ServerPacketHandler{
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        PeekDeckPacket peekDeckPacket = (PeekDeckPacket) packet;
        int topCardID;

        if (peekDeckPacket.isGold()) {
            topCardID = controller.getGame().getTable().getGoldDeck().getCards().peek().getId();

        } else {
            topCardID = controller.getGame().getTable().getResourceDeck().getCards().peek().getId();

        }
        controller.getNetworkHandler().sendPacket(clientConnection, new PeekDeckPacket(topCardID));
    }
}

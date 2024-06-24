package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import it.polimi.ingsw.server.controller.GameController;

/**
 * The class that handles the client's deck peeking packets from the clients
 */
public class ServerPeekDeckPacketHandler extends ServerPacketHandler{

    /**
     * The method handles the client's deck peeking packets from the client
     * @param packet the peek deck packet
     * @param controller the game controller
     * @param clientConnection the connection of the client
     */
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        PeekDeckPacket peekDeckPacket = (PeekDeckPacket) packet;
        int topCardID;

        if (peekDeckPacket.isGold()) {
            topCardID = controller.getGame().getTable().getGoldDeck().getCards().peek().getId();
        } else {
            topCardID = controller.getGame().getTable().getResourceDeck().getCards().peek().getId();

        }
        controller.getNetworkHandler().sendPacket(clientConnection, new PeekDeckPacket(topCardID, peekDeckPacket.isGold()));
    }
}

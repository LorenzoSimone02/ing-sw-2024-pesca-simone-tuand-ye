package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

/**
 * The class that handles the information packets from the clients
 */
public class ServerInfoPacketHandler extends ServerPacketHandler {

    /**
     * The method handles the information packets from the client
     * @param packet the info packet
     * @param controller the game controller
     * @param clientConnection the connection of the client
     */
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        InfoPacket infoPacket = (InfoPacket) packet;
        System.out.println("Info packet received by " + infoPacket.getSender() + " with data: " + infoPacket.getData());
    }
}

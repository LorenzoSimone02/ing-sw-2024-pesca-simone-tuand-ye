package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

/**
 * The abstract class that represents the server packets handler
 */
public abstract class ServerPacketHandler {

    /**
     * The method that handles the packets
     * @param packet the packet
     * @param controller the game controller
     * @param clientConnection the connection of the client
     */
    public abstract void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection);
}

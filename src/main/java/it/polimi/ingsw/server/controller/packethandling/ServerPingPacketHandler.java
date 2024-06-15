package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

/**
 * The class that handles the clients pinging packets from the clients
 */
public class ServerPingPacketHandler extends ServerPacketHandler {

    /**
     * The method handles the clients pinging packets from the client
     * @param packet the ping packet
     * @param controller the game controller
     * @param connection the connection of the client
     */
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        connection.setLastPing(System.currentTimeMillis());
    }
}
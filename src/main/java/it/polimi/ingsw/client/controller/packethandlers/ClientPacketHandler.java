package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.Packet;

/**
 * The abstract class that represents the generic client packet handler
 */
public abstract class ClientPacketHandler {

    /**
     * The method that handles the packet
     * @param packet the packet
     * @param clientManager the client manager
     */
    public abstract void handlePacket(Packet packet, ClientManager clientManager);
}

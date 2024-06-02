package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

import java.io.Serializable;
import java.util.UUID;

/**
 * Abstract Packet class
 * Packets are used to send messages between Server and Client
 */
public abstract class Packet implements Serializable {

    /**
     * The UUID of the sender of the packet
     */
    private UUID sender;

    /**
     * The method returns the UUID of the sender of the packet
     * @return the UUID of the sender of the packet
     */
    public UUID getSender() {
        return sender;
    }

    /**
     * The method sets the UUID of the sender of the packet
     * @param sender the UUID of the sender of the packet
     */
    public void setSender(UUID sender) {
        this.sender = sender;
    }

    /**
     * The method returns the client-side packets handler
     * @return the client-side packets handler
     */
    public abstract ClientPacketHandler getClientPacketHandler();

    /**
     * The method returns the server-side packets handler
     * @return the server-side packets handler
     */
    public abstract ServerPacketHandler getServerPacketHandler();
}

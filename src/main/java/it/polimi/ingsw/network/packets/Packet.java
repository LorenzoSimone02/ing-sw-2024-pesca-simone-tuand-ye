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

    private UUID sender;

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public abstract ClientPacketHandler getClientPacketHandler();

    public abstract ServerPacketHandler getServerPacketHandler();
}

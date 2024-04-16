package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandling.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

import java.io.Serializable;

/**
 * Abstract Packet class
 * Packets are used to send messages between Server and Client
 */
public abstract class Packet implements Serializable {

    String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public abstract ClientPacketHandler getClientPacketHandler();

    public abstract ServerPacketHandler getServerPacketHandler();
}

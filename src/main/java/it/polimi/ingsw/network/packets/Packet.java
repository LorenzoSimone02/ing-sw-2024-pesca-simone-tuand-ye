package it.polimi.ingsw.network.packets;

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

    abstract public void handle();

}

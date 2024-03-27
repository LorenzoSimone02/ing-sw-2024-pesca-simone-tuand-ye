package it.polimi.ingsw.network.packets;

import java.io.Serializable;

/**
 * Abstract Packet class
 * Packets are used to send messages between server and client
 */
public abstract class Packet implements Serializable {
    public Packet(){
    }

    abstract public void handle();

}

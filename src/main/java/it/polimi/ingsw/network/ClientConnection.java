package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

import java.io.Serializable;

/**
 * Class representing the server-side state of a client connected to the server.
 */
public abstract class ClientConnection implements Serializable {

    /**
     * The User nickanme
     */
    private String username;

    /**
     * Whether the client is connected
     */
    private boolean isConnected;

    public ClientConnection() {
        this.isConnected = true;
        this.username = "Unknown";
    }

    public abstract void receivePacket(Packet packet);

    public boolean isConnected() {
        return isConnected;
    }

    public String getUsername() {
        return username;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
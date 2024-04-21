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
    private String nickname;

    /**
     * Whether the client is connected
     */
    private boolean isConnected;

    public ClientConnection() {
        this.isConnected = true;
        this.nickname = "Unknown";
    }

    public abstract void receivePacket(Packet packet);

    public boolean isConnected() {
        return isConnected;
    }

    public String getNickname() {
        return nickname;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
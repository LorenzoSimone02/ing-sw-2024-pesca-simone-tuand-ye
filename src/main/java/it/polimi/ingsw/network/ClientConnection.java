package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

import java.io.Serializable;
import java.util.Random;

/**
 * Class representing the server-side state of a client connected to the server.
 */
public class ClientConnection implements Serializable {
    /**
     * The User uniqueId
     */
    private final int uniqueId;
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
        this.uniqueId = new Random().nextInt(999999);
        this.nickname = "Unknown" + uniqueId;
    }

    public void receivePacket(Packet packet) {
    }

    public int getUniqueId() {
        return uniqueId;
    }

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
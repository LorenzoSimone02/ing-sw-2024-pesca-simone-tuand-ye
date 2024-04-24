package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class representing the server-side state of a client connected to the server.
 */
public abstract class ClientConnection implements Serializable {

    /**
     * The UUID of the Connection
     */
    private UUID uuid;
    /**
     * The User nickanme
     */
    private String username;

    /**
     * Whether the client is connected
     */
    private boolean isConnected;

    /**
     * The last time the Client pinged the Server
     */
    private long lastPing;

    public ClientConnection() {
        this.isConnected = true;
        this.lastPing = System.currentTimeMillis();
    }

    public abstract void receivePacket(Packet packet);

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public long getLastPing() {
        return lastPing;
    }

    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    public String getUsername() {
        return username;
    }

    public boolean isConnected() {
        return isConnected;
    }


    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
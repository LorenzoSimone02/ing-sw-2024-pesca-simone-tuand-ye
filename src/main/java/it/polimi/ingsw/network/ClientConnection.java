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
     * The User nickname
     */
    private String username;

    /**
     * The last time the Client pinged the Server
     */
    private long lastPing;

    /**
     * Constructor of the class
     */
    public ClientConnection() {
        this.lastPing = System.currentTimeMillis();
    }

    /**
     * The method receives a packet from the client
     * @param packet the packet received
     */
    public abstract void receivePacket(Packet packet);

    /** The method returns the UUID of the connection
     * @return the UUID of the connection
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * The method sets the UUID of the connection
     * @param uuid the UUID of the connection
     */
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * The method returns the last time the Client pinged the Server
     * @return the last time the Client pinged the Server
     */
    public long getLastPing() {
        return lastPing;
    }

    /**
     * The method sets the last time the Client pinged the Server
     * @param lastPing the last time the Client pinged the Server
     */
    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    /**
     * The method returns the User nickname
     * @return the User nickname
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method sets the User nickname
     * @param username the User nickname
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
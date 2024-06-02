package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientConnectionEventPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * ConnectionEventPacket is a Packet that contains the information about a player's connection to the a game
 */
public class ConnectionEventPacket extends Packet {

    /**
     * The username of the player who connected or disconnected
     */
    private final String username;

    /**
     * A boolean that is true if the player disconnected
     */
    private final boolean isDisconnection;

    /**
     * A boolean that is true if the player reconnected
     */
    private final boolean isReconnection;

    /**
     * Constructor of the class
     * @param username the username of the player who connected or disconnected
     * @param isDisconnection a boolean that is true if the player disconnected
     * @param isReconnection a boolean that is true if the player reconnected
     */
    public ConnectionEventPacket(String username, boolean isDisconnection, boolean isReconnection) {
        this.username = username;
        this.isDisconnection = isDisconnection;
        this.isReconnection = isReconnection;
    }

    /**
     * The method returns the username of the player who connected or disconnected
     * @return the username of the player who connected or disconnected
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method returns a boolean that is true if the player disconnected
     * @return a boolean that is true if the player disconnected
     */
    public boolean isDisconnection() {
        return isDisconnection;
    }

    /**
     * The method returns a boolean that is true if the player reconnected
     * @return a boolean that is true if the player reconnected
     */
    public boolean isReconnection() {
        return isReconnection;
    }

    /**
     * The method returns the client-side connection events packets handler
     * @return the client-side connection events packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientConnectionEventPacketHandler();
    }

    /**
     * The method returns the server-side connection events packets handler
     * @return the server-side connection events packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}

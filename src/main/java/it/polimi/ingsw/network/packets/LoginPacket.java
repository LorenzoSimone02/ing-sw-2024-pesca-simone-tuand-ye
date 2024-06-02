package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientLoginPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerLoginPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * LoginPacket is a Packet that contains the information about the login of a player
 */
public class LoginPacket extends Packet {

    /**
     * The username of the player who is logging in
     */
    private final String username;

    /**
     * Constructor of the class
     * @param username the username of the player who is logging in
     */
    public LoginPacket(String username) {
        this.username = username;
    }

    /**
     * The method returns the username of the player who is logging in
     * @return the username of the player who is logging in
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method returns the client-side player login packets handler
     * @return the client-side player login packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientLoginPacketHandler();
    }

    /**
     * The method returns the server-side player login packets handler
     * @return the server-side player login packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerLoginPacketHandler();
    }
}
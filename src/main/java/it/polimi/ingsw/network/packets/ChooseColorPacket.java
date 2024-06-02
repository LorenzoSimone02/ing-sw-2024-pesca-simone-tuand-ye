package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChooseColorPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChooseColorPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * ChooseColorPacket is a Packet that contains the color chosen by a player
 */
public class ChooseColorPacket extends Packet {

    /**
     * The username of the player who chose the color
     */
    private final String username;

    /**
     * The color chosen by the player
     */
    private final String color;

    /**
     * Constructor of the class
     * @param username the username of the player who chose the color
     * @param color the color chosen by the player
     */
    public ChooseColorPacket(String username, String color) {
        this.username = username;
        this.color = color;
    }

    /**
     * The method returns the color chosen by the player
     * @return the color chosen by the player
     */
    public String getColor() {
        return color;
    }

    /**
     * The method returns the username of the player who chose the color
     * @return the username of the player who chose the color
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method returns the client-side color choosing packets handler
     * @return the client-side color choosing packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChooseColorPacketHandler();
    }

    /**
     * The method returns the server-side color choosing packets handler
     * @return the server-side color choosing packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChooseColorPacketHandler();
    }
}

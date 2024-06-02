package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientJoinPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerJoinPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * JoinPacket is a Packet that contains the information about the joining of a player to a game
 */
public class JoinPacket extends Packet {

    /**
     * The ID of the game that the player wants to join
     */
    private final int gameID;

    /**
     * Constructor of the class
     */
    public JoinPacket() {
        this.gameID = 0;
    }

    /**
     * Constructor of the class
     * @param gameID the ID of the game that the player wants to join
     */
    public JoinPacket(int gameID) {
        this.gameID = gameID;
    }

    /**
     * The method returns the ID of the game that the player wants to join
     * @return the ID of the game that the player wants to join
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * The method returns the client-side join packets handler
     * @return the client-side join packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientJoinPacketHandler();
    }

    /**
     * The method returns the server-side player joining packets handler
     * @return the server-side player joining packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerJoinPacketHandler();
    }
}

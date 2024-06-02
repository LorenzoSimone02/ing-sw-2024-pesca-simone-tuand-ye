package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientEndTurnPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerEndTurnPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * EndTurnPacket is a Packet that contains the information about the end of a player's turn
 */
public class EndTurnPacket extends Packet {

    /**
     * The username of the player who is currently playing
     */
    private final String activePlayer;

    /**
     * Constructor of the class
     * @param activePlayer the username of the player who is currently playing
     */
    public EndTurnPacket(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * The method returns the username of the player who is currently playing
     * @return the username of the player who is currently playing
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * The method returns the client-side end turn packets handler
     * @return the client-side end turn packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientEndTurnPacketHandler();
    }

    /**
     * The method returns the server-side end turn packets handler
     * @return the server-side end turn packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerEndTurnPacketHandler();
    }
}

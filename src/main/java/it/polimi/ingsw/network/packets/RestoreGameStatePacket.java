package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientRestoreGameStatePacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.save.PlayerSave;

import java.util.ArrayList;

/**
 * Packet used to restore the game state of the client after a disconnection or a server crash.
 */
public class RestoreGameStatePacket extends Packet {

    /**
     * The list of the players' saved data
     */
    private final ArrayList<PlayerSave> playerSaves;

    /**
     * The username of the player that has been restored
     */
    private final String playerRestored;

    /**
     * Constructor of the class
     * @param playerRestored the username of the player that has been restored
     * @param playerSaves the list of the players' saved data
     */
    public RestoreGameStatePacket(String playerRestored, ArrayList<PlayerSave> playerSaves) {
        this.playerSaves = new ArrayList<>();
        this.playerSaves.addAll(playerSaves);
        this.playerRestored = playerRestored;
    }

    /**
     * The method returns the username of the player that has been restored
     * @return the username of the player that has been restored
     */
    public String getPlayerRestored() {
        return playerRestored;
    }

    /**
     * The method returns the list of the players' saved data
     * @return the list of the players' saved data
     */
    public ArrayList<PlayerSave> getPlayerSaves() {
        return playerSaves;
    }

    /**
     * The method returns the client-side game restoring packets handler
     * @return the client-side game restoring packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientRestoreGameStatePacketHandler();
    }

    /**
     * The method returns the server-side game restoring packets handler
     * @return the server-side game restoring packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}

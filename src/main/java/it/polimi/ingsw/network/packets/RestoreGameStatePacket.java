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

    private final ArrayList<PlayerSave> playerSaves;
    private final String playerRestored;

    public RestoreGameStatePacket(String playerRestored, ArrayList<PlayerSave> playerSaves) {
        this.playerSaves = new ArrayList<>();
        this.playerSaves.addAll(playerSaves);
        this.playerRestored = playerRestored;
    }

    public String getPlayerRestored() {
        return playerRestored;
    }

    public ArrayList<PlayerSave> getPlayerSaves() {
        return playerSaves;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientRestoreGameStatePacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}

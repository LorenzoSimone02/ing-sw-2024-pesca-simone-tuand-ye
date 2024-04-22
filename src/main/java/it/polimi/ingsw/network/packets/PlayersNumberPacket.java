package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPlayersNumbersPacketHandler;

public class PlayersNumberPacket extends Packet{

    private final int playersNumber;

    public PlayersNumberPacket() {
        this.playersNumber = 0;
    }

    public PlayersNumberPacket(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return null;
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerPlayersNumbersPacketHandler();
    }
}

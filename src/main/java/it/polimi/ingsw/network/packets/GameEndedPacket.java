package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class GameEndedPacket extends Packet {

    private final String winner;

    public GameEndedPacket(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return null;
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}

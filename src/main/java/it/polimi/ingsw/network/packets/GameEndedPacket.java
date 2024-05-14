package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientGameEndedPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

import java.util.HashMap;

public class GameEndedPacket extends Packet {

    private final String winner;
    private final HashMap<String, Integer> scores;

    public GameEndedPacket(String winner, HashMap<String, Integer> scores) {
        this.winner = winner;
        this.scores = scores;
    }

    public String getWinner() {
        return winner;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientGameEndedPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}

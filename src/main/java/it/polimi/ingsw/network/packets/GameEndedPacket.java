package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientGameEndedPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameEndedPacket extends Packet {

    private final List<String> winners;
    private final HashMap<String, Integer> scores;

    public GameEndedPacket(List<String> winners, HashMap<String, Integer> scores) {
        this.winners = new ArrayList<>();
        this.winners.addAll(winners);
        this.scores = new HashMap<>();
        this.scores.putAll(scores);
    }

    public List<String> getWinners() {
        return winners;
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

package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientGameEndedPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * GameEndedPacket is a Packet that contains the information about the end of a game
 */
public class GameEndedPacket extends Packet {

    /**
     * The list of the usernames of the winners
     */
    private final List<String> winners;

    /**
     * The map that associates the players' usernames with their scores
     */
    private final HashMap<String, Integer> scores;

    /**
     * Constructor of the class
     * @param winners the list of the usernames of the winners
     * @param scores the map that associates the players' usernames with their scores
     */
    public GameEndedPacket(List<String> winners, HashMap<String, Integer> scores) {
        this.winners = new ArrayList<>();
        this.winners.addAll(winners);
        this.scores = new HashMap<>();
        this.scores.putAll(scores);
    }

    /**
     * The method returns the list of the usernames of the winners
     * @return the list of the usernames of the winners
     */
    public List<String> getWinners() {
        return winners;
    }

    /**
     * The method returns the map that associates the players' usernames with their scores
     * @return the map that associates the players' usernames with their scores
     */
    public HashMap<String, Integer> getScores() {
        return scores;
    }

    /**
     * The method returns the client-side endgame packets handler
     * @return the client-side endgame packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientGameEndedPacketHandler();
    }

    /**
     * The method returns the server-side endgame packets handler
     * @return the server-side endgame packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}

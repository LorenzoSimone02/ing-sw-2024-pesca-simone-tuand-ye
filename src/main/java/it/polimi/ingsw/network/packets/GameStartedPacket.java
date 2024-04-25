package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientGameStartedPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;

public class GameStartedPacket extends Packet {

    private final int gameID;
    private final ArrayList<String> players;
    private final String firstPlayer;

    public GameStartedPacket(Game game) {
        this.gameID = game.getInfo().getId();
        this.players = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            players.add(player.getUsername());
        }
        this.firstPlayer = game.getInfo().getFirstPlayer().getUsername();
    }

    public int getGameID() {
        return gameID;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientGameStartedPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}

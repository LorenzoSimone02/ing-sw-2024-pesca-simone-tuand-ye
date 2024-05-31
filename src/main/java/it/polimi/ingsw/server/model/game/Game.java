package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Table table;
    private final List<Player> players;
    private final List<Player> offlinePlayers;
    private final GameInfo info;

    public Game(int id) {
        this.table = new Table();
        this.players = new ArrayList<>();
        this.offlinePlayers = new ArrayList<>();
        this.info = new GameInfo(this, id);
    }

    public Table getTable() {
        return table;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getOfflinePlayers() {
        return offlinePlayers;
    }

    public GameInfo getInfo() {
        return info;
    }
}

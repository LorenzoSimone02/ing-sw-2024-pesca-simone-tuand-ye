package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The class represents a game.
 */
public class Game {

    /**
     * The table of the game.
     */
    private final Table table;

    /**
     * The list of players in the game.
     */
    private final List<Player> players;

    /**
     * The list of offline players in the game.
     */
    private final List<Player> offlinePlayers;

    /**
     * The info of the game.
     */
    private final GameInfo info;

    /**
     * Constructor of the class
     * @param id the id of the game
     */
    public Game(int id) {
        this.table = new Table();
        this.players = new ArrayList<>();
        this.offlinePlayers = new ArrayList<>();
        this.info = new GameInfo(this, id);
    }

    /**
     * Method returns the table of the game
     * @return the table of the game
     */
    public Table getTable() {
        return table;
    }

    /**
     * The method returns the list of players in the game
     * @return the list of players in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * The method returns the list of offline players in the game
     * @return the list of offline players in the game
     */
    public List<Player> getOfflinePlayers() {
        return offlinePlayers;
    }

    /**
     * The method returns the info of the game
     * @return the info of the game
     */
    public GameInfo getInfo() {
        return info;
    }
}

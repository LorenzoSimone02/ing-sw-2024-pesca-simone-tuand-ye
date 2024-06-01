package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the information of a game
 */
public class GameInfo {

    /**
     * The game that the info refers to
     */
    private final Game game;

    /**
     * The id of the game
     */
    private final int id;

    /**
     * The maximum number of players in the game
     */
    private int maxPlayers;

    /**
     * The first player of the game
     */
    private Player firstPlayer;

    /**
     * The current active player of the game
     */
    private Player activePlayer;

    /**
     * The list of winners of the game
     */
    private final List<Player> winners;

    /**
     * The status of the game
     */
    private GameStatusEnum gameStatus;

    /**
     * Constructor of the class
     * @param game the game that the info refers to
     * @param id the id of the game
     */
    public GameInfo(Game game, int id) {
        this.game = game;
        this.id = id;
        maxPlayers = 4;
        gameStatus = GameStatusEnum.WAITING_FOR_PLAYERS;
        winners = new ArrayList<>(4);
    }

    /**
     * The method returns the id of the game that the info refers to
     * @return the id of the game that the info refers to
     */
    public int getId() {
        return id;
    }

    /**
     * The method returns the number of players in the game
     * @return the number of players in the game
     */
    public int getPlayersNumber() {
        return game.getPlayers().size();
    }

    /**
     * The method returns the max number of players in the game that the info refers to
     * @return the max number of players in the game that the info refers to
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * The method sets the max number of players in the game that the info refers to
     * @param maxPlayers the max number of players in the game that the info refers to
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * The method returns the first player of the game
     * @return the first player of the game
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * The method returns the current active player of the game
     * @return the current active player of the game
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * The method sets the current active player of the game
     * @param activePlayer the current active player of the game
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * The method sets the first player of the game
     * @param firstPlayer the first player of the game
     */
    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    /**
     * The method returns the list of winners of the game
     * @return the list of winners of the game
     */
    public List<Player> getWinners() {
        return winners;
    }

    /**
     * The method adds a winner to the list of winners of the game
     * @param winner the winner to add
     */
    public void addWinner(Player winner) {
        winners.add(winner);
    }

    /**
     * The method returns the status of the game
     * @return the status of the game
     */
    public GameStatusEnum getGameStatus() {
        return gameStatus;
    }

    /**
     * The method sets the status of the game
     * @param gameStatus the status of the game
     */
    public void setGameStatus(GameStatusEnum gameStatus) {
        this.gameStatus = gameStatus;
    }
}

package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    private final Game game;
    private final int id;
    private int maxPlayers;
    private Player firstPlayer;
    private Player activePlayer;
    private final List<Player> winners;
    private GameStatusEnum gameStatus;

    public GameInfo(Game game, int id) {
        this.game = game;
        this.id = id;
        maxPlayers = 4;
        gameStatus = GameStatusEnum.WAITING_FOR_PLAYERS;
        winners = new ArrayList<>(4);
    }

    public int getId() {
        return id;
    }

    public int getPlayersNumber() {
        return game.getPlayers().size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public void addWinner(Player winner) {
        winners.add(winner);
    }

    public GameStatusEnum getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatusEnum gameStatus) {
        this.gameStatus = gameStatus;
    }
}

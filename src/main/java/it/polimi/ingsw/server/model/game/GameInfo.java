package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;

import java.util.List;

public class GameInfo {

    private final int id;
    private int playersNumber;
    private int maxPlayers;
    private Player firstPlayer;
    private Player activePlayer;
    private List<Player> winners;
    private GameStatusEnum gameStatus;

    public GameInfo(int id, int playersNumber) {
        this.id = id;
        this.playersNumber = playersNumber;
        this.maxPlayers = 4;
        gameStatus = GameStatusEnum.WAITING_FOR_PLAYERS;
    }

    public int getId() {
        return id;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
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

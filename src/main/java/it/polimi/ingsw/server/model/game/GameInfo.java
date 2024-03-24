package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;

public class GameInfo {
    private final int playersNumber;
    private Player firstPlayer;
    private Player winner;
    private GameStatusEnum gameStatus;

    public GameInfo(int playersNumber, GameStatusEnum gameStatus) {
        this.playersNumber = playersNumber;
        this.gameStatus = gameStatus;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameStatusEnum getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatusEnum gameStatus) {
        this.gameStatus = gameStatus;
    }
}

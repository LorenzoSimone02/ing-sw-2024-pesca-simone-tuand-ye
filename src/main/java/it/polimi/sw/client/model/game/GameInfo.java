package it.polimi.sw.client.model.game;

import it.polimi.sw.client.model.player.Player;

public class GameInfo{
    private int playersNumber;
    private Player firstPlayer;
    private Player winner;
    private GameStatusEnum gameStatus;

    public int getPlayersNumber() {
        return playersNumber;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public GameStatusEnum getGameStatus() {
        return gameStatus;
    }
}

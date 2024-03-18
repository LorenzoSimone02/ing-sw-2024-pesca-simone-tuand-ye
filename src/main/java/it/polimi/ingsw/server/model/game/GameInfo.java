package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;

public class GameInfo{
    private int playersNumber;
    private Player firstPlayer;
    private Player winner;
    private GameStatusEnum gameStatus;

    public GameInfo(){

    }

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

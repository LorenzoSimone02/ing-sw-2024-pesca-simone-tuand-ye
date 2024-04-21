package it.polimi.ingsw.server.model.exceptions;

public class GameStartException extends RuntimeException {
    public GameStartException() {
        super("Error while starting the game.");
    }
}

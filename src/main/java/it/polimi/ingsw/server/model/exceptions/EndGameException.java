package it.polimi.ingsw.server.model.exceptions;

public class EndGameException extends RuntimeException {
    public EndGameException() {
        super("The game has ended.");
    }
}

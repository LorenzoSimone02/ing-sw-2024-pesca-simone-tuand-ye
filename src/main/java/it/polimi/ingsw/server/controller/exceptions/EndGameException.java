package it.polimi.ingsw.server.controller.exceptions;

public class EndGameException extends RuntimeException {
    public EndGameException() {
        super("The game has ended.");
    }
}

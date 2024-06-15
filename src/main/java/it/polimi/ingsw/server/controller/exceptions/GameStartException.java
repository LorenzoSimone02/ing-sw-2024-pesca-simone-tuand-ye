package it.polimi.ingsw.server.controller.exceptions;

/**
 * The exception is thrown when an error occurs while starting the game
 */
public class GameStartException extends RuntimeException {

    /**
     * Class constructor that prints the error message about an error while starting the game
     */
    public GameStartException() {
        super("Error while starting the game.");
    }
}

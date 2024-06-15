package it.polimi.ingsw.server.controller.exceptions;

/**
 * The exception is thrown when a player tries to join a full lobby
 */
public class FullLobbyException extends RuntimeException {

    /**
     * Class constructor that prints the error message about the full lobby
     */
    public FullLobbyException() {
        super("Max number of players reached.");
    }
}

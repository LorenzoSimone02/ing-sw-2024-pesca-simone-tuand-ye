package it.polimi.ingsw.server.controller.exceptions;

/**
 * The exception is thrown when a player tries to register with a nickname that is already present
 */
public class DuplicatePlayerException extends RuntimeException {

    /**
     * Class constructor that prints the error message about the nickname that is already present
     * @param nickname the nickname that is already present
     */
    public DuplicatePlayerException(String nickname) {
        super("A Player with the Nickname " + nickname + " is already present.");
    }
}

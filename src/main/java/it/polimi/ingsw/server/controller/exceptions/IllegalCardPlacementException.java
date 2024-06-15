package it.polimi.ingsw.server.controller.exceptions;

/**
 * The exception is thrown when a player tries to place a card in an illegal position
 */
public class IllegalCardPlacementException extends RuntimeException {

    /**
     * Class constructor that prints the error message about the illegal card placement
     */
    public IllegalCardPlacementException() {
        super("The given card cannot be placed.");
    }
}

package it.polimi.ingsw.server.model.exceptions;

public class IllegalCardPlacementException extends RuntimeException {
    public IllegalCardPlacementException() {
        super("The given card cannot be placed.");
    }
}

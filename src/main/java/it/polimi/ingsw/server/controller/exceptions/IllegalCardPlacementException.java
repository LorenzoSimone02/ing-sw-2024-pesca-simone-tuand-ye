package it.polimi.ingsw.server.controller.exceptions;

public class IllegalCardPlacementException extends RuntimeException {
    public IllegalCardPlacementException() {
        super("The given card cannot be placed.");
    }
}

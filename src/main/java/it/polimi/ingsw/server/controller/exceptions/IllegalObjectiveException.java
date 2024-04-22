package it.polimi.ingsw.server.controller.exceptions;

public class IllegalObjectiveException extends RuntimeException {
    public IllegalObjectiveException() {
        super("There are already 2 common objective cards.");
    }
}

package it.polimi.ingsw.server.controller.exceptions;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException() {
        super("Wrong token has been assigned.");
    }
}

package it.polimi.ingsw.server.model.exceptions;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException() {
        super("Wrong token has been assigned.");
    }
}

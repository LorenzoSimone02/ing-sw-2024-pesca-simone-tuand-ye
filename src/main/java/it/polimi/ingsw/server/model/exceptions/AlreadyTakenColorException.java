package it.polimi.ingsw.server.model.exceptions;

public class AlreadyTakenColorException extends RuntimeException{
    public AlreadyTakenColorException() {
        super("Token color has already been taken.");
    }
}

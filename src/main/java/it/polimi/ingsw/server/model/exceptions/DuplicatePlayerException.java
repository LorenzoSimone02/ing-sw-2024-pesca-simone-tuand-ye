package it.polimi.ingsw.server.model.exceptions;

public class DuplicatePlayerException extends RuntimeException{
    public DuplicatePlayerException() {
        super("Player already added.");
    }
}

package it.polimi.ingsw.server.model.exceptions;

public class StartGameException extends RuntimeException{
    public StartGameException () {
        super("Error while starting the game.");
    }
}

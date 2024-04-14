package it.polimi.ingsw.server.model.exceptions;

public class FullLobbyException extends RuntimeException{
    public FullLobbyException() {
        super("Max number of players reached.");
    }
}

package it.polimi.ingsw.server.controller.exceptions;

public class FullLobbyException extends RuntimeException {
    public FullLobbyException() {
        super("Max number of players reached.");
    }
}

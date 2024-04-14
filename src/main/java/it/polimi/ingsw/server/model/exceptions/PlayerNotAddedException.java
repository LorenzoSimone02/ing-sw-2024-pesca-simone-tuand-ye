package it.polimi.ingsw.server.model.exceptions;

public class PlayerNotAddedException extends RuntimeException {
    public PlayerNotAddedException() {
        super("Player failed to be added.");
    }
}

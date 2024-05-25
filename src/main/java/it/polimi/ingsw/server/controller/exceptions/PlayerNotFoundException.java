package it.polimi.ingsw.server.controller.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String player) {
        super("Player " + player + " was not found.");
    }
}

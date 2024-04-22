package it.polimi.ingsw.server.controller.exceptions;

public class DuplicatePlayerException extends RuntimeException {

    public DuplicatePlayerException(String nickname) {
        super("A Player with the Nickname " + nickname + " is already present.");
    }
}

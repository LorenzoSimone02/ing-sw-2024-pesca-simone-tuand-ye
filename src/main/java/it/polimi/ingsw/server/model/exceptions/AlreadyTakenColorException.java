package it.polimi.ingsw.server.model.exceptions;

import it.polimi.ingsw.server.model.player.TokenColorEnum;

public class AlreadyTakenColorException extends RuntimeException {

    public AlreadyTakenColorException(TokenColorEnum color) {
        super("Token color" + color.toString() + " has already been taken.");
    }
}

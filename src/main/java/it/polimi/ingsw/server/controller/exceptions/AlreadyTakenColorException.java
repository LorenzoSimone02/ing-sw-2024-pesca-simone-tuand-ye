package it.polimi.ingsw.server.controller.exceptions;

import it.polimi.ingsw.server.model.player.TokenColorEnum;

public class AlreadyTakenColorException extends RuntimeException {

    public AlreadyTakenColorException(TokenColorEnum color) {
        super("The" + color.toString() + " token has already been taken.");
    }
}

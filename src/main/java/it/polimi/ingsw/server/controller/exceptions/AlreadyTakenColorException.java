package it.polimi.ingsw.server.controller.exceptions;

import it.polimi.ingsw.server.model.player.TokenColorEnum;

/**
 * The exception is thrown when a player tries to take a color that has already been taken
 */
public class AlreadyTakenColorException extends RuntimeException {

    /**
     * Class constructor that prints the error message about the color that has already been taken
     * @param color the color that has already been taken
     */
    public AlreadyTakenColorException(TokenColorEnum color) {
        super("The" + color.toString() + " token has already been taken.");
    }
}

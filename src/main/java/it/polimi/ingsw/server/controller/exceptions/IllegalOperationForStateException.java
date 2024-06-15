package it.polimi.ingsw.server.controller.exceptions;

import it.polimi.ingsw.server.model.game.GameStatusEnum;

/**
 * The exception is thrown when a player tries to perform an illegal action in the current game status
 */
public class IllegalOperationForStateException extends RuntimeException {

    /**
     * Class constructor that prints the error message about the illegal action in the current game status
     * @param status the current game status
     */
    public IllegalOperationForStateException(GameStatusEnum status) {
        super("This action cannot be performed in the GameStatus " + status + ".");
    }
}

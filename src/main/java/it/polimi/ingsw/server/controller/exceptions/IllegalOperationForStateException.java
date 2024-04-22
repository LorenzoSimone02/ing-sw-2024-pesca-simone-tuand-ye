package it.polimi.ingsw.server.controller.exceptions;

import it.polimi.ingsw.server.model.game.GameStatusEnum;

public class IllegalOperationForStateException extends RuntimeException {
    public IllegalOperationForStateException(GameStatusEnum status) {
        super("This action cannot be performed in the GameStatus " + status + ".");
    }
}

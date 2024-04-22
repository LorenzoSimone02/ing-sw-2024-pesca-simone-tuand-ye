package it.polimi.ingsw.server.controller.exceptions;

import it.polimi.ingsw.server.model.objectives.ObjectiveType;

public class InvalidObjectiveStrategyException extends RuntimeException {
    public InvalidObjectiveStrategyException(ObjectiveType type) {
        super("Invalid" + type.getStrategy().toString() + "pattern.");
    }
}

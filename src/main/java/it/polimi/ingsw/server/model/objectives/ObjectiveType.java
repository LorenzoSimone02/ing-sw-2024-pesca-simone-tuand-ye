package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.objectives.strategies.*;

public enum ObjectiveType {

    BOTTOM_LEFT_L_SHAPE(new BottomLeftLShape()),
    BOTTOM_RIGHT_L_SHAPE(new BottomRightLShape()),
    TOP_LEFT_L_SHAPE(new TopLeftLShape()),
    TOP_RIGHT_L_SHAPE(new TopRightLShape()),
    TOP_RIGHT_DIAGONAL(new TopRightDiagonal()),
    TOP_LEFT_DIAGONAL(new TopLeftDiagonal()),
    OBJECT(new ObjectStrategy()),
    RESOURCE(new ResourceStrategy());

    private final ObjectiveStrategy strategy;

    ObjectiveType(ObjectiveStrategy strategy) {
        this.strategy = strategy;
    }

    public ObjectiveStrategy getStrategy() {
        return strategy;
    }
}

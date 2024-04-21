package it.polimi.ingsw.server.model.card.goldStrategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldStrategy;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

public class ObjectCount implements GoldStrategy {

    private int basePoints;
    private ObjectTypeEnum objectNeeded;

    public ObjectCount(int basePoints, ObjectTypeEnum objectNeeded) {
        this.basePoints = basePoints;
        this.objectNeeded = objectNeeded;
    }

    @Override
    public int calculatePoints(Card[][] cards, int xCoord, int yCoord) {
        return 0;
    }
}

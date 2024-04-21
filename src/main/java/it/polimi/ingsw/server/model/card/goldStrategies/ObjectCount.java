package it.polimi.ingsw.server.model.card.goldStrategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldStrategy;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.Player;

public class ObjectCount implements GoldStrategy {

    private int basePoints;
    private ObjectTypeEnum objectNeeded;

    public ObjectCount(int basePoints, ObjectTypeEnum objectNeeded) {
        this.basePoints = basePoints;
        this.objectNeeded = objectNeeded;
    }

    @Override
    public int calculatePoints(Player player, int xCoord, int yCoord) {
        int objectCounter = 1;
        for (Object obj: player.getObjects()) {
            if (obj.equals(objectNeeded)) {
                objectCounter++;
            }
        }

        return objectCounter * basePoints;
    }
}

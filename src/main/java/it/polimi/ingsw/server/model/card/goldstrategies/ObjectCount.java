package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.card.GoldStrategy;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.Player;

public class ObjectCount implements GoldStrategy {

    private final int basePoints;
    private final ObjectTypeEnum objectNeeded;

    public ObjectCount(int basePoints, ObjectTypeEnum objectNeeded) {
        this.basePoints = basePoints;
        this.objectNeeded = objectNeeded;
    }

    @Override
    public int calculatePoints(Player player, int xCoord, int yCoord) {
        int objectCounter = 1;
        for (Object obj: player.getObjects()) {
            if (obj.type().equals(objectNeeded)) {
                objectCounter++;
            }
        }

        return objectCounter * basePoints;
    }
}

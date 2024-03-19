package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

import java.util.List;

public class Objective {
    private final int points;
    private List<Object> objectsNeeded;
    private final ObjectTypeEnum pattern;

    public Objective(int points, ObjectTypeEnum pattern, List<Object> objectsNeeded) {
        this.points = points;
        this.pattern = pattern;
        if (objectsNeeded != null)
            this.objectsNeeded.addAll(objectsNeeded);
    }

    public int getPoints() {
        return points;
    }

    public List<Object> getObjectsNeeded() {
        return objectsNeeded;
    }

    public ObjectTypeEnum getPattern() {
        return pattern;
    }

    public boolean meetsRequirements(Player player) {
        return false;
    }
}

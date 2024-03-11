package it.polimi.sw.client.model.objectives;

import it.polimi.sw.client.model.player.Player;
import it.polimi.sw.client.model.resources.Object;

import java.util.List;

public class Objective {
    private int points;
    private List<Object> objectsNeeded;
    private Pattern pattern;

    public int getPoints() {
        return points;
    }
    public List<Object> getObjectsNeeded() {
        return objectsNeeded;
    }
    public Pattern getPattern() {
        return pattern;
    }
    public boolean meetsRequirements(Player player){
        return false;
    }
}

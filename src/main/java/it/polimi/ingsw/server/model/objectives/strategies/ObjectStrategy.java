package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

import java.util.Collections;
import java.util.List;

public class ObjectStrategy implements ObjectiveStrategy {

    private final List<ObjectTypeEnum> objectRequirement;
    int pointsPerPattern;

    public ObjectStrategy(List<ObjectTypeEnum> objectRequirement, int pointsPerPattern) {
        this.objectRequirement = objectRequirement;
        this.pointsPerPattern = pointsPerPattern;
    }

    public int calculatePoints(Player player) {
        int points = 0;
        int counter = 0;
        for(int i = 0; i < objectRequirement.size(); i++)
        {
            if(counter <= 0) {
                counter = Collections.frequency(player.getObjects(), objectRequirement.get(i));
            }
            else{
                counter = Math.min(counter, Collections.frequency(player.getObjects(), objectRequirement.get(i)));
            }
        }
        points = counter * pointsPerPattern;
        return points;
    }
}

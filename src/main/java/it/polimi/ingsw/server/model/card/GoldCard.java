package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.corner.Corner;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.List;

public class GoldCard extends Card{
    private List<Corner> corners;
    private List<Resource> resourcesNeeded;
    private List<Object> objectsNeeded;
    private int points;
    private int xCoord;
    private int yCoord;

    public GoldCard (){

    }

    public List<Corner> getCorners() {
        return corners;
    }

    public List<Resource> getResourcesNeeded() {
        return resourcesNeeded;
    }

    public List<Object> getObjectsNeeded() {
        return objectsNeeded;
    }

    public int getPoints() {
        return points;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }
}

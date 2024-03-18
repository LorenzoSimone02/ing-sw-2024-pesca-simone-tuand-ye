package it.polimi.sw.client.model.card;

import it.polimi.sw.client.model.corner.Corner;
import it.polimi.sw.client.model.resources.Resource;

import java.util.List;

public class GoldCard {
    private List<Corner> corners;
    private List<Resource> resourcesNeeded;
    private List<Object> objectsNeeded;
    private int points;

    private int xCoord;
    private int yCoord;

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

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
}

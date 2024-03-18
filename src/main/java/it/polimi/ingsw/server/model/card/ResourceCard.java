package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.corner.Corner;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.List;

public class ResourceCard extends Card{
    private List<Corner> corners;
    private int points;
    private Resource backResource;
    private int xCoord;
    private int yCoord;

    public ResourceCard() {
    }

    public List<Corner> getCorners() {
        return corners;
    }
    public int getPoints() {
        return points;
    }
    public Resource getBackResource() {
        return backResource;
    }
    public int getXCoord() {
        return xCoord;
    }
    public int getYCoord() {
        return yCoord;
    }
}

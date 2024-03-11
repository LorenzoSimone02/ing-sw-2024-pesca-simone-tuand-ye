package it.polimi.sw.client.model.card;

import it.polimi.sw.client.model.corner.Corner;
import it.polimi.sw.client.model.resources.Resource;

import java.util.List;

public class ResourceCard {
    private List<Corner> corners;
    private int points;
    private Resource backResource;

    public List<Corner> getCorners() {
        return corners;
    }

    public int getPoints() {
        return points;
    }

    public Resource getBackResource() {
        return backResource;
    }
}

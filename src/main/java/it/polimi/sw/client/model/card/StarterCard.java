package it.polimi.sw.client.model.card;

import it.polimi.sw.client.model.corner.Corner;
import it.polimi.sw.client.model.resources.Resource;

import java.util.List;

public class StarterCard {
    private List<Corner> corners;
    private List<Resource> backResources;

    public List<Corner> getCorners() {
        return corners;
    }

    public List<Resource> getBackResources() {
        return backResources;
    }
}

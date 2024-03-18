package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.corner.Corner;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.List;

public class StarterCard extends Card{
    private List<Corner> corners;
    private List<Resource> backResources;

    public StarterCard() {
    }

    public List<Corner> getCorners() {
        return corners;
    }

    public List<Resource> getBackResources() {
        return backResources;
    }
}

package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.card.goldstrategies.GoldStrategyType;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * The class GoldCard represents a gold card in the game
 */
public class GoldCard extends ResourceCard {

    /**
     * The list of resources needed to place the card
     */
    private final List<Resource> resourcesNeeded;

    /**
     * The gold strategy of the card
     */
    private final GoldStrategyType pointsStrategy;

    /**
     * The description of the objective of the card
     */
    private final String objectiveDescription;

    /**
     * Constructor of the class
     * @param jsonData a JSON string containing the data of the gold card
     * @see com.google.gson.Gson
     */
    public GoldCard(String jsonData) {
        super(jsonData);
        Gson gson = new Gson();
        GoldCard proprieties = gson.fromJson(jsonData, GoldCard.class);
        this.pointsStrategy = proprieties.pointsStrategy;
        this.resourcesNeeded = proprieties.getResourcesNeeded();
        this.objectiveDescription = proprieties.getObjectiveDescription();
    }

    /**
     * The method returns a boolean that indicates if the player meets the requirements to place the card
     * @param playerResources the list of resources of the player
     */
    public boolean meetRequirements(List<Resource> playerResources) {

        HashMap<ResourceTypeEnum, Integer> currResources = new HashMap<>(Map.ofEntries(
                Map.entry(ResourceTypeEnum.PLANT, 0),
                Map.entry(ResourceTypeEnum.INSECT, 0),
                Map.entry(ResourceTypeEnum.ANIMAL, 0),
                Map.entry(ResourceTypeEnum.FUNGI, 0),
                Map.entry(ResourceTypeEnum.EMPTY, 0)));

        for (Resource res: playerResources) {
            currResources.put(res.getType(), currResources.get(res.getType()) + 1);
        }

        for (Resource res: resourcesNeeded) {
            currResources.put(res.getType(), currResources.get(res.getType()) - 1);
        }

        for (ResourceTypeEnum resType: ResourceTypeEnum.values()) {
            if (currResources.get(resType) < 0) return false;
        }

        return true;
    }


    /**
     * The method returns the list of resources needed to place the card
     * @return the list of resources needed to place the card
     */
    public List<Resource> getResourcesNeeded() {
        return resourcesNeeded;
    }

    /**
     * The method returns the gold strategy of the card
     * @return the gold strategy of the card
     */
    public GoldStrategyType getPointsStrategy() {
        return pointsStrategy;
    }

    /**
     * The method returns the description of the objective of the card
     * @return the description of the objective of the card
     */
    public String getObjectiveDescription() {
        return objectiveDescription;
    }

}

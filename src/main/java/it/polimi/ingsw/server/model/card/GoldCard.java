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

public class GoldCard extends ResourceCard {

    private final List<Resource> resourcesNeeded;
    private final GoldStrategyType pointsStrategy;
    private final String objectiveDescription;

    public GoldCard(File jsonFile) {
        super(jsonFile);
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            GoldCard proprieties = gson.fromJson(reader, GoldCard.class);
            this.pointsStrategy = proprieties.pointsStrategy;
            this.resourcesNeeded = proprieties.getResourcesNeeded();
            this.objectiveDescription = proprieties.getObjectiveDescription();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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


    public List<Resource> getResourcesNeeded() {
        return resourcesNeeded;
    }

    public GoldStrategyType getPointsStrategy() {
        return pointsStrategy;
    }

    public String getObjectiveDescription() {
        return objectiveDescription;
    }

}

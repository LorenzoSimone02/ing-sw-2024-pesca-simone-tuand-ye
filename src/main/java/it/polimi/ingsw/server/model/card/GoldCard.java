package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.card.goldstrategies.GoldStrategyType;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class GoldCard extends ResourceCard {

    private final List<Resource> resourcesNeeded;
    private final GoldStrategyType pointsStrategy;

    public GoldCard(File jsonFile) {
        super(jsonFile);
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            GoldCard proprieties = gson.fromJson(reader, GoldCard.class);
            this.pointsStrategy = proprieties.pointsStrategy;
            this.resourcesNeeded = proprieties.getResourcesNeeded();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Resource> getResourcesNeeded() {
        return resourcesNeeded;
    }

    public GoldStrategyType getPointsStrategy() {
        return pointsStrategy;
    }

}

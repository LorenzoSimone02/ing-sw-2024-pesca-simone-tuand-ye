package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class GoldCard extends ResourceCard {

    private final List<Resource> resourcesNeeded;
    private final List<Object> objectsNeeded;
    private final int points;

    public GoldCard(File jsonFile) {
        super(jsonFile);
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            GoldCard proprieties = gson.fromJson(reader, GoldCard.class);
            this.points = proprieties.getPoints();
            this.resourcesNeeded = proprieties.getResourcesNeeded();
            this.objectsNeeded = proprieties.getObjectsNeeded();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
}

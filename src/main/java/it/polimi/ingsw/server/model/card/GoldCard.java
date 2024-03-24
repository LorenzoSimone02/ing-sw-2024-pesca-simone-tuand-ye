package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class GoldCard extends ResourceCard {
    private List<Resource> resourcesNeeded;
    private List<Object> objectsNeeded;
    private int points;

    public GoldCard(File jsonFile) {
        super(jsonFile);

        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            Properties data = gson.fromJson(reader, Properties.class);

            //Esempio ma solo con attributi extra della gold card
            this.points = Integer.parseInt(data.getProperty("points"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
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

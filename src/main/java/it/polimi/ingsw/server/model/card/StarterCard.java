package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.corner.Corner;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class StarterCard extends Card{
    private List<Corner> corners;
    private List<Resource> backResources;

    public StarterCard(File jsonFile) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            Properties data = gson.fromJson(reader, Properties.class);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Corner> getCorners() {
        return corners;
    }

    public List<Resource> getBackResources() {
        return backResources;
    }
}

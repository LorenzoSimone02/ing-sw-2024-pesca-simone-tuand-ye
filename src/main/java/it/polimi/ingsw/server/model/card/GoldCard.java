package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.corner.Corner;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class GoldCard extends Card {
    private List<Corner> corners;
    private List<Resource> resourcesNeeded;
    private List<Object> objectsNeeded;
    private int points;
    private int xCoord;
    private int yCoord;

    public GoldCard(File jsonFile) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            Properties data = gson.fromJson(reader, Properties.class);

            //Esempio
            this.points = Integer.parseInt(data.getProperty("points"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public List<Corner> getCorners() {
        return corners;
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

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}

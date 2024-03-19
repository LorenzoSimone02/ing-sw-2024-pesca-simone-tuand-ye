package it.polimi.ingsw.server.model.card;

import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.corner.Corner;
import it.polimi.ingsw.server.model.resources.Resource;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class ResourceCard extends Card {
    private List<Corner> corners;
    private int points;
    private Resource backResource;
    private int xCoord;
    private int yCoord;

    public ResourceCard(File jsonFile) {
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

    public int getPoints() {
        return points;
    }

    public Resource getBackResource() {
        return backResource;
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

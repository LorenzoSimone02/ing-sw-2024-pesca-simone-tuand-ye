package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class ResourceCard extends Card {
    private int xCoord;
    private int yCoord;
    private int points;
    private List<Corner> corners;
    private Resource backResource;

    public ResourceCard(File jsonFile) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            Properties data = gson.fromJson(reader, Properties.class);

            //Esempio
            this.id = Integer.parseInt(data.getProperty("id"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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

    private int getPoints() {
        return points;
    }

    public List<Corner> getCorners() {
        return corners;
    }

    public Resource getBackResource() {
        return backResource;
    }

    public boolean canBePlaced(int x, int y){

        return false;
    }

}

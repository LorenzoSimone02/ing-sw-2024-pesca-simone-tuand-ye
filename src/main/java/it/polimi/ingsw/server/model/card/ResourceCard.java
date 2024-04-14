package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.card.corner.CornerLocationEnum;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceCard extends Card {
    private int xCoord;
    private int yCoord;
    private final int points;
    private final List<Corner> corners;
    private final Resource backResource;

    public ResourceCard(File jsonFile) {
        this.corners = new ArrayList<>();
        this.corners.add(new Corner(CornerLocationEnum.TOP_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.TOP_RIGHT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_RIGHT, null, null, FaceEnum.BACK, true));

        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            ResourceCard proprieties = gson.fromJson(reader, ResourceCard.class);
            this.id = proprieties.getId();
            this.color = proprieties.getColor();
            this.face = proprieties.getFace();
            this.points = proprieties.getPoints();
            this.backResource = proprieties.getBackResource();
            this.corners.addAll(proprieties.getCorners());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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

    public Corner getCorner(CornerLocationEnum location) {
        return corners.stream().filter(corner -> corner.getLocation().equals(location)).findFirst().orElse(null);
    }

    public Resource getBackResource() {
        return backResource;
    }

}

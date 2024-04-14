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

public class StarterCard extends Card {

    private int xCoord;
    private int yCoord;
    private final List<Corner> corners;
    private final List<Resource> frontResources;

    public StarterCard(File jsonFile) {
        this.corners = new ArrayList<>();
        this.corners.add(new Corner(CornerLocationEnum.TOP_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.TOP_RIGHT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_RIGHT, null, null, FaceEnum.BACK, true));

        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            StarterCard proprieties = gson.fromJson(reader, StarterCard.class);
            this.id = proprieties.getId();
            this.color = proprieties.getColor();
            this.face = proprieties.getFace();
            this.frontResources = proprieties.getFrontResources();
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

    public List<Corner> getCorners() {
        return corners;
    }

    public List<Resource> getFrontResources() {
        return frontResources;
    }
}

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
    final int points;
    final List<Corner> corners;
    final List<Resource> backResources;

    public ResourceCard(String jsonData) {
        this.corners = new ArrayList<>();
        this.corners.add(new Corner(CornerLocationEnum.TOP_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.TOP_RIGHT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_LEFT, null, null, FaceEnum.BACK, true));
        this.corners.add(new Corner(CornerLocationEnum.BOTTOM_RIGHT, null, null, FaceEnum.BACK, true));

        Gson gson = new Gson();
        ResourceCard proprieties = gson.fromJson(jsonData, ResourceCard.class);
        this.id = proprieties.getId();
        this.color = proprieties.getColor();
        this.face = proprieties.getFace();
        this.points = proprieties.getPoints();
        this.backResources = proprieties.getBackResources();
        this.corners.addAll(proprieties.getCorners());
    }

    public int getPoints() {
        return points;
    }

    public List<Corner> getCorners() {
        return corners;
    }

    public Corner getCorner(CornerLocationEnum location) {
        return corners.stream().filter(corner -> corner.getLocation().equals(location) && corner.getFace().equals(this.getFace())).findFirst().orElse(null);
    }

    public List<Resource> getBackResources() {
        return backResources;
    }
}

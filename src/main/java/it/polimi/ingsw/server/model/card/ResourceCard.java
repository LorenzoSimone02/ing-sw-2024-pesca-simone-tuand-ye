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

/**
 * The class ResourceCard represents a resource card in the game
 */
public class ResourceCard extends Card {

    /**
     * The points of the card
     */
    final int points;

    /**
     * The list of corners of the card
     */
    final List<Corner> corners;

    /**
     * The list of resources on the back of the card
     */
    final List<Resource> backResources;

    /**
     * Constructor of the class
     * @param jsonData a JSON string containing the data of the resource card
     * @see com.google.gson.Gson
     */
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

    /**
     * The method returns the points of the card
     * @return the points of the card
     */
    public int getPoints() {
        return points;
    }

    /**
     * The method returns the list of corners of the card
     * @return the list of corners of the card
     */
    public List<Corner> getCorners() {
        return corners;
    }

    /**
     * The method returns a corner of the card based on the location
     * @param location the location of the corner
     * @return the corner of the card based on the location if it exists, null otherwise
     */
    public Corner getCorner(CornerLocationEnum location) {
        return corners.stream().filter(corner -> corner.getLocation().equals(location) && corner.getFace().equals(this.getFace())).findFirst().orElse(null);
    }

    /**
     * The method returns the list of resources on the back of the card
     * @return the list of resources on the back of the card
     */
    public List<Resource> getBackResources() {
        return backResources;
    }
}

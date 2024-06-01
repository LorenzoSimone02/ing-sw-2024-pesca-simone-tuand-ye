package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;

/**
 * The class StarterCard represents a starter card in the game
 */
public class StarterCard extends ResourceCard {

    /**
     * Constructor of the class
     * @param jsonData a JSON string containing the data of the starter card
     * @see com.google.gson.Gson
     */
    public StarterCard(String jsonData) {
        super(jsonData);
        this.corners.clear();
        Gson gson = new Gson();
        StarterCard proprieties = gson.fromJson(jsonData, StarterCard.class);
        this.corners.addAll(proprieties.getCorners());
    }
}

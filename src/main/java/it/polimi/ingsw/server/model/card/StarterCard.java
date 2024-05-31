package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;

public class StarterCard extends ResourceCard {

    public StarterCard(String jsonData) {
        super(jsonData);
        this.corners.clear();
        Gson gson = new Gson();
        StarterCard proprieties = gson.fromJson(jsonData, StarterCard.class);
        this.corners.addAll(proprieties.getCorners());
    }
}

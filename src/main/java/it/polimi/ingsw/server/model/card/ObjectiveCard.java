package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.objectives.ObjectiveType;
import it.polimi.ingsw.server.model.player.Player;

/**
 * The class ObjectiveCard represents an objective card in the game
 */
public class ObjectiveCard extends Card {

    /**
     * The type of the objective of the card
     */
    private final ObjectiveType objectiveType;

    /**
     * The description of the objective of the card
     */
    private final String objectiveDescription;

    /**
     * Constructor of the class
     * @param jsonData a JSON string containing the data of the objective card
     * @see com.google.gson.Gson
     */
    public ObjectiveCard(String jsonData) {
        Gson gson = new Gson();
        ObjectiveCard proprieties = gson.fromJson(jsonData, ObjectiveCard.class);
        this.id = proprieties.getId();
        this.color = proprieties.getColor();
        this.face = proprieties.getFace();
        this.objectiveType = proprieties.getObjectiveType();
        this.objectiveDescription = proprieties.getObjectiveDescription();
    }

    /**
     * The method returns the type of the objective of the card
     * @return the type of the objective of the card
     */
    public ObjectiveType getObjectiveType() {
        return objectiveType;
    }

    /**
     * The method returns the description of the objective of the card
     * @return the description of the objective of the card
     */
    public String getObjectiveDescription() {
        return objectiveDescription;
    }

    /**
     * The method calculates the points of the player based on the objective of the card
     * @param player the player that has to calculate the points
     * @return the points of the player based on the objective of the card
     */
    public int calculatePoints(Player player) {
        return objectiveType.getStrategy().calculatePoints(player);
    }
}

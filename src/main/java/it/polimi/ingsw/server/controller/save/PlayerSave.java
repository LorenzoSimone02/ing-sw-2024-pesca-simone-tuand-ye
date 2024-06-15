package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class that represents the player status saving in case of disconnection
 */
public class PlayerSave implements Serializable {

    /**
     * The saved username of the player
     */
    private final String username;

    /**
     * The saved score of the player
     */
    private final int score;

    /**
     * The saved token color of the player
     */
    private final String tokenColor;

    /**
     * The saved placed cards of the player
     */
    private final CardSave[][] cards;

    /**
     * The saved in hands card of the player
     */
    private final ArrayList<CardSave> cardsInHand;

    /**
     * The saved starter card of the player
     */
    private final CardSave starterCard;

    /**
     * The saved objective card of the player
     */
    private final CardSave objectiveCard;

    /**
     * The saved resources and objects of the player
     */
    private final HashMap<String, Integer> resourcesAndObjects;

    /**
     * The constructor of the class
     * @param player the player
     */
    public PlayerSave(Player player) {
        this.username = player.getUsername();
        this.score = player.getScore();
        this.tokenColor = player.getToken().getColor().toString();
        this.cards = new CardSave[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                if (player.getCardAt(i, j).isPresent()) {
                    this.cards[i][j] = new CardSave(player.getCardAt(i, j).get());
                }
            }
        }
        this.cardsInHand = new ArrayList<>(3);
        for (Card cards : player.getCardsInHand()) {
            this.cardsInHand.add(new CardSave(cards));
        }
        this.starterCard = new CardSave(player.getStarterCard());
        this.objectiveCard = new CardSave(player.getObjectiveCard());
        this.resourcesAndObjects = new HashMap<>();
        for(Resource res : player.getResources()){
            this.resourcesAndObjects.put(res.getType().toString(), resourcesAndObjects.getOrDefault(res.getType().toString(), 0) + 1);
        }
        for(Object obj : player.getObjects()){
            this.resourcesAndObjects.put(obj.getType().toString(), resourcesAndObjects.getOrDefault(obj.getType().toString(), 0) + 1);
        }
    }

    /**
     * The method returns the saved username of the player
     * @return the saved username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method returns the saved score of the player
     * @return the saved score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * The method returns the saved token color of the player
     * @return the saved token color of the player
     */
    public String getTokenColor() {
        return tokenColor;
    }

    /**
     * The method returns the saved placed cards of the player
     * @return the saved placed cards of the player
     */
    public CardSave[][] getCards() {
        return cards;
    }

    /**
     * The method returns the saved in hands card of the player
     * @return the saved in hands card of the player
     */
    public ArrayList<CardSave> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * The method returns the saved starter card of the player
     * @return the saved starter card of the player
     */
    public CardSave getStarterCard() {
        return starterCard;
    }

    /**
     * The method returns the saved objective card of the player
     * @return the saved objective card of the player
     */
    public CardSave getObjectiveCard() {
        return objectiveCard;
    }

    /**
     * The method returns the saved resources and objects of the player
     * @return the saved resources and objects of the player
     */
    public HashMap<String, Integer> getResourcesAndObjects() {
        return resourcesAndObjects;
    }
}

package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerSave implements Serializable {

    private final String username;
    private final int score;
    private final String tokenColor;
    private final CardSave[][] cards;
    private final ArrayList<CardSave> cardsInHand;
    private final CardSave starterCard;
    private final CardSave objectiveCard;
    private final HashMap<String, Integer> resourcesAndObjects;

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

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getTokenColor() {
        return tokenColor;
    }

    public CardSave[][] getCards() {
        return cards;
    }

    public ArrayList<CardSave> getCardsInHand() {
        return cardsInHand;
    }

    public CardSave getStarterCard() {
        return starterCard;
    }

    public CardSave getObjectiveCard() {
        return objectiveCard;
    }

    public HashMap<String, Integer> getResourcesAndObjects() {
        return resourcesAndObjects;
    }
}

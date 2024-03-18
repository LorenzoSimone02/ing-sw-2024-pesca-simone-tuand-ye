package it.polimi.sw.client.model.player;

import it.polimi.sw.client.model.card.Card;
import it.polimi.sw.client.model.card.ObjectiveCard;
import it.polimi.sw.client.model.card.StarterCard;
import it.polimi.sw.client.model.resources.Resource;

import java.util.List;

public class Player {
    private String nickname;
    private PlayerToken token;
    private List<Card> cardsInHand;
    private StarterCard starterCard;
    private ObjectiveCard objectiveCard;
    private List<Resource> resources;
    private boolean first;
    private int score;

    public String getNickname() {
        return nickname;
    }

    public PlayerToken getToken() {
        return token;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public boolean isFirst() {
        return first;
    }

    public int getScore() {
        return score;
    }
    public void placeCard(Card card) {

    }

    public boolean canPlaceCard(Card card) {
        return false;
    }


    public void endTurn() {

    }

    public void chooseObjectiveCard(){

    }
}

package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;

import java.util.LinkedList;

public class PlayerState {

    private final String username;
    private String tokenColor;
    private final ResourceCard[][] cardsPlaced;
    private final LinkedList<ResourceCard> orderedCardsPlaced;
    int score;

    public PlayerState(String username) {
        this.username = username;
        this.cardsPlaced = new ResourceCard[81][81];
        this.orderedCardsPlaced = new LinkedList<>();
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTokenColor() {
        return tokenColor;
    }

    public void setTokenColor(String tokenColor) {
        this.tokenColor = tokenColor;
    }

    public ResourceCard[][] getCardsPlaced() {
        return cardsPlaced;
    }

    public LinkedList<ResourceCard> getOrderedCardsPlaced() {
        return orderedCardsPlaced;
    }

    public void addOrderedCard(ResourceCard card) {
        orderedCardsPlaced.add(card);
    }

    public void setCardPlaced(ResourceCard card, int x, int y) {
        cardsPlaced[x][y] = card;
    }

    public void setStarterCard(StarterCard card){
        setCardPlaced(card, 40, 40);
    }
}

package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.server.model.card.ResourceCard;

import java.util.ArrayList;

public class PlayerState {

    private final String username;
    private String color;
    private final ArrayList<ResourceCard> cardsInHand;
    int score;

    public PlayerState(String username) {
        this.username = username;
        this.cardsInHand = new ArrayList<>(3);
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<ResourceCard> getCardsInHand() {
        return cardsInHand;
    }

    public void addCardInHand(ResourceCard card) {
        cardsInHand.add(card);
    }

    public void removeCardInHand(ResourceCard card) {
        cardsInHand.remove(card);
    }
}

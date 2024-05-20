package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.server.model.card.ResourceCard;

import java.util.ArrayList;

public class PlayerState {

    private final String username;
    private String color;
    private final ArrayList<ResourceCard> cardsPlaced;
    int score;

    public PlayerState(String username) {
        this.username = username;
        this.cardsPlaced = new ArrayList<>(10);
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

    public ArrayList<ResourceCard> getCardsPlaced() {
        return cardsPlaced;
    }

    public void addCardPlaced(ResourceCard card) {
        cardsPlaced.add(card);
    }

    public void removeCardPlaced(ResourceCard card) {
        cardsPlaced.remove(card);
    }
}

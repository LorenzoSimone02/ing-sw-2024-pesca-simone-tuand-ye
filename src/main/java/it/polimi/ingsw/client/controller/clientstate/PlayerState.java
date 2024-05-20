package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.server.model.card.ResourceCard;

public class PlayerState {

    private final String username;
    private String color;
    private final ResourceCard[][] cardsPlaced;
    int score;

    public PlayerState(String username) {
        this.username = username;
        this.cardsPlaced = new ResourceCard[81][81];
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

    public ResourceCard[][] getCardsPlaced() {
        return cardsPlaced;
    }

    public void setCardPlaced(ResourceCard card, int x, int y) {
        cardsPlaced[x][y] = card;
    }
}

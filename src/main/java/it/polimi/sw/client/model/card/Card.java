package it.polimi.sw.client.model.card;

public abstract class Card {
    private CardColorEnum color;
    private CardFaceEnum face;

    public CardColorEnum getColor() {
        return color;
    }
    public CardFaceEnum getFace() {
        return face;
    }
    public void turnCard() {

    }
}

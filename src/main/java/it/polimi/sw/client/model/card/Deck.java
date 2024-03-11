package it.polimi.sw.client.model.card;

import java.util.List;

public class Deck {
    private List<Card> cards;
    public void shuffleDeck() {}
    public void drawCard() {}
    public Card getCards() {
        return (Card) cards;
    }

}

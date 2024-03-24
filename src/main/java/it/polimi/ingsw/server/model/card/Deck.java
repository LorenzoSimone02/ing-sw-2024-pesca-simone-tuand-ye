package it.polimi.ingsw.server.model.card;

import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(int numCards) {
        this.shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.removeFirst();
    }

    public List<Card> getCards() {
        return cards;
    }

}

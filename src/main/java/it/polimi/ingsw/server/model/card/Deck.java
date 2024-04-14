package it.polimi.ingsw.server.model.card;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    private final Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public void addCard(Card card) {
        cards.push(card);
    }

    public Card drawCard() {
        return cards.pop();
    }

    public List<Card> getCards() {
        return cards;
    }

}

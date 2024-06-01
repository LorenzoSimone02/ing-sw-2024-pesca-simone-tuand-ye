package it.polimi.ingsw.server.model.card;

import java.util.Collections;
import java.util.Stack;

/**
 * The class Deck represents the deck of cards in the game
 */
public class Deck {

    /**
     * The stack of cards of a deck
     */
    private final Stack<Card> cards;

    /**
     * Constructor of the class
     */
    public Deck() {
        cards = new Stack<>();
    }

    /**
     * The method shuffles the deck of cards
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * The method adds a card to the deck
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.push(card);
    }

    /**
     * The method draws a card from the deck
     * @return the card drawn from the deck
     */
    public Card drawCard() {
        return cards.pop();
    }

    /**
     * The method returns the stack of cards of the deck
     * @return the stack of cards of the deck
     */
    public Stack<Card> getCards() {
        return cards;
    }

}

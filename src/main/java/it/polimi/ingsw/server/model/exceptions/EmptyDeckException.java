package it.polimi.ingsw.server.model.exceptions;

import it.polimi.ingsw.server.model.card.Deck;

public class EmptyDeckException extends RuntimeException{
    public EmptyDeckException(Deck deck) {
        super(deck.toString() + "is empty.");
    }
}

package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;

import java.io.Serializable;

public class CardSave implements Serializable {

    private final int id;
    private final String face;

    public CardSave(Card card) {
        this.id = card.getId();
        this.face = card.getFace().toString();
    }

    public int getId() {
        return id;
    }

    public String getFace() {
        return face;
    }
}

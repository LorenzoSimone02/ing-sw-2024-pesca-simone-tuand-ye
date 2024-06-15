package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;

import java.io.Serializable;

/**
 * The class that represents the card status saving in case of disconnection

 */
public class CardSave implements Serializable {

    /**
     * The ID of the card
     */
    private final int id;

    /**
     * The face of the card
     */
    private final String face;

    /**
     * The constructor of the class
     * @param card the card
     */
    public CardSave(Card card) {
        this.id = card.getId();
        this.face = card.getFace().toString();
    }

    /**
     * The method returns the ID of the card
     * @return the ID of the card
     */
    public int getId() {
        return id;
    }

    /**
     * The method returns the face of the card
     * @return the face of the card
     */
    public String getFace() {
        return face;
    }
}

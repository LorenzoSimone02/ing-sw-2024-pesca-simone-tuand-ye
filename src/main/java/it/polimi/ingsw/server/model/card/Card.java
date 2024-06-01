package it.polimi.ingsw.server.model.card;

/**
 * The abstract class Card represents a generic card in the game
 */
public abstract class Card {

    /**
     * The id of the card
     */
    int id;

    /**
     * The color of the card
     */
    CardColorEnum color;

    /**
     * The face of the card
     */
    FaceEnum face;

    /**
     * The method returns the id of the card
     * @return the id of the card
     */
    public int getId() {
        return id;
    }

    /**
     * The method returns the color of the card
     * @return the color of the card
     */
    public CardColorEnum getColor() {
        return color;
    }

    /**
     * The method returns the face of the card
     * @return the face of the card
     */
    public FaceEnum getFace() {
        return face;
    }

    /**
     * The method sets the face of the card
     * @param face the face of the card
     */
    public void setFace(FaceEnum face) {
        this.face = face;
    }
}

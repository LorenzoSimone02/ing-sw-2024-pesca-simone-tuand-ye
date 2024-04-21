package it.polimi.ingsw.server.model.card;

public abstract class Card {

    int id;
    CardColorEnum color;
    FaceEnum face;

    public int getId() {
        return id;
    }

    public CardColorEnum getColor() {
        return color;
    }

    public FaceEnum getFace() {
        return face;
    }

    public void setFace(FaceEnum face) {
        this.face = face;
    }
}

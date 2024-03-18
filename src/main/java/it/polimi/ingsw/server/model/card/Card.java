package it.polimi.ingsw.server.model.card;

public abstract class Card {
    int id;
    private CardColorEnum color;
    private CardFaceEnum face;

    public int getId() {
        return id;
    }

    public CardColorEnum getColor() {
        return color;
    }

    public CardFaceEnum getFace() {
        return face;
    }

    public void turnCard() {
        if (this.face == CardFaceEnum.FRONT) {
            this.face = CardFaceEnum.BACK;
        } else if (this.face == CardFaceEnum.BACK) {
            this.face = CardFaceEnum.FRONT;
        }
    }


}

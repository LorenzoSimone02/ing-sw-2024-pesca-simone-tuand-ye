package it.polimi.ingsw.server.model.card;

public abstract class Card {
    int id;
    private CardColorEnum color;
    private FaceEnum face;

    public int getId() {
        return id;
    }

    public CardColorEnum getColor() {
        return color;
    }

    public FaceEnum getFace() {
        return face;
    }

    public void turnCard() {
        if (this.face == FaceEnum.FRONT) {
            this.face = FaceEnum.BACK;
        } else if (this.face == FaceEnum.BACK) {
            this.face = FaceEnum.FRONT;
        }
    }


}

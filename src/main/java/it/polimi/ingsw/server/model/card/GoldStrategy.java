package it.polimi.ingsw.server.model.card;

public interface GoldStrategy {

    public int calculatePoints(Card[][] cards, int xCoord, int yCoord);
}

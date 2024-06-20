package it.polimi.ingsw.client.view.gui;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private List<Card> cardsOnTable;
    private List<Card> cardsInHand;



    public Player(){
        this.name = "Marco";
        this.score = 0;
        this.cardsOnTable = new ArrayList<Card>();
        this.cardsInHand = new ArrayList<Card>();
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public List<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    public void addCardToHand(Card card){
        this.cardsInHand.add(card);
    }

    public void placeCard(Card card){
        this.cardsOnTable.add(card);
    }

}

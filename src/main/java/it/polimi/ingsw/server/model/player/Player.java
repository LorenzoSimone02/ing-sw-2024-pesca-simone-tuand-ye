package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.List;

public class Player {
    private String nickname;
    private final PlayerToken token;
    private Game game;
    private Card[][] cards;
    private List<Card> cardsInHand;
    private StarterCard starterCard;
    private ObjectiveCard objectiveCard;
    private List<Resource> resources;
    private boolean first;
    private int score;

    public Player(PlayerToken token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public PlayerToken getToken() {
        return token;
    }

    public Game getGame() {
        return game;
    }

    public Card[][] getCards() {
        return cards;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public boolean isFirst() {
        return first;
    }

    public int getScore() {
        return score;
    }

    public void placeCard(Card card) {

    }

    public boolean canPlaceCard(GoldCard card) {
        return this.getResources().containsAll(card.getResourcesNeeded());
    }


    public void endTurn() {

    }

    public void chooseObjectiveCard() {

    }
}

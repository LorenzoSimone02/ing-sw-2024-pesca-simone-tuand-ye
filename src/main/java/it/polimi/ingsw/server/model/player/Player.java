package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.List;

public class Player {
    private final String nickname;
    private final PlayerToken token;
    private final Game game;
    private Card[][] cards;
    private List<Card> cardsInHand;
    private final StarterCard starterCard;
    private ObjectiveCard objectiveCard;
    private List<Resource> resources;
    private final boolean first;
    private int score;

    public Player(PlayerToken token, String nickname, Game game, StarterCard starterCard, boolean first) {
        this.token = token;
        this.nickname = nickname;
        this.game = game;
        this.starterCard = starterCard;
        this.first = first;
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

    public void placeCard(ResourceCard card) {
    }

    public void placeCard(GoldCard card, int x, int y) {
        if(canPlaceCard(card, x, y)){
            card.setXCoord(x);
            card.setYCoord(y);
            cards[x][y] = card;
        }
    }

    public boolean canPlaceCard(GoldCard card, int x, int y) {
        return this.getResources().containsAll(card.getResourcesNeeded());
    }


    public void endTurn() {

    }

    public void chooseObjectiveCard() {

    }
}

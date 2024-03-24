package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.List;
import java.util.Optional;

public class Player {
    private final String nickname;
    private PlayerToken token;
    private final Game game;
    private Card[][] cards;
    private List<Card> cardsInHand;
    private StarterCard starterCard;
    private ObjectiveCard objectiveCard;
    private List<Resource> resources;
    private boolean first;
    private int score;

    public Player(String nickname, Game game) {
        this.nickname = nickname;
        this.game = game;
        this.score = 0;
    }

    public String getNickname() {
        return nickname;
    }

    public PlayerToken getToken() {
        return token;
    }

    public void setToken(PlayerToken token) {
        this.token = token;
    }

    public Game getGame() {
        return game;
    }

    public Card[][] getCards() {
        return cards;
    }

    public Optional<Card> getCardAt(int x, int y) {
        return Optional.ofNullable(getCards()[x][y]);
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
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

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void placeCard(ResourceCard card, int x, int y) {
        if (card.canBePlaced(x, y)) {
            card.setXCoord(x);
            card.setYCoord(y);
            cards[x][y] = card;
        }
    }

    public void chooseToken(PlayerToken token) {
        for (Player player : game.getPlayers()) {
            if (player.getToken().color().equals(token.color())) {
                // inserire commento da mostrare al player di cambiare colore
                return;
            }
        }
        this.setToken(token);
    }

    public void endTurn() {

    }

    public void chooseObjectiveCard() {

    }
}

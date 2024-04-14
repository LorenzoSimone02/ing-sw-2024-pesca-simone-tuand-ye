package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.card.corner.CornerLocationEnum;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player {
    private final String nickname;
    private PlayerToken token;
    private final Game game;
    private final Card[][] cards;
    private final List<Card> cardsInHand;
    private StarterCard starterCard;
    private ObjectiveCard objectiveCard;
    private final List<Resource> resources;
    private boolean first;
    private int score;

    public Player(String nickname, Game game) {
        this.nickname = nickname;
        this.game = game;
        this.score = 0;
        this.cards = new Card[81][81];
        this.cardsInHand = new ArrayList<>();
        this.resources = new ArrayList<>();
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

    public void addCardInHand(Card card) {
        cardsInHand.add(card);
    }

    public void removeCardInHand(Card card) {
        cardsInHand.remove(card);
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
        starterCard.setXCoord(40);
        starterCard.setYCoord(40);
        setCard(starterCard, 40, 40);
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
        if (canPlaceCard(x, y)) {
            card.setXCoord(x);
            card.setYCoord(y);
            setCard(card, x, y);
            removeCardInHand(card);
        }
    }

    public void setCard(Card card, int x, int y) {
        cards[x][y] = card;
    }

    public boolean canPlaceCard(int x, int y) {
        if (getCardAt(x, y).isEmpty()) {
            if (getCardAt(x - 1, y - 1).isPresent()) {
                if (x - 1 == 40 && y - 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) getCardAt(x - 1, y - 1).get();
                if (neighbour.getCorner(CornerLocationEnum.BOTTOM_LEFT).isVisible())
                    return true;
            }
            if (getCardAt(x + 1, y + 1).isPresent()) {
                if (x + 1 == 40 && y + 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) getCardAt(x + 1, y + 1).get();
                if (neighbour.getCorner(CornerLocationEnum.TOP_RIGHT).isVisible())
                    return true;
            }
            if (getCardAt(x - 1, y + 1).isPresent()) {
                if (x - 1 == 40 && y + 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) getCardAt(x - 1, y + 1).get();
                if (neighbour.getCorner(CornerLocationEnum.TOP_LEFT).isVisible())
                    return true;
            }
            if (getCardAt(x + 1, y - 1).isPresent()) {
                if (x - 1 == 40 && y + 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) getCardAt(x + 1, y - 1).get();
                return neighbour.getCorner(CornerLocationEnum.BOTTOM_RIGHT).isVisible();
            }
        }
        return false;
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

    public void chooseObjectiveCard() {

    }

    public void endTurn() {
        game.nextTurn();
    }
}

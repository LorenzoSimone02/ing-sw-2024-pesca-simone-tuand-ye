package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player {
    private final String nickname;
    private PlayerToken token;
    private final Game game;
    private final ResourceCard[][] cards;
    private final List<Card> cardsInHand;
    private StarterCard starterCard;
    private ObjectiveCard objectiveCard;
    private final List<Resource> resources;
    private final List<Object> objects;
    private boolean first;
    private int score;

    public Player(String nickname, Game game) {
        this.nickname = nickname;
        this.game = game;
        this.score = 0;
        this.cards = new ResourceCard[81][81];
        this.cardsInHand = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.objects = new ArrayList<>();
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

    public ResourceCard[][] getCards() {
        return cards;
    }

    public Optional<ResourceCard> getCardAt(int x, int y) {
        return Optional.ofNullable(cards[x][y]);
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
    }

    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void addObject(Object object) {
        objects.add(object);
    }

    public void removeObject(Object object) {
        objects.remove(object);
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

    public void setCard(ResourceCard card, int x, int y) {
        cards[x][y] = card;
    }
}

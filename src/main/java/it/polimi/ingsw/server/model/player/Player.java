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

/**
 * This class represents a player in the game
 */
public class Player {

    /**
     *  The player's username
     */
    private final String username;

    /**
     *  The player's token color
     */
    private PlayerToken token;

    /**
     *  The game the player is playing
     */
    private final Game game;

    /**
     *  The player's on ground cards matrix
     */
    private final ResourceCard[][] cards;

    /**
     *  The player's in hand cards
     */
    private final List<Card> cardsInHand;

    /**
     *  The player's ordered cards
     */
    private final ArrayList<ResourceCard> orderedCards;

    /**
     *  The player's starter card
     */
    private StarterCard starterCard;

    /**
     *  The player's objective card
     */
    private ObjectiveCard objectiveCard;

    /**
     *  The player's list of resources
     */
    private final List<Resource> resources;

    /**
     *  The player's list of objects
     */
    private final List<Object> objects;

    /**
     *  The player's current score
     */
    private int score;

    /**
     * Constructor for the player
     * @param nickname the player's username
     * @param game the game the player is playing
     */
    public Player(String nickname, Game game) {
        this.username = nickname;
        this.game = game;
        this.score = 0;
        this.cards = new ResourceCard[81][81];
        this.orderedCards = new ArrayList<>();
        this.cardsInHand = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.objects = new ArrayList<>();
    }

    /**
     * This method returns the player's username
     * @return the player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the player's token color
     * @return the player's token color
     */
    public PlayerToken getToken() {
        return token;
    }

    /**
     * This method sets the player's token color
     * @param token the player's token color
     */
    public void setToken(PlayerToken token) {
        this.token = token;
    }

    /**
     * This method returns the game the player is playing
     * @return the game the player is playing
     */
    public Game getGame() {
        return game;
    }

    /**
     * This method returns the player's on ground cards matrix
     * @return the player's on ground cards matrix
     */
    public ResourceCard[][] getCards() {
        return cards;
    }

    /**
     * This method returns the card at the specified position, if present in the matrix
     * @param x the x coordinate of the card
     * @param y the y coordinate of the card
     * @return the card at the specified position
     */
    public Optional<ResourceCard> getCardAt(int x, int y) {
        return Optional.ofNullable(cards[x][y]);
    }

    /**
     * This method returns the player's ordered cards
     * @return the player's ordered cards
     */
    public ArrayList<ResourceCard> getOrderedCards() {
        return orderedCards;
    }

    /**
     * This method adds a card to the player's list of ordered cards
     * @param card the card to add to the player's list of ordered cards
     */
    public void addOrderedCard(ResourceCard card) {
        orderedCards.add(card);
    }

    /**
     * This method returns the player's list of in hand cards
     * @return the player's in hand cards
     */
    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * This method adds a card to the player's list of in hand cards
     * @param card the card to add to the player's list of in hand cards
     */
    public void addCardInHand(Card card) {
        cardsInHand.add(card);
    }

    /**
     * This method removes a card from the player's list of in hand cards
     * @param card the card to remove from the player's list of in hand cards
     */
    public void removeCardInHand(Card card) {
        cardsInHand.remove(card);
    }

    /**
     * This method returns the player's starter card
     * @return the player's starter card
     */
    public StarterCard getStarterCard() {
        return starterCard;
    }

    /**
     * This method sets the player's starter card
     * @param starterCard the player's starter card
     */
    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    /**
     * This method returns the player's objective card
     * @return the player's objective card
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    /**
     * This method sets the player's objective card
     * @param objectiveCard the player's objective card
     */
    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    /**
     * This method returns the player's list of resources
     * @return the player's list of resources
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * This method adds a resource to the player's list of resources
     * @param resource the resource to add to the player's list of resources
     */
    public void addResource(Resource resource) {
        resources.add(resource);
    }

    /**
     * This method removes a resource from the player's list of resources
     * @param resource the resource to remove from the player's list of resources
     */
    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    /**
     * This method returns the player's list of objects
     * @return the player's list of objects
     */
    public List<Object> getObjects() {
        return objects;
    }

    /**
     * This method adds an object to the player's list of objects
     * @param object the object to add to the player's list of objects
     */
    public void addObject(Object object) {
        objects.add(object);
    }

    /**
     * This method removes an object from the player's list of objects
     * @param object the object to remove from the player's list of objects
     */
    public void removeObject(Object object) {
        objects.remove(object);
    }

    /**
     * This method returns the player's current score
     * @return the player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * This method sets the player's current score
     * @param score the player's current score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method sets the card at the specified position in the matrix
     * @param card the card to be set at the specified position in the matrix
     * @param x the x coordinate of the card
     * @param y the y coordinate of the card
     */
    public void setCard(ResourceCard card, int x, int y) {
        cards[x][y] = card;
    }
}

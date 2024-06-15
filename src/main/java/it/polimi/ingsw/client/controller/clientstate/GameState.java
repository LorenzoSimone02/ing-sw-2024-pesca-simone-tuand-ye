package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * The class represents the current state of the game
 */
public class GameState {

    /**
     * The active player's UUID
     */
    private final UUID uuid;

    /**
     * The active player's username
     */
    private String username;

    /**
     * The active player's score
     */
    private int score;

    /**
     * The active player's token color
     */
    private String tokenColor;

    /**
     * The game's chat messages list
     */
    private final ArrayList<String> chatMessages;

    /**
     * Thea active player's client status
     */
    private ClientStatusEnum clientStatus;

    /**
     * The game's last ping to the active client
     */
    private long lastPing;

    /**
     * The active player's list of all cards
     */
    private final ArrayList<Card> allCards;

    /**
     * The active player's placed cards on the board
     */
    private final ResourceCard[][] cardsPlaced;

    /**
     * The active player's ordered placed cards
     */
    private final LinkedList<ResourceCard> orderedCardsPlaced;

    /**
     * The active player's in hand cards
     */
    private final ArrayList<ResourceCard> cardsInHand;

    /**
     * The game's cards on the ground
     */
    private final ArrayList<Card> cardsOnGround;

    /**
     * The player's chosen starter card
     */
    private StarterCard starterCard;

    /**
     * The proposed starter card the player has to choose the face of
     */
    private StarterCard givenStarter;

    /**
     * The game's proposed objective cards
     */
    private final ArrayList<ObjectiveCard> proposedCards;

    /**
     * The player's chosen objective card
     */
    private ObjectiveCard objectiveCard;

    /**
     * The player's resources
     */
    private final HashMap<String, Integer> resources;

    /**
     * The list of the game's players states
     */
    private final ArrayList<PlayerState> playerStates;

    /**
     * The game's first player
     */
    private String firstPlayer;

    /**
     * The game's active player
     */
    private String activePlayer;

    /**
     * The list of the game's winners
     */
    private final ArrayList<String> winners;

    /**
     * The constructor of the class
     */
    public GameState() {
        this.uuid = UUID.randomUUID();
        score = 0;
        allCards = new ArrayList<>(80);
        orderedCardsPlaced = new LinkedList<>();
        cardsPlaced = new ResourceCard[81][81];
        playerStates = new ArrayList<>(3);
        cardsOnGround = new ArrayList<>(6);
        chatMessages = new ArrayList<>();
        cardsInHand = new ArrayList<>(3);
        proposedCards = new ArrayList<>(2);
        resources = new HashMap<>(7);
        clientStatus = ClientStatusEnum.DISCONNECTED;
        winners = new ArrayList<>();
        lastPing = System.currentTimeMillis();

        loadCards();
    }

    /**
     * The method returns the active player's UUID
     * @return the active player's UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * The method returns the active player's username
     * @return the active player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method sets the active player's username
     * @param username the active player's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The method returns the active player's score
     * @return the active player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * The method sets the active player's score
     * @param score the active player's score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * The method returns the active player's token color
     * @return the active player's token color
     */
    public String getTokenColor() {
        return tokenColor;
    }

    /**
     * The method sets the active player's token color
     * @param tokenColor the active player's token color
     */
    public void setTokenColor(String tokenColor) {
        this.tokenColor = tokenColor;
    }

    /**
     * The method returns the game's list of all players states
     * @return the game's list of all players states
     */
    public ArrayList<PlayerState> getPlayerStates() {
        return playerStates;
    }

    /**
     * The method adds a player state to the game's list of player states
     * @param playerState the player state to add
     */
    public void addPlayerState(PlayerState playerState) {
        playerStates.add(playerState);
    }

    /**
     * The method returns the game's active player
     * @return the game's active player
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * The method sets the game's active player
     * @param activePlayer the game's active player
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * The method returns the game's first player
     * @return the game's first player
     */
    public String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * The method sets the game's first player
     * @param firstPlayer the game's first player
     */
    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    /**
     * The method returns the game's chat messages list
     * @return the game's chat messages list
     */
    public ArrayList<String> getChatMessages() {
        return chatMessages;
    }

    /**
     * The method adds a chat message to the game's chat messages list
     * @param username the username of the message sender
     * @param message the message to add
     */
    public void addChatMessage(String username, String message) {
        chatMessages.add(Printer.CYAN + username + ": " + Printer.RESET + message);
    }

    /**
     * The method returns the active player's client status
     * @return the active player's client status
     */
    public ClientStatusEnum getClientStatus() {
        return clientStatus;
    }

    /**
     * The method sets the active player's client status
     * @param clientStatus the active player's client status
     */
    public void setClientStatus(ClientStatusEnum clientStatus) {
        this.clientStatus = clientStatus;
    }

    /**
     * The method returns the game's proposed objective cards
     * @return the game's proposed objective cards
     */
    public ArrayList<ObjectiveCard> getProposedCards() {
        return proposedCards;
    }

    /**
     * The method adds a proposed card to the game's proposed cards list
     * @param card the card to add
     */
    public void addProposedCard(ObjectiveCard card) {
        proposedCards.add(card);
    }

    /**
     * The method returns the game's last ping to the active client
     * @return the game's last ping to the active client
     */
    public long getLastPing() {
        return lastPing;
    }

    /**
     * The method sets the game's last ping to the active client
     * @param lastPing the game's last ping to the active client
     */
    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    /**
     * The method returns the game's list of winners
     * @return the game's list of winners
     */
    public ArrayList<String> getWinners() {
        return winners;
    }

    /**
     * The method adds a winner to the game's list of winners
     * @param winner the winner to add
     */
    public void addWinner(String winner) {
        winners.add(winner);
    }

    /**
     * The method returns the player's chosen starter card
     * @return the player's chosen starter card
     */
    public StarterCard getStarterCard() {
        return starterCard;
    }

    /**
     * The method sets the player's chosen starter card
     * @param starterCard the player's chosen starter card
     */
    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
        setCardPlaced(starterCard, 40, 40);
    }

    /**
     * The method sets the player's given starter card they have to choose the face of
     * @param card the player's given starter card they have to choose the face of
     */
    public void setGivenStarter(StarterCard card) {
        this.givenStarter = card;
    }

    /**
     * The method returns the player's given starter card they have to choose the face of
     * @return the player's given starter card they have to choose the face of
     */
    public StarterCard getGivenStarter() {
        return givenStarter;
    }

    /**
     * The method returns the player's chosen objective card
     * @return the player's chosen objective card
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    /**
     * The method sets the player's chosen objective card
     * @param objectiveCard the player's chosen objective card
     */
    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    /**
     * The method returns the player's resources
     * @return the player's resources
     */
    public HashMap<String, Integer> getResources() {
        return resources;
    }

    /**
     * The method sets the player's resources
     * @param resources the player's resources
     */
    public void setResources(HashMap<String, Integer> resources) {
        this.resources.clear();
        this.resources.put(ResourceTypeEnum.FUNGI.name(), 0);
        this.resources.put(ResourceTypeEnum.ANIMAL.name(), 0);
        this.resources.put(ResourceTypeEnum.INSECT.name(), 0);
        this.resources.put(ResourceTypeEnum.PLANT.name(), 0);
        this.resources.put(ObjectTypeEnum.QUILL.name(), 0);
        this.resources.put(ObjectTypeEnum.INKWELL.name(), 0);
        this.resources.put(ObjectTypeEnum.MANUSCRIPT.name(), 0);
        this.resources.putAll(resources);
    }

    /**
     * The method adds a resource to the player's resources
     * @param type the resource type to add
     */
    public void addResource(String type) {
        resources.put(type, resources.getOrDefault(type, 0) + 1);
    }

    /**
     * The method loads the active player's cards
     */
    public void loadCards() {
        try {
            for (int i = 1; i <= 40; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonData = stringBuilder.toString();
                ResourceCard card = new ResourceCard(jsonData);
                allCards.add(card);
            }

            for(int i = 1; i <= 40; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/goldcards/goldCard" + i + ".json"))));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonData = stringBuilder.toString();
                GoldCard card = new GoldCard(jsonData);
                allCards.add(card);
            }

            for(int i = 1; i <= 6; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/startercards/starterCard" + i + ".json"))));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonData = stringBuilder.toString();
                StarterCard card = new StarterCard(jsonData);
                allCards.add(card);
            }

            for(int i = 1; i <= 16; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonData = stringBuilder.toString();
                ObjectiveCard card = new ObjectiveCard(jsonData);
                allCards.add(card);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method returns the card with the given ID if it's in the active player's cards
     * @param id the card ID
     * @return the card with the given ID if it's in the active player's cards
     */
    public Card getCardById(int id) {
        for (Card card : allCards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }

    /**
     * The method returns the player's state by the given nickname if it's in the game's list of player states,
     *      otherwise it creates a new player state
     * @param nickname the player's nickname
     * @return the player's state by the given nickname if it's in the game's list of player states
     */
    public PlayerState getOrCreatePlayerStateByNick(String nickname) {
        for (PlayerState state : playerStates) {
            if (state.getUsername().equals(nickname)) {
                return state;
            }
        }
        return new PlayerState(nickname);
    }

    /**
     * The method removes the player's state by the given nickname if it's in the game's list of player states
     * @param nickname the player's nickname
     */
    public void removePlayerStateByNick(String nickname) {
        playerStates.removeIf(state -> state.getUsername().equals(nickname));
    }

    /**
     * The method returns the active player's placed cards
     * @return the active player's placed cards
     */
    public ResourceCard[][] getCardsPlaced() {
        return cardsPlaced;
    }

    /**
     * The method places the given card on the board at the given coordinates
     * @param card the card to place
     * @param x the x coordinate of the card
     * @param y the y coordinate of the card
     */
    public void setCardPlaced(ResourceCard card, int x, int y) {
        cardsPlaced[x][y] = card;
    }

    /**
     * The method returns the active player's ordered placed cards
     * @return the active player's ordered placed cards
     */
    public LinkedList<ResourceCard> getOrderedCardsPlaced() {
        return orderedCardsPlaced;
    }

    /**
     * The method adds a card to the active player's ordered placed cards
     * @param card the card to add
     */
    public void addOrderedCard(ResourceCard card) {
        orderedCardsPlaced.add(card);
    }

    /**
     * The method returns the active player's list of in hand cards
     * @return the active player's list of in hand cards
     */
    public ArrayList<ResourceCard> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * The method adds a card to the active player's in hand cards
     * @param card the card to add
     */
    public void addCardInHand(ResourceCard card) {
        cardsInHand.add(card);
    }

    /**
     * The method removes a card from the active player's in hand cards
     * @param card the card to remove
     */
    public void removeCardInHand(ResourceCard card) {
        cardsInHand.remove(card);
    }

    /**
     * The method returns the game's cards on the ground
     * @return the game's cards on the ground
     */
    public ArrayList<Card> getCardsOnGround() {
        return cardsOnGround;
    }

    /**
     * The method adds a card to the game's cards on the ground
     * @param card the card to add
     */
    public void addCardOnGround(Card card) {
        cardsOnGround.add(card);
    }

    /**
     * The method removes a card from the game's cards on the ground
     * @param card the card to remove
     */
    public void removeCardOnGround(Card card) {
        cardsOnGround.remove(card);
    }
}


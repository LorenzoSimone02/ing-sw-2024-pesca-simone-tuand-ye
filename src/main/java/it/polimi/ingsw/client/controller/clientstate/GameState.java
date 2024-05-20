package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.server.model.card.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class GameState {

    private final UUID uuid;
    private int gameID;
    private String username;
    private int score;
    private String color;
    private final ArrayList<String> chatMessages;
    private ClientStatusEnum clientStatus;
    private long lastPing;
    private final ArrayList<Card> allCards;
    private final ArrayList<Card> placedCards;
    private final ArrayList<ResourceCard> cardsInHand;
    private final ArrayList<ResourceCard> cardsOnGround;
    private StarterCard starterCard;
    private final ArrayList<ObjectiveCard> proposedCards;
    private ObjectiveCard objectiveCard;
    //TODO: Resources

    private final ArrayList<PlayerState> playerStates;
    private String firstPlayer;
    private String activePlayer;
    private String winner;

    public GameState() {
        this.uuid = UUID.randomUUID();
        score = 0;
        allCards = new ArrayList<>(80);
        placedCards = new ArrayList<>();
        playerStates = new ArrayList<>(3);
        cardsOnGround = new ArrayList<>(6);
        chatMessages = new ArrayList<>();
        cardsInHand = new ArrayList<>(3);
        proposedCards = new ArrayList<>(2);
        clientStatus = ClientStatusEnum.DISCONNECTED;
        lastPing = System.currentTimeMillis();
        loadCards();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ArrayList<PlayerState> getPlayerStates() {
        return playerStates;
    }

    public void addPlayerState(PlayerState playerState) {
        playerStates.add(playerState);
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public ArrayList<String> getChatMessages() {
        return chatMessages;
    }

    public void addChatMessage(String username, String message) {
        chatMessages.add(Printer.CYAN + username + ": " + Printer.RESET + message);
    }

    public ClientStatusEnum getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatusEnum clientStatus) {
        this.clientStatus = clientStatus;
    }

    public ArrayList<ObjectiveCard> getProposedCards() {
        return proposedCards;
    }

    public void addProposedCard(ObjectiveCard card) {
        proposedCards.add(card);
    }

    public long getLastPing() {
        return lastPing;
    }

    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
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

    public void loadCards(){

        File folder = Paths.get("src/main/resources/assets/resourcecards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ResourceCard card = new ResourceCard(file);
            allCards.add(card);
        }

        folder = Paths.get("src/main/resources/assets/goldcards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            GoldCard card = new GoldCard(file);
            allCards.add(card);
        }

        folder = Paths.get("src/main/resources/assets/startercards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            StarterCard card = new StarterCard(file);
            allCards.add(card);
        }

        folder = Paths.get("src/main/resources/assets/objectivecards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ObjectiveCard card = new ObjectiveCard(file);
            allCards.add(card);
        }
    }

    public Card getCardById(int id) {
        for (Card card : allCards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }

    public ArrayList<Card> getPlacedCards() {
        return placedCards;
    }

    public ArrayList<ResourceCard> getCardsInHand() {
        return cardsInHand;
    }

    public void addCardInHand(ResourceCard card) {
        cardsInHand.add(card);
    }

    public void removeCardInHand(ResourceCard card) {
        cardsInHand.remove(card);
    }

    public ArrayList<ResourceCard> getCardsOnGround() {
        return cardsOnGround;
    }

    public void addCardOnGround(ResourceCard card) {
        cardsOnGround.add(card);
    }
}


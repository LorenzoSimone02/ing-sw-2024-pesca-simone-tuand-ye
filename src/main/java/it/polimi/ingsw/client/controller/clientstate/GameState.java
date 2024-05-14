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
    private ArrayList<Card> gameCards;
    private ArrayList<Integer> cardsInHand;
    private StarterCard starterCard;
    private ObjectiveCard objectiveCard;
    //TODO: private ArrayList<Integer> cardsOnTable;
    //TODO: Resources

    private final ArrayList<PlayerState> playerStates;
    private String firstPlayer;
    private String activePlayer;
    private String winner;

    public GameState() {
        this.uuid = UUID.randomUUID();
        score = 0;
        playerStates = new ArrayList<>();
        chatMessages = new ArrayList<>();
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
        chatMessages.add(Printer.ANSI_CYAN + username + ": " + Printer.ANSI_RESET + message);
    }

    public ClientStatusEnum getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatusEnum clientStatus) {
        this.clientStatus = clientStatus;
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
        gameCards = new ArrayList<>();

        File folder = Paths.get("src/main/resources/assets/resourcecards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ResourceCard card = new ResourceCard(file);
            gameCards.add(card);
        }

        folder = Paths.get("src/main/resources/assets/goldcards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            GoldCard card = new GoldCard(file);
            gameCards.add(card);
        }

        folder = Paths.get("src/main/resources/assets/startercards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            StarterCard card = new StarterCard(file);
            gameCards.add(card);
        }

        folder = Paths.get("src/main/resources/assets/objectivecards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ObjectiveCard card = new ObjectiveCard(file);
            gameCards.add(card);
        }
    }

    public Card getCardById(int id) {
        for (Card card : gameCards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }

    public ArrayList<Card> getGameCards() {
        return gameCards;
    }
}

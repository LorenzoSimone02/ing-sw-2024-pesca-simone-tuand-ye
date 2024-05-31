package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final ResourceCard[][] cardsPlaced;
    private final ArrayList<ResourceCard> cardsInHand;
    private final ArrayList<Card> cardsOnGround;
    private StarterCard starterCard;
    private final ArrayList<ObjectiveCard> proposedCards;
    private StarterCard givenStarter;
    private ObjectiveCard objectiveCard;
    private final HashMap<String, Integer> resources;

    private final ArrayList<PlayerState> playerStates;
    private String firstPlayer;
    private String activePlayer;
    private ArrayList<String> winners;

    public GameState() {
        this.uuid = UUID.randomUUID();
        score = 0;
        allCards = new ArrayList<>(80);
        cardsPlaced = new ResourceCard[81][81];
        playerStates = new ArrayList<>(3);
        cardsOnGround = new ArrayList<>(6);
        chatMessages = new ArrayList<>();
        cardsInHand = new ArrayList<>(3);
        proposedCards = new ArrayList<>(2);
        resources = new HashMap<>(7);
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

    public ArrayList<String> getWinners() {
        return winners;
    }

    public void addWinner(String winner) {
        winners.add(winner);
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
        setCardPlaced(starterCard, 40, 40);
    }


    public void setGivenStarter(StarterCard card) {
        this.givenStarter = card;
    }

    public StarterCard getGivenStarter() {
        return givenStarter;
    }

    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    public HashMap<String, Integer> getResources() {
        return resources;
    }

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

    public void addResource(String type) {
        resources.put(type, resources.getOrDefault(type, 0) + 1);
    }

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

    public Card getCardById(int id) {
        for (Card card : allCards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }

    public PlayerState getPlayerStateByNick(String nickname) {
        for (PlayerState state : playerStates) {
            if (state.getUsername().equals(nickname)) {
                return state;
            }
        }
        return null;
    }

    public ResourceCard[][] getCardsPlaced() {
        return cardsPlaced;
    }

    public void setCardPlaced(ResourceCard card, int x, int y) {
        cardsPlaced[x][y] = card;
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

    public ArrayList<Card> getCardsOnGround() {
        return cardsOnGround;
    }

    public void addCardOnGround(Card card) {
        cardsOnGround.add(card);
    }

    public void removeCardOnGround(Card card) {
        cardsOnGround.remove(card);
    }
}


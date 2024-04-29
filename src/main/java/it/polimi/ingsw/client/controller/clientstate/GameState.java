package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.client.controller.Printer;

import java.util.ArrayList;
import java.util.UUID;

public class GameState {

    private int gameID;
    private final UUID uuid;
    private String username;
    private int score;
    private String color;
    private final ArrayList<String> chatMessages;
    private ClientStatusEnum clientStatus;
    private long lastPing;

    private final ArrayList<PlayerState> playerStates;
    private String firstPlayer;
    private String activePlayer;
    private String winner;


    public GameState() {
        uuid = UUID.randomUUID();
        score = 0;
        playerStates = new ArrayList<>();
        chatMessages = new ArrayList<>();
        clientStatus = ClientStatusEnum.DISCONNECTED;
        lastPing = System.currentTimeMillis();
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
}

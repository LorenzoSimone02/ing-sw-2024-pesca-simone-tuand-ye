package it.polimi.ingsw.client.controller.gamestate;

import it.polimi.ingsw.client.controller.Printer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

public class GameState {

    private int gameID;
    private final UUID uuid;
    private String username;
    private String winner;
    private final LinkedHashMap<String, Integer> playerScores;
    private String activePlayer;
    private final ArrayList<String> chatMessages;
    private ClientStatusEnum clientStatus;
    private long lastPing;

    public GameState() {
        uuid = UUID.randomUUID();
        playerScores = new LinkedHashMap<>();
        chatMessages = new ArrayList<>();
        clientStatus = ClientStatusEnum.DISCONNECTED;
        this.lastPing = System.currentTimeMillis();
    }

    public int getGameID() {
        return gameID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LinkedHashMap<String, Integer> getPlayerScores() {
        return playerScores;
    }

    public void addPlayerScore(String username, int score) {
        playerScores.put(username, score);
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
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

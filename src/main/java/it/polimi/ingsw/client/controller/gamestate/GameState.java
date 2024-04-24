package it.polimi.ingsw.client.controller.gamestate;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.view.UserInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GameState {

    private int gameID;
    private String username;
    private UserInterface userInterface;
    private String winner;
    private final LinkedHashMap<String, Integer> playerScores;
    private String activePlayer;
    private final ArrayList<String> chatMessages;
    private ClientStatusEnum clientStatus;

    public GameState() {
        playerScores = new LinkedHashMap<>();
        chatMessages = new ArrayList<>();
        clientStatus = ClientStatusEnum.DISCONNECTED;
    }

    public int getGameID() {
        return gameID;
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
}

package it.polimi.ingsw.client.controller.gamestate;

import it.polimi.ingsw.client.controller.ClientStatusEnum;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.view.UserInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GameState {

    private String username;
    private UserInterface userInterface;
    private String winner;
    private final LinkedHashMap<String, Integer> playerScores;
    private String activePlayer;
    private List<String> orderedPlayersNames;
    private final ArrayList<String> chatMessages;
    private String serverErrorMessage;
    private ClientStatusEnum clientStatus;

    public GameState() {
        playerScores = new LinkedHashMap<>();
        chatMessages = new ArrayList<>();
        clientStatus = ClientStatusEnum.LOGIN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

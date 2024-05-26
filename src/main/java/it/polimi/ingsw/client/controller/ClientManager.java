package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.controller.clientstate.GameState;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.client.view.tui.TUIClient;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class ClientManager {

    private static ClientManager instance;
    private ClientNetworkHandler networkHandler;
    private final ViewModeEnum viewMode;
    private final UserInterface userInterface;
    private final GameState gameState;
    private final String serverIP;

    public ClientManager(ClientNetworkHandler networkHandler, ViewModeEnum viewMode, String serverIP) {
        if (instance != null)
            throw new IllegalStateException("ClientManager already instantiated");
        instance = this;
        this.networkHandler = networkHandler;
        this.networkHandler.setClientManager(this);
        this.serverIP = serverIP;
        this.viewMode = viewMode;
        this.userInterface = viewMode == ViewModeEnum.TUI ? new TUIClient(this) : new GUIClient();
        this.gameState = new GameState();
    }

    public void runUI() {
        userInterface.runView();
    }

    public ClientNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public void setNetworkHandler(ClientNetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public ViewModeEnum getViewMode() {
        return viewMode;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getServerIP() {
        return serverIP;
    }

    public static ClientManager getInstance() {
        return instance;
    }
}

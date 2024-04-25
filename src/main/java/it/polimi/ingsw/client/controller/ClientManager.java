package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.controller.gamestate.GameState;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.tui.TUIClient;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class ClientManager {

    private ClientNetworkHandler networkHandler;
    private final ViewModeEnum viewMode;
    private final UserInterface userInterface;
    private final GameState gameState;

    public ClientManager(ClientNetworkHandler networkHandler, ViewModeEnum viewMode) {
        this.networkHandler = networkHandler;
        this.networkHandler.setClientManager(this);
        this.viewMode = viewMode;
        userInterface = viewMode == ViewModeEnum.TUI ? new TUIClient(this) : new GUIClient(this);
        gameState = new GameState();
    }

    public void runUI(){
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


}

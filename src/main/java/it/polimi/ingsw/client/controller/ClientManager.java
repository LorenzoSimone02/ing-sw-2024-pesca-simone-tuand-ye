package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.controller.clientstate.GameState;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.client.view.tui.TUIClient;
import it.polimi.ingsw.network.ClientNetworkHandler;

/**
 * The class of the client manager
 */
public class ClientManager {

    /**
     * The instance of the client manager
     */
    private static ClientManager instance;

    /**
     * The client-side network handler
     */
    private ClientNetworkHandler networkHandler;

    /**
     * The client's view mode
     */
    private final ViewModeEnum viewMode;

    /**
     * The client's user interface
     */
    private final UserInterface userInterface;

    /**
     * The client's game state
     */
    private final GameState gameState;

    /**
     * The server IP
     */
    private final String serverIP;

    /**
     * The constructor of the client manager
     * @param networkHandler the client-side network handler
     * @param viewMode the client's view mode
     * @param serverIP the server IP
     */
    public ClientManager(ClientNetworkHandler networkHandler, ViewModeEnum viewMode, String serverIP) {
        if (instance != null)
            throw new IllegalStateException("ClientManager already instantiated");
        instance = this;
        this.networkHandler = networkHandler;
        this.networkHandler.setClientManager(this);
        this.serverIP = serverIP;
        this.viewMode = viewMode;
        this.userInterface = viewMode == ViewModeEnum.TUI ? new TUIClient(this) : new GUIClient(this);
        this.gameState = new GameState();
    }

    /**
     * The method that runs the user interface
     */
    public void runUI() {
        userInterface.runView();
    }

    /**
     * The method that returns the client-side network handler
     * @return the client-side network handler
     */
    public ClientNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    /**
     * The method that sets the client-side network handler
     * @param networkHandler the client-side network handler
     */
    public void setNetworkHandler(ClientNetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    /**
     * The method that returns the view mode
     * @return the view mode
     */
    public ViewModeEnum getViewMode() {
        return viewMode;
    }

    /**
     * The method that returns the user interface
     * @return the user interface
     */
    public UserInterface getUserInterface() {
        return userInterface;
    }

    /**
     * The method that returns the game state
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * The method that returns the server IP
     * @return the server IP
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * The method that returns the instance of the client manager
     * @return the instance of the client manager
     */
    public static ClientManager getInstance() {
        return instance;
    }
}

package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.cli.CLIClient;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class ClientManager {

    private final ClientNetworkHandler networkHandler;
    private final ViewModeEnum viewMode;
    private final UserInterface userInterface;

    public ClientManager(ClientNetworkHandler networkHandler, ViewModeEnum viewMode) {
        this.networkHandler = networkHandler;
        this.networkHandler.setClientManager(this);
        this.viewMode = viewMode;
        userInterface = viewMode == ViewModeEnum.CLI ? new CLIClient(this) : new GUIClient(this);
    }

    public void runUI(){
        userInterface.runView();
    }

    public ClientNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public ViewModeEnum getViewMode() {
        return viewMode;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

}

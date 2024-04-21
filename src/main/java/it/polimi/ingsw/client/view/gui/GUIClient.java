package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.UserInterface;

public class GUIClient implements UserInterface {

    private final ClientManager clientManager;

    public GUIClient(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void run() {

    }

    public ClientManager getClientManager() {
        return clientManager;
    }
}

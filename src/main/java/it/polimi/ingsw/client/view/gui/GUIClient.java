package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.UserInterface;

public record GUIClient(ClientManager clientManager) implements UserInterface {

    @Override
    public void runView() {

    }

    @Override
    public void showMessage(String message) {

    }
}

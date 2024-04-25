package it.polimi.ingsw.client.view.tui;

import it.polimi.ingsw.client.controller.commands.CommandReader;
import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.UserInterface;

public class TUIClient implements UserInterface {

    private final ClientManager clientManager;

    public TUIClient(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void runView() {
        CommandReader commandReader = new CommandReader(clientManager);
        commandReader.run();
    }

    public ClientManager getClientManager() {
        return clientManager;
    }
}

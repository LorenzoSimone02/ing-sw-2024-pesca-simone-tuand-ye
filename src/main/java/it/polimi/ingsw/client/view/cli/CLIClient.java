package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.commands.CommandReader;
import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.UserInterface;

public class CLIClient implements UserInterface {

    private final ClientManager clientManager;

    public CLIClient(ClientManager clientManager) {
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

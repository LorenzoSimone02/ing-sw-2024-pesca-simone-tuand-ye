package it.polimi.ingsw.client.view.tui;

import it.polimi.ingsw.client.controller.commands.CommandReader;
import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.UserInterface;

public record TUIClient(ClientManager clientManager) implements UserInterface {

    @Override
    public void runView() {
        CommandReader commandReader = new CommandReader(clientManager);
        commandReader.run();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}

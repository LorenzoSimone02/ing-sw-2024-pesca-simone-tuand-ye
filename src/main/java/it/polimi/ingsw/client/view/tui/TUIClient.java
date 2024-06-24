package it.polimi.ingsw.client.view.tui;

import it.polimi.ingsw.client.controller.commands.CommandReader;
import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.UserInterface;

/**
 * Text User Interface client view
 */
public class TUIClient implements UserInterface {

    /**
     * The method runs the TUI client view
     */
    @Override
    public void runView() {
        CommandReader commandReader = new CommandReader(ClientManager.getInstance());
        commandReader.run();
    }

    /**
     * The method shows a message to the user
     * @param message the message to show
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}

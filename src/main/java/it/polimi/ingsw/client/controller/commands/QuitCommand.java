package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

/**
 * The class represents the command that allows a player to quit from the current game
 */
public class QuitCommand extends Command {

    /**
     * The constructor of the class
     */
    public QuitCommand() {
        commandName = "/quit";
        description = "  Quits from the current Server \n  Usage: /quit";

        addValidStatus(ClientStatusEnum.LOBBY);
        addValidStatus(ClientStatusEnum.LOGGED);
        addValidStatus(ClientStatusEnum.CONNECTED);
        addValidStatus(ClientStatusEnum.CHOOSING_OBJECTIVE);
        addValidStatus(ClientStatusEnum.CHOOSING_COLOR);
        addValidStatus(ClientStatusEnum.CHOOSING_STARTER_FACE);
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
        addValidStatus(ClientStatusEnum.ENDED);
    }

    /**
     * The method executes the command that allows a player to quit from the current game
     * @param input the input of the command
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.exit(0);
            } else {
                System.err.println("Usage: /quit");
            }
        } else {
            System.err.println("You aren't in a Server.");
        }
    }

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}


package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

public class QuitCommand extends Command {

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

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}


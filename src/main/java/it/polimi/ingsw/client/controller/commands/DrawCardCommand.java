package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

public class DrawCardCommand extends Command {

    public DrawCardCommand() {
        commandName = "/drawCard";
        description = "  Draw a Card on the ground or from a Deck\n  Usage: /drawCard TODO";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {

            } else {
                System.err.println("Usage: /drawCard TODO");
            }
        } else {
            System.err.println("You can't draw a Card now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

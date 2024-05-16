package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

public class ViewCardCommand extends Command {

    public ViewCardCommand() {
        commandName = "/viewCard";
        description = "  View the details of a specific Card\n  Usage: /viewCard TODO";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String[] split = input.split(" ");
            if (split.length == 1) {

            } else {
                System.err.println("Usage: /viewCard TODO");
            }
        } else {
            System.err.println("You can't view a Card now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

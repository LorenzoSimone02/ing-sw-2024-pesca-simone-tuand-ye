package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

public class PlaceCardCommand extends Command {

    public PlaceCardCommand() {
        commandName = "/placeCard";
        description = "  Place a Card \n  Usage: /placeCard TODO";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {

            } else {
                System.err.println("Usage: /placeCard TODO");
            }
        } else {
            System.err.println("You can't place a Card now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

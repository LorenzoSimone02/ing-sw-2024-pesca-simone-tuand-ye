package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;

public class ResourcesCommand extends Command {

    public ResourcesCommand() {
        commandName = "/resources";
        description = "  Shows your Resources or the ones of another Player" +
                " \n  Usage: /resources <player>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.out.println(Printer.GREEN + "Your Resources:" + Printer.RESET);
            } else {
                String arg = input.split(" ")[0];
                for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
                    if (arg.equalsIgnoreCase(playerState.getUsername())) {
                        System.out.println(Printer.GREEN + "Resources of " + input.split(" ")[0] + ":" + Printer.RESET);

                        return;
                    }
                    System.err.println("Usage: /resources <player>");
                }
            }

        } else {
            System.err.println("You can't view the Resources now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

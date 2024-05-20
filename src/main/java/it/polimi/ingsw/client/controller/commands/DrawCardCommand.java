package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

public class DrawCardCommand extends Command {

    public DrawCardCommand() {
        commandName = "/drawCard";
        description = "  Draw a Card on the ground or from a Deck\n  Usage: /drawCard <1/2/3/4/resources/gold>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String[] split = input.split(" ");
            if (split.length == 1) {
                String arg = split[0];
                if (arg.equalsIgnoreCase("1")) {

                } else if (arg.equalsIgnoreCase("2")) {

                } else if (arg.equalsIgnoreCase("3")) {

                } else if (arg.equalsIgnoreCase("4")) {

                } else if (arg.equalsIgnoreCase("gold")) {

                } else if (arg.equalsIgnoreCase("resources")) {
                } else {
                    System.err.println("Usage: /drawCard <1/2/3/4/resources/gold>");
                }
            } else {
                System.err.println("Usage: /drawCard <1/2/3/4/resources/gold>");
            }
        } else {
            System.err.println("You can't draw a Card now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

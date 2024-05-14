package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChooseObjectivePacket;

public class ChooseObjectiveCommand extends Command {

    public ChooseObjectiveCommand() {
        commandName = "/chooseObjective";
        description = "  Chooses a personal Objective Card \n  Usage: /chooseObjective <1 or 2>";
        addValidStatus(ClientStatusEnum.CHOOSING_OBJECTIVE);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String option = input.split(" ")[0];
                if (option.equals("1") || option.equals("2")) {
                    clientManager.getNetworkHandler().sendPacket(new ChooseObjectivePacket(Integer.parseInt(option)));
                } else {
                    System.err.println("Invalid objective. Type 1 for the first ObjectiveCard or 2 for the second.");
                }
            } else {
                System.err.println("Usage: /chooseObjective <1 or 2>");
            }
        } else {
            System.err.println("You can't choose an Objective Card now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

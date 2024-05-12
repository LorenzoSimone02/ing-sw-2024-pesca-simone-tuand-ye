package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

public class ChooseObjectiveCommand extends Command {

    public ChooseObjectiveCommand() {
        commandName = "/chooseObjective";
        description = "  Chooses a personal Objective Card \n  Usage: /chooseObjective <option>";
        addValidStatus(ClientStatusEnum.CHOOSING_OBJECTIVE);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
             
            } else {
                System.err.println("Usage: /chooseObjective <option>");
            }
        } else {
            System.err.println("You can't choose an Objective Card now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

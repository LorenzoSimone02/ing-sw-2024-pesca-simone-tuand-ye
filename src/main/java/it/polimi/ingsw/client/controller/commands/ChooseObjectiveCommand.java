package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChooseObjectivePacket;

/**
 * The class represents the command that allows a player to choose a personal Objective Card
 */
public class ChooseObjectiveCommand extends Command {

    /**
     * The constructor of the class
     */
    public ChooseObjectiveCommand() {
        commandName = "/chooseObjective";
        description = "  Chooses a personal Objective Card \n  Usage: /chooseObjective <1 or 2>";
        addValidStatus(ClientStatusEnum.CHOOSING_OBJECTIVE);
    }

    /**
     * The method executes the command that allows a player to choose a personal Objective Card
     * @param input the Objective Card to choose
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String option = input.split(" ")[0];
                if (option.equals("1")) {
                    clientManager.getNetworkHandler().sendPacket(new ChooseObjectivePacket(clientManager.getGameState().getProposedCards().getFirst().getId()));
                } else if (option.equals("2")) {
                    clientManager.getNetworkHandler().sendPacket(new ChooseObjectivePacket(clientManager.getGameState().getProposedCards().get(1).getId()));
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

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

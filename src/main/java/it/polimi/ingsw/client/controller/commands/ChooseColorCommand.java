package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChooseColorPacket;

/**
 * The class represents the command that allows a player to choose a token color
 */
public class ChooseColorCommand extends Command {

    /**
     * The constructor of the class
     */
    public ChooseColorCommand() {
        commandName = "/chooseColor";
        description = "  Chooses a Token Color \n  Usage: /chooseColor <color>";
        addValidStatus(ClientStatusEnum.CHOOSING_COLOR);
    }

    /**
     * The method executes the command that allows a player to choose a token color
     * @param input the color to choose
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String color = input.split(" ")[0];
                if (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("yellow") || color.equalsIgnoreCase("green") || color.equalsIgnoreCase("blue")) {
                    clientManager.getNetworkHandler().sendPacket(new ChooseColorPacket(clientManager.getGameState().getUsername(), color.toUpperCase()));
                } else {
                    System.err.println("Invalid color. Choose between red, yellow, green, blue.");
                }
            } else {
                System.err.println("Usage: /chooseColor <color>");
            }
        } else {
            System.err.println("You can't choose your color now.");
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

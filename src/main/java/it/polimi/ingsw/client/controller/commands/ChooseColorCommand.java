package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChooseColorPacket;

public class ChooseColorCommand extends Command {

    public ChooseColorCommand() {
        commandName = "/chooseColor";
        description = "  Chooses a Token Color \n  Usage: /chooseColor <color>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String color = input.split(" ")[0];
                if (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("yellow") || color.equalsIgnoreCase("green") || color.equalsIgnoreCase("blue")) {
                    clientManager.getNetworkHandler().sendPacket(new ChooseColorPacket(clientManager.getGameState().getUsername(), color.toUpperCase()));
                }
            } else {
                System.err.println("Usage: /chooseColor <color>");
            }
        } else {
            System.err.println("You can't choose your color now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

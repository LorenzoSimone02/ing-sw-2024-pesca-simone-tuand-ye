package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.JoinPacket;

public class JoinCommand extends Command {

    public JoinCommand() {
        commandName = "/join";
        description = "  Joins into an existing Game \n  Usage: /join";
        addValidStatus(ClientStatusEnum.LOGGED);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                clientManager.getNetworkHandler().sendPacket(new JoinPacket());
            } else {
                System.err.println("Usage: /join");
            }
        } else {
            System.err.println("You can't join a Game now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

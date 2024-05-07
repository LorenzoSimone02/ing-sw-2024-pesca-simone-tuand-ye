package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.EndTurnPacket;

public class EndTurnCommand extends Command {

    public EndTurnCommand() {
        commandName = "/endTurn";
        description = "  Terminates your turn \n  Usage: /endTurn";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager) && clientManager.getGameState().getActivePlayer().equalsIgnoreCase(clientManager.getGameState().getUsername())) {
            if (input.trim().isEmpty()) {
                clientManager.getNetworkHandler().sendPacket(new EndTurnPacket(clientManager.getGameState().getUsername()));
            } else {
                System.err.println("Usage: /endTurn");
            }
        } else {
            System.err.println("You can't end your turn now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

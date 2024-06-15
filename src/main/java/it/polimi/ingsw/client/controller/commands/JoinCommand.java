package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.JoinPacket;

/**
 * The class represents the command that allows a player to join an existing Game
 */
public class JoinCommand extends Command {

    public JoinCommand() {
        commandName = "/join";
        description = "  Joins into an existing Game \n  Usage: /join";
        addValidStatus(ClientStatusEnum.LOGGED);
    }

    /**
     * The method executes the command that allows a player to join an existing Game
     * @param input the input of the command
     * @param clientManager the client manager
     */
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

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.CreateGamePacket;

/**
 * The class represents the command that creates a game
 */
public class CreateGameCommand extends Command {

    /**
     * The constructor of the class
     */
    public CreateGameCommand() {
        commandName = "/createGame";
        description = "  Creates a game with a given number of Players \n  Usage: /createGame <playersNumber>";
        addValidStatus(ClientStatusEnum.LOGGED);
    }

    /**
     * The method executes the command that creates a game
     * @param input the number of players
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String number = input.split(" ")[0];
            try {
                int playersNumber = Integer.parseInt(number);
                if (playersNumber < 2 || playersNumber > 4) {
                    System.err.println("Players number must be between 2 and 4.");
                } else {
                    clientManager.getNetworkHandler().sendPacket(new CreateGamePacket(playersNumber));
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Usage: /createGame <playersNumber>");
            }
        } else {
            System.err.println("You can't create a Game now.");
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


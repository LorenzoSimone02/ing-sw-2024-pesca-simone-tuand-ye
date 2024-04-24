package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.CreateGamePacket;

public class CreateGameCommand extends Command {

    public CreateGameCommand() {
        commandName = "/createGame";
        description = "  Creates a game with a given number of Players \n  Usage: /createGame <playersNumber>";
        addValidStatus(ClientStatusEnum.LOBBY);
    }


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

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}


package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientStatusEnum;
import it.polimi.ingsw.network.packets.PlayersNumberPacket;

public class PlayersNumberCommand extends Command {

    public PlayersNumberCommand() {
        commandName = "/playersNumber";
        description = "  Sets the maximum players number for a game \n  Usage: /playersNumber <number>";
        addValidStatus(ClientStatusEnum.LOBBY);
    }


    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable()) {
            String number = input.split(" ")[0];
            try{
                int playersNumber = Integer.parseInt(number);
                if (playersNumber < 2 || playersNumber > 4) {
                    System.err.println("Players number must be between 2 and 4.");
                } else {
                    clientManager.getNetworkHandler().sendPacket(new PlayersNumberPacket(playersNumber));
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format.");
            }
        } else {
            System.err.println("You can't set the players number now.");
        }
    }

    public boolean isExecutable() {
        return true;
    }
}


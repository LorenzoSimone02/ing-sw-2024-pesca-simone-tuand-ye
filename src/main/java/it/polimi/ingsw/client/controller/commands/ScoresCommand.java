package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;

public class ScoresCommand extends Command {

    public ScoresCommand() {
        commandName = "/scores";
        description = "  Shows the scores of every Player in the Game \n  Usage: /scores";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.ENDING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.out.println("Players Scores:");
                System.out.println(Printer.ANSI_PURPLE + clientManager.getGameState().getUsername() + ": " + Printer.ANSI_RESET + clientManager.getGameState().getScore() + " points");
                for (PlayerState player : clientManager.getGameState().getPlayerStates()) {
                    System.out.println(Printer.ANSI_PURPLE + player.getUsername() + ": " + Printer.ANSI_RESET + player.getScore() + " points");
                }
            } else {
                System.err.println("Usage: /scores");
            }
        } else {
            System.err.println("The game is not in progress. " + clientManager.getGameState().getClientStatus());
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

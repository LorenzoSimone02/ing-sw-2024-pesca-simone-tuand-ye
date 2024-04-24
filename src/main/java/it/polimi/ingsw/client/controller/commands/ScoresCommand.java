package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.Printer;

public class ScoresCommand extends Command {

    public ScoresCommand() {
        commandName = "/scores";
        description = "  Shows the scores of every Player in the Game \n  Usage: /scores";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.out.println("Players Scores:");
                for (String player : clientManager.getGameState().getPlayerScores().keySet()) {
                    System.out.println(Printer.ANSI_PURPLE + player + ": " + Printer.ANSI_RESET + clientManager.getGameState().getPlayerScores().get(player) + " points");
                }
            } else {
                System.err.println("Usage: /scores");
            }
        } else {
            System.err.println("The game is not in progress.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

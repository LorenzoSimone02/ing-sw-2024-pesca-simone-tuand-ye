package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;

/**
 * The class represents the command that shows the scores of every Player in the Game
 */
public class ScoresCommand extends Command {

    /**
     * The constructor of the class
     */
    public ScoresCommand() {
        commandName = "/scores";
        description = "  Shows the scores of every Player in the Game \n  Usage: /scores";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
        addValidStatus(ClientStatusEnum.ENDED);
    }

    /**
     * The method executes the command that shows the scores of every Player in the Game
     * @param input the input of the command
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.out.println("Players Scores:");
                System.out.println(Printer.getColorByToken(clientManager.getGameState().getTokenColor()) + clientManager.getGameState().getUsername() + ": " + Printer.RESET + clientManager.getGameState().getScore() + " points");
                for (PlayerState player : clientManager.getGameState().getPlayerStates()) {
                    System.out.println(Printer.getColorByToken(player.getTokenColor()) + player.getUsername() + ": " + Printer.RESET + player.getScore() + " points");
                }
            } else {
                System.err.println("Usage: /scores");
            }
        } else {
            System.err.println("The game is not in progress.");
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

package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.StarterCard;

/**
 * The class represents the command that allows a player to view the details of a specific Card given a position in the matrix
 */
public class ViewCardCommand extends Command {

    /**
     * The constructor of the class
     */
    public ViewCardCommand() {
        commandName = "/viewCard";
        description = "  View the details of a specific Card\n  Usage: /viewCard <x> <y> <player>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    /**
     * The method executes the command that allows a player to view the details of a specific Card given a position in the matrix
     * @param input the input of the command
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String[] split = input.split(" ");
            if (split.length == 2) {
                try {
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);
                    if (clientManager.getGameState().getCardsPlaced()[x][y] == null) {
                        System.err.println("No card at position " + x + " " + y);
                        return;
                    }
                    System.out.println(Printer.GREEN + "Card at position " + x + " " + y + ":" + Printer.RESET);
                    if (x == 40 && y == 40) {
                        Printer.printCard(clientManager.getGameState().getStarterCard());
                    } else {
                        Printer.printCard((Card)clientManager.getGameState().getCardsPlaced()[x][y]);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Usage: /viewCard <x> <y> <player>");
                }
            } else if (split.length == 3) {
                for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
                    if (split[2].equalsIgnoreCase(playerState.getUsername())) {
                        try {
                            int x = Integer.parseInt(split[0]);
                            int y = Integer.parseInt(split[1]);
                            if (playerState.getCardsPlaced()[x][y] == null) {
                                System.err.println("No card at position " + x + " " + y + " of " + playerState.getUsername());
                                return;
                            }
                            System.out.println(Printer.GREEN + "Card at position " + x + " " + y + " of " + playerState.getUsername() + ":" + Printer.RESET);
                            if (x == 40 && y == 40) {
                                Printer.printCard((StarterCard) playerState.getCardsPlaced()[x][y]);
                            } else {
                                Printer.printCard(playerState.getCardsPlaced()[x][y]);
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Usage: /viewCard <x> <y> <player>");
                        }
                        return;
                    }
                }
                System.err.println("Usage: /viewCard <x> <y> <player>");
            } else {
                System.err.println("Usage: /viewCard <x> <y> <player>");
            }
        } else {
            System.err.println("You can't view a Card now.");
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

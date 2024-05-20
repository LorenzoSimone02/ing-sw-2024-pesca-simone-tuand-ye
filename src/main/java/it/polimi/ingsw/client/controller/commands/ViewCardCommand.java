package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;

public class ViewCardCommand extends Command {

    public ViewCardCommand() {
        commandName = "/viewCard";
        description = "  View the details of a specific Card\n  Usage: /viewCard <x> <y> <player>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String[] split = input.split(" ");
            if (split.length == 2) {
                try {
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);
                    System.out.println(Printer.GREEN + "Card at position " + x + " " + y + ":" + Printer.RESET);
                    Printer.printCard(clientManager.getGameState().getCardsPlaced()[x][y]);
                } catch (NumberFormatException e) {
                    System.err.println("Usage: /viewCard <x> <y> <player>");
                }
            } else if (split.length == 3) {
                for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
                    if (split[2].equalsIgnoreCase(playerState.getUsername())) {
                        try {
                            int x = Integer.parseInt(split[0]);
                            int y = Integer.parseInt(split[1]);
                            System.out.println(Printer.GREEN + "Card at position " + x + " " + y + " of " + playerState.getUsername() + ":" + Printer.RESET);
                            Printer.printCard(playerState.getCardsPlaced()[x][y]);
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

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

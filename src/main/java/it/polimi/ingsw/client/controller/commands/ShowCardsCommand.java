package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;

public class ShowCardsCommand extends Command {

    public ShowCardsCommand() {
        commandName = "/showCards";
        description = "  Shows the cards in your hand, your placed cards, your objective, the placed cards of another Player or " +
                " \n  Usage: /showCards <hand/placed/ground/objective/player>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String arg = input.split(" ")[0];
            if (arg.equalsIgnoreCase("hand")) {
                System.out.println(Printer.GREEN + "Cards in hand:" + Printer.RESET);
                for (Card card : clientManager.getGameState().getCardsInHand()) {
                    Printer.printCard(card);
                }
            } else if (arg.equalsIgnoreCase("placed")) {
                System.out.println(Printer.GREEN + "Placed cards:" + Printer.RESET);
                Printer.printCardsPlaced(clientManager.getGameState().getCardsPlaced());
            } else if (arg.equalsIgnoreCase("ground")) {
                System.out.println(Printer.GREEN + "Cards on ground:" + Printer.RESET);
                for (Card card : clientManager.getGameState().getCardsOnGround()) {
                    if (!(card instanceof ObjectiveCard)) Printer.printCard(card);
                }
                for (Card card : clientManager.getGameState().getCardsOnGround()) {
                    if (card instanceof ObjectiveCard) Printer.printCard(card);
                }

            } else if (arg.equalsIgnoreCase("objective")) {
                System.out.println(Printer.GREEN + "Your personal Objective Card:" + Printer.RESET);
                Printer.printCard(clientManager.getGameState().getObjectiveCard());
            } else {
                for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
                    if (arg.equalsIgnoreCase(playerState.getUsername())) {
                        System.out.println(Printer.GREEN + "Placed Cards of " + playerState.getUsername() + ":" + Printer.RESET);
                        Printer.printCardsPlaced(playerState.getCardsPlaced());
                        return;
                    }
                }
                System.err.println("Usage: /showCards <hand/placed/ground/objective/player>");
            }
        } else {
            System.err.println("You can't view the Cards now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

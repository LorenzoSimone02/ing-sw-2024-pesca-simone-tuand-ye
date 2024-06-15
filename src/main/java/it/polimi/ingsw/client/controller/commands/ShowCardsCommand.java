package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;

/**
 * The class represents the command that shows the player's cards in the hand, the placed cards, the objective card, the placed cards of another Player
 */
public class ShowCardsCommand extends Command {

    public ShowCardsCommand() {
        commandName = "/showCards";
        description = "  Shows the cards in your hand, your placed cards, your objective, the placed cards of another Player or " +
                " \n  Usage: /showCards <hand/placed/ground/objective/player>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    /**
     * The method executes the command that shows the player's cards in the hand, the placed cards, the objective card, the placed cards of another Player
     * @param input the input of the command
     * @param clientManager the client manager
     */
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

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}

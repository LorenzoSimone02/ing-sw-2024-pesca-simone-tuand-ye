package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;

import java.util.ArrayList;

public class DrawCardCommand extends Command {

    public DrawCardCommand() {
        commandName = "/drawCard";
        description = "  Draw a Card on the ground or from a Deck\n  Usage: /drawCard <1/2/3/4/resources/gold>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String[] split = input.split(" ");
            if (split.length == 1) {
                String arg = split[0];
                int selected = -1;
                if (!arg.equalsIgnoreCase("gold") && !arg.equalsIgnoreCase("resources")) {
                    try{
                        selected = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                        System.err.println("Usage: /drawCard <1/2/3/4/resources/gold>");
                        return;
                    }
                }

                if (selected > 0 && selected < 5 && selected <= (clientManager.getGameState().getCardsOnGround().size() - 2) ) {

                    ArrayList<Card> drawableGroundCards = new ArrayList<>(4);

                    for (Card currCard: clientManager.getGameState().getCardsOnGround()) {
                        if (!(currCard instanceof ObjectiveCard)) drawableGroundCards.add(currCard);
                    }

                    Card card = drawableGroundCards.get(selected - 1);
                    clientManager.getNetworkHandler().sendPacket(new DrawCardPacket(card.getId()));
                } else if (arg.equalsIgnoreCase("gold")) {
                    clientManager.getNetworkHandler().sendPacket(new DrawCardPacket(true));
                } else if (arg.equalsIgnoreCase("resources")) {
                    clientManager.getNetworkHandler().sendPacket(new DrawCardPacket(false));
                } else {
                    System.err.println("Usage: /drawCard <1/2/3/4/resources/gold>");
                }
            } else {
                System.err.println("Usage: /drawCard <1/2/3/4/resources/gold>");
            }
        } else {
            System.err.println("You can't draw a Card now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus()) && clientManager.getGameState().getCardsInHand().size() < 3;
    }
}

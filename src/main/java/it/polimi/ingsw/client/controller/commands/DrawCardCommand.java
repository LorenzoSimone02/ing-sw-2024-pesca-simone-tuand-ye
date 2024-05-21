package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.server.model.card.Card;

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
                int selected;
                try{
                    selected = Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    System.err.println("Usage: /drawCard <1/2/3/4/resources/gold>");
                    return;
                }
                if (selected > 0 && selected < 5) {
                    Card card = clientManager.getGameState().getCardsOnGround().get(selected - 1);
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

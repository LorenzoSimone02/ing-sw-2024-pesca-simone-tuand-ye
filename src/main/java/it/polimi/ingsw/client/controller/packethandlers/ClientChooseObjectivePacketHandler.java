package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChooseObjectivePacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.model.card.ObjectiveCard;

/**
 * The class that handles the objective card choosing packets from the server
 */
public class ClientChooseObjectivePacketHandler extends ClientPacketHandler {

    /**
     * The method handles the objective card choosing packet
     * @param packet the choose objective packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ChooseObjectivePacket chooseObjectivePacket = (ChooseObjectivePacket) packet;
        if (chooseObjectivePacket.getChosenCardID() > 0) {
            ObjectiveCard card = (ObjectiveCard) clientManager.getGameState().getCardById(chooseObjectivePacket.getChosenCardID());
            System.out.println(Printer.GREEN + "You have chosen your personal Objective Card." + Printer.RESET);
            clientManager.getGameState().setObjectiveCard(card);
            clientManager.getGameState().setClientStatus(ClientStatusEnum.PLAYING);
        } else {
            clientManager.getGameState().setClientStatus(ClientStatusEnum.CHOOSING_OBJECTIVE);
            ObjectiveCard card1 = (ObjectiveCard) clientManager.getGameState().getCardById(chooseObjectivePacket.getCardID1());
            ObjectiveCard card2 = (ObjectiveCard) clientManager.getGameState().getCardById(chooseObjectivePacket.getCardID2());
            clientManager.getGameState().addProposedCard(card1);
            clientManager.getGameState().addProposedCard(card2);
            System.out.println(Printer.CYAN + "Choose one of the following cards with /chooseObjective <1/2>:" + Printer.RESET);
            Printer.printCard(card1);
            Printer.printCard(card2);
        }
    }
}

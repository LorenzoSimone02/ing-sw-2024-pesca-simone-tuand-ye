package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.ChooseObjectivePacket;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.PlayerController;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;

public class ServerChooseObjectivePacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        ChooseObjectivePacket chooseObjectivePacket = (ChooseObjectivePacket) packet;
        if (controller.getGame().getInfo().getGameStatus() != GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't choose your Objective Card now." + Printer.RESET));
            return;
        }
        int chosenCardID = chooseObjectivePacket.getChoosenCardID();
        if (chosenCardID > 0) {
            ObjectiveCard card = (ObjectiveCard) controller.getCardById(chosenCardID);
            controller.getPlayerController(clientConnection.getUsername()).chooseObjectiveCard(card);
            controller.getNetworkHandler().sendPacket(clientConnection, chooseObjectivePacket);
            for (PlayerController playerController : controller.getPlayerControllers()) {
                if (playerController.getPlayer().getObjectiveCard() == null) {
                    return;
                }
            }
            controller.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
            controller.getNetworkHandler().sendPacketToAll(new InfoPacket(Printer.GREEN + "All players have chosen their Objective Cards, the first turn is starting." + Printer.RESET));
            controller.getNetworkHandler().sendPacketToAll(new EndTurnPacket(controller.getGame().getInfo().getFirstPlayer().getUsername()));
        }
    }
}

package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.ChooseStarterFacePacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;

public class ServerChooseStarterFacePacketHandler extends ServerPacketHandler {
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        ChooseStarterFacePacket chooseStarterFacePacket = (ChooseStarterFacePacket) packet;
        if (controller.getGame().getInfo().getGameStatus() != GameStatusEnum.CHOOSING_STARTER_FACE) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't choose your Starter Card Face now." + Printer.RESET));
            return;
        }
        FaceEnum chosenStarterFace = chooseStarterFacePacket.getChosenStarterFace();
        if (chosenStarterFace != null) {
            StarterCard starterCard = (StarterCard) controller.getCardById(chooseStarterFacePacket.getStarterID());
            controller.getPlayerController(clientConnection.getUsername()).setStarterCard(starterCard, chooseStarterFacePacket.getChosenStarterFace());
            controller.getNetworkHandler().sendPacketToAll(new ChooseStarterFacePacket(chosenStarterFace, starterCard.getId(), clientConnection.getUsername()));
            controller.checkPreGameConditions();
        }

    }
}

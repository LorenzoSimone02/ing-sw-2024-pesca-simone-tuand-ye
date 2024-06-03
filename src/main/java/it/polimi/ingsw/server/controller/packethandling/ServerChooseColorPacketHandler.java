package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.ChooseColorPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.exceptions.AlreadyTakenColorException;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.TokenColorEnum;

public class ServerChooseColorPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        ChooseColorPacket chooseColorPacket = (ChooseColorPacket) packet;
        if (controller.getGame().getInfo().getGameStatus() != GameStatusEnum.CHOOSING_COLOR) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't choose your Token Color now." + Printer.RESET));
            return;
        }
        if (chooseColorPacket.getColor() == null) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "Invalid Color." + Printer.RESET));
            return;
        }
        try {
            controller.getPlayerController(clientConnection.getUsername()).chooseToken(TokenColorEnum.valueOf(chooseColorPacket.getColor()));
            controller.getNetworkHandler().sendPacketToAll(new ChooseColorPacket(clientConnection.getUsername(), chooseColorPacket.getColor()));
            controller.checkPreGameConditions();
        } catch (AlreadyTakenColorException e) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "The " + chooseColorPacket.getColor() + " Token Color has already been chosen." + Printer.RESET));
        }
    }
}

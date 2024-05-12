package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.ChooseColorPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.PlayerController;
import it.polimi.ingsw.server.controller.exceptions.AlreadyTakenColorException;
import it.polimi.ingsw.server.model.player.TokenColorEnum;

public class ServerChooseColorPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        ChooseColorPacket chooseColorPacket = (ChooseColorPacket) packet;
        if (chooseColorPacket.getColor() == null) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.ANSI_RED + "Invalid Color." + Printer.ANSI_RESET));
            return;
        }
        try {
            controller.getPlayerController(clientConnection.getUsername()).chooseToken(TokenColorEnum.valueOf(chooseColorPacket.getColor()));
            controller.getNetworkHandler().sendPacketToAll(new ChooseColorPacket(clientConnection.getUsername(), chooseColorPacket.getColor()));
            for(PlayerController playerController : controller.getPlayerControllers()){
                if(playerController.getPlayer().getToken() == null){
                    return;
                }
            }
            //TODO: Far scegliere la carta obiettivo
        } catch (AlreadyTakenColorException e) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.ANSI_RED + "The " + chooseColorPacket.getColor() + " Token Color has already been chosen." + Printer.ANSI_RESET));
        }
    }
}

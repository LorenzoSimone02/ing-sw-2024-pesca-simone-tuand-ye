package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.network.packets.GameStartedPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientGameStartedPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        GameStartedPacket gameStartedPacket = (GameStartedPacket) packet;

        clientManager.getGameState().setGameID(gameStartedPacket.getGameID());
        clientManager.getGameState().setActivePlayer(gameStartedPacket.getFirstPlayer());
        clientManager.getGameState().setFirstPlayer(gameStartedPacket.getFirstPlayer());
        for (String player : gameStartedPacket.getPlayers()) {
            if (!player.equals(clientManager.getGameState().getUsername()))
                clientManager.getGameState().addPlayerState(new PlayerState(player));
        }
        clientManager.getGameState().setClientStatus(ClientStatusEnum.CHOOSING_COLOR);
        System.out.println(Printer.CYAN + "The game has started.\n" +
                "Before playing, choose your Token color with the command /chooseColor <color>" + Printer.RESET);
    }
}

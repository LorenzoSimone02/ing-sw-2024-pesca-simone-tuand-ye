package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.GameStartedPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientGameStartedPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        GameStartedPacket gameStartedPacket = (GameStartedPacket) packet;

        clientManager.getGameState().setClientStatus(ClientStatusEnum.PLAYING);

        clientManager.getGameState().setGameID(gameStartedPacket.getGameID());
        clientManager.getGameState().setActivePlayer(gameStartedPacket.getFirstPlayer());
        clientManager.getGameState().setFirstPlayer(gameStartedPacket.getFirstPlayer());
        for(String player : gameStartedPacket.getPlayers()) {
            clientManager.getGameState().addPlayerScore(player, 0);
        }
    }
}

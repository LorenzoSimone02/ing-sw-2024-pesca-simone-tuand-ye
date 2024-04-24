package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.Packet;

public class ClientLoginPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        clientManager.getGameState().setClientStatus(ClientStatusEnum.LOBBY);
    }
}

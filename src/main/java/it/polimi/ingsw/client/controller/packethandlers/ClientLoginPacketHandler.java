package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.LoginPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientLoginPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        LoginPacket loginPacket = (LoginPacket) packet;
        clientManager.getGameState().setUsername(loginPacket.getUsername());
        clientManager.getGameState().setClientStatus(ClientStatusEnum.LOGGED);
        new Thread(() -> clientManager.getNetworkHandler().sendPacket(new JoinPacket())).start();
    }
}

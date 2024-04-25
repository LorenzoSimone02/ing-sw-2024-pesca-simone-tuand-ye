package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientCreateGamePacketHandler extends ClientPacketHandler {
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        new Thread(() -> clientManager.getNetworkHandler().sendPacket(new JoinPacket())).start();
    }
}

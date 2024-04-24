package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingPacket;

public class ClientPingPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        clientManager.getGameState().setLastPing(System.currentTimeMillis());
        new Thread(() -> clientManager.getNetworkHandler().sendPacket(new PingPacket())).start();
    }
}

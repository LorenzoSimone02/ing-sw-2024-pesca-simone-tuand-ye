package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

import java.util.Iterator;

public class PacketHandlerThread implements Runnable {

    private final ServerNetworkHandler serverNetworkHandler;

    public PacketHandlerThread(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (serverNetworkHandler) {
                if (serverNetworkHandler.getPacketQueue().isEmpty()) continue;
                Iterator<Packet> packetIterator = serverNetworkHandler.getPacketQueue().keySet().iterator();
                Packet packet = packetIterator.next();
                packet.getServerPacketHandler().handlePacket(packet, serverNetworkHandler.getGameController(), serverNetworkHandler.getPacketQueue().get(packet));
                packetIterator.remove();
            }
        }
    }
}

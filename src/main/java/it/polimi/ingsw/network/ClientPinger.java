package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.PingPacket;

public class ClientPinger implements Runnable {

    private final ServerNetworkHandler serverNetworkHandler;

    public ClientPinger(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                for (ClientConnection connection : serverNetworkHandler.getConnections()) {
                    serverNetworkHandler.sendPacket(connection, new PingPacket());
                }
            } catch (InterruptedException e) {
                System.err.println("Ping thread interrupted");
            }
        }
    }
}

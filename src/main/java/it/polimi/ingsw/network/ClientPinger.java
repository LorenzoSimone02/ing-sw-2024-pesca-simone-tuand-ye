package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.PingPacket;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientPinger implements Runnable {

    private final ServerNetworkHandler serverNetworkHandler;

    public ClientPinger(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    @Override
    public void run() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("Pinging " + serverNetworkHandler.getConnections().size() + " clients");
            for (ClientConnection conn : serverNetworkHandler.getConnections()) {
                serverNetworkHandler.sendPacket(conn, new PingPacket());
            }
        }, 1, 3, TimeUnit.SECONDS);
    }
}

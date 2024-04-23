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
    public synchronized void run() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("Pinging " + serverNetworkHandler.getConnections().size() + " clients");
            for (ClientConnection conn : serverNetworkHandler.getConnections()) {
                try {
                    serverNetworkHandler.sendPacket(conn, new PingPacket());
                } catch (Exception e) {
                    System.err.println("Lost connection with " + conn.getUsername() + " due to " + e.getMessage());
                }
            }
        }, 1, 3, TimeUnit.SECONDS);
    }
}

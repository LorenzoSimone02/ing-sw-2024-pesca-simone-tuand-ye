package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.PingPacket;
import it.polimi.ingsw.server.model.player.Player;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientPingerThread implements Runnable {

    private final ServerNetworkHandler serverNetworkHandler;

    public ClientPingerThread(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    @Override
    public void run() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Iterator<ClientConnection> iter = serverNetworkHandler.getConnections().iterator();
            while (iter.hasNext()) {
                ClientConnection conn = iter.next();
                serverNetworkHandler.sendPacket(conn, new PingPacket());
                long latency = System.currentTimeMillis() - conn.getLastPing();
                if(latency > 7000) {
                    System.err.println("Lost connection with " + conn.getUsername() + " due to timeout");
                    iter.remove();
                }
            }
        }, 0, 3, TimeUnit.SECONDS);
    }
}

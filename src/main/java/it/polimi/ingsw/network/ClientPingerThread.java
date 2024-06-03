package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.PingPacket;
import it.polimi.ingsw.server.ServerMain;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ClientPingerThread is the class that represents the thread that pings the clients to check if they are still connected
 */
public class ClientPingerThread implements Runnable {

    /**
     * The ServerNetworkHandler
     */
    private final ServerNetworkHandler serverNetworkHandler;

    /**
     * Constructor of the class
     *
     * @param serverNetworkHandler the ServerNetworkHandler
     */
    public ClientPingerThread(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    /**
     * The method pings the clients to check if they are still connected, if not it removes them from the list of connected clients
     */
    @Override
    public void run() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Iterator<ClientConnection> iter = serverNetworkHandler.getConnections().iterator();
            while (iter.hasNext()) {
                ClientConnection conn = iter.next();
                serverNetworkHandler.sendPacket(conn, new PingPacket());
                long latency = System.currentTimeMillis() - conn.getLastPing();
                if (latency > 5000) {
                    iter.remove();
                    if (conn.getUsername() != null) {
                        System.err.println("Lost connection with client " + conn.getUsername());
                        for (ServerNetworkHandler serverNetworkHandlers : ServerMain.getMatches()) {
                            serverNetworkHandlers.getGameController().onDisconnect(conn.getUsername());
                            serverNetworkHandlers.removeConnection(conn);
                        }
                        serverNetworkHandler.getGameController().onDisconnect(conn.getUsername());
                        serverNetworkHandler.removeConnection(conn);
                    }
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}

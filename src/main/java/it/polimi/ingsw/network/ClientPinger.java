package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.PingPacket;

import java.util.Iterator;

public class ClientPinger implements Runnable {

    private final ServerNetworkHandler serverNetworkHandler;

    public ClientPinger(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(3000);
                Iterator<ClientConnection> iterator = serverNetworkHandler.getConnections().listIterator();
                System.out.println("Pinging " + serverNetworkHandler.getConnections().size() + " clients");
                while(iterator.hasNext()){
                    ClientConnection connection = iterator.next();
                    serverNetworkHandler.sendPacket(connection, new PingPacket());
                }
            } catch (InterruptedException e) {
                System.err.println("Ping thread interrupted");
            }
        }
    }
}

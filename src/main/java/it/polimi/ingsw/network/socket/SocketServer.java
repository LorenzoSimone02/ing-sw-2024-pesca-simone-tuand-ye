package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ServerNetworkHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {
    private final ServerSocket serverSocket;
    private final ServerNetworkHandler serverNetworkHandler;

    public SocketServer(ServerNetworkHandler networkHandler, int port) throws IOException {
        this.serverNetworkHandler = networkHandler;
        serverSocket = new ServerSocket(port);
        start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                SocketClientConnection newConnection = new SocketClientConnection(socket, this);
                new Thread(newConnection).start();
            } catch (IOException e) {
                System.err.println("Could not accept a new connection " + e);
            }
        }
    }

    public ServerNetworkHandler getServerNetworkHandler() {
        return serverNetworkHandler;
    }

}

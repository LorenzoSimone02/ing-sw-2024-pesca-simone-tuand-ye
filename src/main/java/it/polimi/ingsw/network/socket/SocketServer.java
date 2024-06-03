package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ServerNetworkHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer extends Thread {

    private final ServerSocket serverSocket;
    private final ServerNetworkHandler serverNetworkHandler;
    private final ArrayList<Thread> clientThreads;

    public SocketServer(ServerNetworkHandler networkHandler, int port) throws IOException {
        this.serverNetworkHandler = networkHandler;
        serverSocket = new ServerSocket(port);
        clientThreads = new ArrayList<>();
        start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (serverSocket.isClosed())
                    break;
                Socket socket = serverSocket.accept();
                SocketClientConnection newConnection = new SocketClientConnection(socket, this);
                Thread clientThread = new Thread(newConnection);
                clientThreads.add(clientThread);
                clientThread.start();
            } catch (IOException e) {
                System.err.println("Could not accept a new connection " + e);
            }
        }
    }

    public ServerNetworkHandler getServerNetworkHandler() {
        return serverNetworkHandler;
    }

    public void stopServer() {
        try {
            this.interrupt();
            for (Thread clientThread : clientThreads) {
                clientThread.interrupt();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not close the server socket " + e);
        }
    }
}

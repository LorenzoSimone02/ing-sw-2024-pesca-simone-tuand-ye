package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ServerNetworkHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * SocketServer is the class that represents the server-side socket network handler
 */
public class SocketServer extends Thread {

    /**
     * The server socket
     */
    private final ServerSocket serverSocket;

    /**
     * The server-side network handler
     */
    private final ServerNetworkHandler serverNetworkHandler;

    /**
     * The list of the clients' threads
     */
    private final ArrayList<Thread> clientThreads;

    /**
     * Constructor of the class
     * @param networkHandler the server-side network handler
     * @param port the port of the server
     * @throws IOException if an I/O error occurs when creating the server socket
     */
    public SocketServer(ServerNetworkHandler networkHandler, int port) throws IOException {
        this.serverNetworkHandler = networkHandler;
        serverSocket = new ServerSocket(port);
        clientThreads = new ArrayList<>();
        start();
    }

    /**
     * The method runs the server
     */
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

    /**
     * The method returns the server-side network handler
     * @return the server-side network handler
     */
    public ServerNetworkHandler getServerNetworkHandler() {
        return serverNetworkHandler;
    }

    /**
     * The method stops the server
     */
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

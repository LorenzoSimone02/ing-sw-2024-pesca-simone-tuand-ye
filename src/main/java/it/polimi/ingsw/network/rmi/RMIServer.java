package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMIServer is the class that represents the server-side RMI network handler
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    /**
     * The server-side network handler
     */
    private final ServerNetworkHandler networkHandler;
    /**
     * The RMI registry
     */
    private final Registry registry;

    /**
     * The name of the registry
     */
    private final String registryName;

    /**
     * Constructor of the class
     * @param networkHandler the server-side network handler
     * @param registryName the name of the registry
     * @param port the port of the server
     * @throws RemoteException if there is an error with the remote connection
     */
    public RMIServer(ServerNetworkHandler networkHandler, String registryName, int port) throws RemoteException {
        super();
        this.networkHandler = networkHandler;
        this.registryName = registryName;
        registry = LocateRegistry.createRegistry(port);
        registry.rebind(registryName, this);
    }

    /**
     * The method receives a packet from a client if the client is already connected, otherwise it creates a new connection
     * @param packet the packet received
     * @param clientInterface the client-side RMI interface
     */
    public void receivePacket(Packet packet, RMIClientInterface clientInterface) throws InterruptedException {
        RMIClientConnection conn = (RMIClientConnection) networkHandler.getConnectionByUUID(packet.getSender());
        if (conn != null) {
            networkHandler.receivePacket(packet, conn);
        } else {
            RMIClientConnection connection = new RMIClientConnection(clientInterface);
            connection.setUUID(packet.getSender());
            networkHandler.addConnection(connection);
            networkHandler.receivePacket(packet, connection);
        }
    }

    /**
     * The method tries to stop the RMI server, if it is not possible it prints an error message
     */
    public void stopServer(){
        try {
            registry.unbind(registryName);
            UnicastRemoteObject.unexportObject(this, true);
        } catch (Exception e) {
            System.err.println("Could not stop the RMI server " + e);
        }
    }
}

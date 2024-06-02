package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMIClient is the class that represents the client-side RMI network handler
 */
public class RMIClient extends ClientNetworkHandler implements RMIClientInterface {

    /**
     * The RMI server-side interface
     */
    private final RMIServerInterface serverInterface;

    /**
     * Constructor of the class
     * @param registryName the name of the registry
     * @param serverIP the IP address of the server
     * @param port the port of the server
     * @throws NotBoundException if the registry is not bound
     * @throws RemoteException if there is an error with the remote connection
     */
    public RMIClient(String registryName, String serverIP, int port) throws NotBoundException, RemoteException {
        super();
        Registry registry = LocateRegistry.getRegistry(serverIP, port);
        this.serverInterface = (RMIServerInterface) registry.lookup(registryName);
    }

    /**
     * The method tries to send a packet to the server, if it is not possible
     *      or if the packet is null it prints two different error messages
     * @param packet the packet to send
     */
    public void sendPacket(Packet packet) {
        if (packet != null) {
            try {
                super.sendPacket(packet);
                serverInterface.receivePacket(packet, this);
            } catch (RemoteException e) {
                System.err.println("Error sending packet: " + e);
            }
        } else {
            System.err.println("Packet is null");
        }
    }

}

package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient extends ClientNetworkHandler implements RMIClientInterface {

    private final RMIServerInterface serverInterface;

    public RMIClient(String registryName, String serverIP, int port) throws NotBoundException, RemoteException {
        super();
        Registry registry = LocateRegistry.getRegistry(serverIP, port);
        this.serverInterface = (RMIServerInterface) registry.lookup(registryName);
    }

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

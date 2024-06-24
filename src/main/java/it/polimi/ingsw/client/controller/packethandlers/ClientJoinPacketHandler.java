package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The class that handles the joining client packets from the server
 */
public class ClientJoinPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the joining client packet
     * @param packet the client join packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        JoinPacket joinPacket = (JoinPacket) packet;
        int gameID = joinPacket.getGameID();

        try {
            if (gameID == -1) {
                ClientNetworkHandler newHandler = clientManager.getNetworkHandler() instanceof RMIClient ? new RMIClient("CodexNaturalisServer", clientManager.getServerIP(), 1099) : new SocketClient(clientManager.getServerIP(), 5000);
                newHandler.setClientManager(clientManager);
                clientManager.setNetworkHandler(newHandler);
                clientManager.getGameState().setClientStatus(ClientStatusEnum.LOGGED);
                if (clientManager.getViewMode() == ViewModeEnum.GUI) {
                    GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
                    guiClient.changeScene(clientManager.getGameState().getClientStatus());
                }
                return;
            }

            ClientNetworkHandler newHandler = clientManager.getNetworkHandler() instanceof RMIClient ? new RMIClient("Game" + gameID, clientManager.getServerIP(), 1099 + gameID) : new SocketClient(clientManager.getServerIP(), 5000 + gameID);
            newHandler.setClientManager(clientManager);
            clientManager.setNetworkHandler(newHandler);
            clientManager.getGameState().setClientStatus(ClientStatusEnum.CONNECTED);
            if (clientManager.getViewMode() == ViewModeEnum.GUI) {
                GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
                guiClient.changeScene(clientManager.getGameState().getClientStatus());
            }
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Error while connecting to the game server");
            throw new RuntimeException(e);
        }
    }
}

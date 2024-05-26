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

public class ClientJoinPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        JoinPacket joinPacket = (JoinPacket) packet;
        int gameID = joinPacket.getGameID();

        if (gameID == -1) {
            if (clientManager.getViewMode() == ViewModeEnum.TUI) {
                System.out.println("There are no available Games at the moment. Use /createGame to create one or /join to try again.");
            } else {
                GUIClient client = (GUIClient) clientManager.getUserInterface();
                client.updateScene(clientManager.getGameState().getClientStatus());
            }
            return;
        }

        try {
            ClientNetworkHandler newHandler = clientManager.getNetworkHandler() instanceof RMIClient ? new RMIClient("Game" + gameID, clientManager.getServerIP(), 1099 + gameID) : new SocketClient(clientManager.getServerIP(), 5000 + gameID);
            newHandler.setClientManager(clientManager);
            clientManager.setNetworkHandler(newHandler);
            clientManager.getGameState().setClientStatus(ClientStatusEnum.CONNECTED);
            clientManager.getGameState().setGameID(joinPacket.getGameID());
            if(clientManager.getViewMode() == ViewModeEnum.GUI) {
                GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
                guiClient.updateScene(clientManager.getGameState().getClientStatus());
            }
        } catch (NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}

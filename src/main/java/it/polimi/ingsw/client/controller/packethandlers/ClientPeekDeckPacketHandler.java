package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class ClientPeekDeckPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        PeekDeckPacket peekDeckPacket = (PeekDeckPacket) packet;
        if (peekDeckPacket.isGold()) {
            GoldCard card = (GoldCard) clientManager.getGameState().getCardById(peekDeckPacket.getTopDeckCardId());
            clientManager.getGameState().setTopGoldDeckCard(card);
        } else {
            ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(peekDeckPacket.getTopDeckCardId());
            clientManager.getGameState().setTopResourcesDeckCard(card);
        }
        if(clientManager.getViewMode() == ViewModeEnum.GUI){
            GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
            guiClient.updateCurrentScene("updateDecks");
        }
    }
}

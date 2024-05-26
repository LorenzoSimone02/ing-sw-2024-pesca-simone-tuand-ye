package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChooseStarterFacePacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChooseStarterFacePacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.model.card.FaceEnum;

public class ChooseStarterFacePacket extends Packet{

    private FaceEnum chosenStarterFace;
    private int starterID;
    private String username;

    public ChooseStarterFacePacket(FaceEnum chosenStarterFace, int starterID, String username) {
        this.chosenStarterFace = chosenStarterFace;
        this.starterID = starterID;
        this.username = username;
    }

    public ChooseStarterFacePacket(int starterID, String username) {
        this.starterID = starterID;
        this.chosenStarterFace = null;
        this.username = username;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChooseStarterFacePacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChooseStarterFacePacketHandler();
    }

    public FaceEnum getChosenStarterFace() {
        return chosenStarterFace;
    }

    public int getStarterID() {
        return starterID;
    }

    public String getUsername() {
        return username;
    }
}

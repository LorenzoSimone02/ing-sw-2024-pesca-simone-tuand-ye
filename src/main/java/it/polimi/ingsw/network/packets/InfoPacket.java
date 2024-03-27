package it.polimi.ingsw.network.packets;

public class InfoPacket extends ClientPacket {

    private final String data;

    public InfoPacket(String data) {
        this.data = data;
    }

    @Override
    public void handle() {
        System.out.println("Test packet received by " + getSender() + " with data: " + data);
    }
}

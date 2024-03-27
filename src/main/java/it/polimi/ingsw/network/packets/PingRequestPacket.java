package it.polimi.ingsw.network.packets;

public class PingRequestPacket extends Packet {
    public PingRequestPacket() {
    }

    @Override
    public void handle() {
        System.out.println("The server is requesting a Ping");
    }
}

package it.polimi.ingsw.network;

/**
 * Abstract class representing the server-side state of a client connected to the server.
 */
public abstract class ClientConnection {
    /**
     * The User nickanme
     */
    private String nickname;

    /**
     * Whether the client is connected
     */
    private boolean isConnected;

    /**
     * This method sends a message through the network
     */
    //public abstract void sendMessage(Message m);

    public ClientConnection(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getNickname() {
        return nickname;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
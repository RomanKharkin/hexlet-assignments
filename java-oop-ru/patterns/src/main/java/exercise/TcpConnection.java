package exercise;

import exercise.connections.Connection;
import exercise.connections.Disconnected;

// BEGIN
public class TcpConnection {

    private Connection connectionState;

    private String ip;
    private int port;


    public TcpConnection(String ip, int port) {
        this.connectionState = new Disconnected(this);
        this.ip = ip;
        this.port = port;
    }

    public void setConnectionState(Connection connectionState) {
        this.connectionState = connectionState;
    }

    public String getCurrentState() {
        return connectionState.getName();
    }
    public void connect() {
        connectionState.connect();
    }
    public void disconnect() {
        connectionState.disconnect();
    }
    public void write(String data) {
        connectionState.write(data);
    }
}
// END

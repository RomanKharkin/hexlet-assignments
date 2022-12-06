package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Disconnected implements Connection {

    private TcpConnection  tcpConnection;

    public Disconnected(TcpConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
    }
    @Override
    public void connect() {
        tcpConnection.setConnectionState(new Connected(tcpConnection));
    }

    @Override
    public void disconnect() {
        System.out.print("Error! Connection already disconnected");
    }

    @Override
    public void write(String data) {
        System.out.print("Error! Connection disconnected");
    }

    @Override
    public String getName() {
        return "disconnected";
    }
}
// END

import java.net.*;

public class UDPServerEcho {
    public static void main(String[] args) {
        final int PORT = 5551;
        byte[] buffer = new byte[2048];

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                // Receive packet
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // Get the client's address and port
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                // Get the received message
                int length = packet.getLength();
                String message = new String(packet.getData(), 0, length);
                System.out.println("Received: " + message);

                // Send the same message back to the client
                DatagramPacket echoPacket = new DatagramPacket(packet.getData(), length, clientAddress, clientPort);
                socket.send(echoPacket);
                System.out.println("Message Echoed: " + message);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

import java.net.*;
import java.io.*;

public class UdpDateTimeClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 5551;
        byte[] buffer = new byte[2048];

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            // Send a request to the server
            String requestMessage = "GET_DATE_TIME";
            byte[] requestBytes = requestMessage.getBytes();
            DatagramPacket requestPacket = new DatagramPacket(requestBytes, requestBytes.length, serverAddress, SERVER_PORT);
            socket.send(requestPacket);  // Send the packet
            System.out.println("Request sent to server.");

            // Create a packet to receive the date and time
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);  // Receive the packet

            String dateTime = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Received from server: " + dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpDateTimeServer {
    public static void main(String[] args) {
        final int PORT = 5551;
        byte[] buffer = new byte[2048];

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                // Create a packet to receive data
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);  // Receive a packet

                // Get the client's address and port
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                // Get the current date and time
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                byte[] dateTimeBytes = dateTime.getBytes();

                // Send the current date and time back to the client
                DatagramPacket responsePacket = new DatagramPacket(dateTimeBytes, dateTimeBytes.length, clientAddress, clientPort);
                socket.send(responsePacket);  // Send the packet back
                System.out.println("Sent date and time: " + dateTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

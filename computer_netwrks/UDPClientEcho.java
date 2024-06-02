import java.net.*;
import java.io.*;

public class UDPClientEcho {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 5551;
        byte[] buffer = new byte[2048];

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter a message to send to the server, type 'bye' to exit:");

            while (true) {
                System.out.print("> ");
                String userInput = userInputReader.readLine();
                if (userInput == null || "bye".equalsIgnoreCase(userInput.trim())) {
                    break;
                }

                byte[] buf = userInput.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, serverAddress, SERVER_PORT);
                socket.send(sendPacket);
                System.out.println("Sent message: " + userInput);

                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from server: " + receivedMessage);
            }

            System.out.println("Closing UDP client.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

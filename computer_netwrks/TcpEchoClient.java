import java.net.*;
import java.io.*;

public class TcpEchoClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 5555;

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to the server");

            // Create input and output streams
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            // Prompt user for input
            System.out.print("Enter a message to send to the server: ");
            String userInput = userInputReader.readLine();

            // Send message to the server
            output.println(userInput);
            System.out.println("Sent to server: " + userInput);

            // Read echoed message from the server
            String response = input.readLine();
            System.out.println("Received from server: " + response);

        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}

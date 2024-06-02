import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TcpServer {
    public static void main(String[] args) {
        final int PORT = 5555;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("New client connected");

                    // Create input and output streams for the client
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                    // Create input stream for server-side user
                    Scanner serverInput = new Scanner(System.in);

                    // Read message from the client
                    String message = input.readLine();
                    System.out.println("Received from client: " + message);

                    // Prompt server user for response
                    System.out.print("Enter a response to the client: ");
                    String response = serverInput.nextLine();

                    // Send response to the client
                    output.println(response);
                    System.out.println("Sent to client: " + response);
                } catch (IOException e) {
                    System.out.println("Server exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + PORT);
            e.printStackTrace();
        }
    }
}

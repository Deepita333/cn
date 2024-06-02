import java.net.*;
import java.io.*;

public class TcpEchoServer {
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

                    // Read message from the client
                    String message = input.readLine();
                    System.out.println("Received from client: " + message);

                    // Send the same message back to the client (echo)
                    output.println(message);
                    System.out.println("Echoed to client: " + message);
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

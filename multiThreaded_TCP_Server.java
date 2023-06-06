import java.io.*;
import java.net.*;


public class multiThreaded_TCP_Server {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server listening on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                // Start a new thread to handle the client's request
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received message from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ": " + message);

                    // Echo the message back to the client
                    writer.println(message);

                    if (message.equals("bye")) {
                        break;
                    }
                }

                reader.close();
                writer.close();
                clientSocket.close();
                System.out.println("Connection closed with " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



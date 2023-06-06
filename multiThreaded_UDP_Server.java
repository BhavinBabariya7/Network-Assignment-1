import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class multiThreaded_UDP_Server {
    private static final int SERVER_PORT = 12345;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("Server listening on port " + SERVER_PORT);

            while (true) {
                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, BUFFER_SIZE);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message from " + receivePacket.getAddress() + ":" + receivePacket.getPort() + ": " + message);

                // Start a new thread to handle the client's request
                Thread clientThread = new Thread(new ClientHandler(serverSocket, receivePacket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private DatagramSocket serverSocket;
        private DatagramPacket receivePacket;

        public ClientHandler(DatagramSocket serverSocket, DatagramPacket receivePacket) {
            this.serverSocket = serverSocket;
            this.receivePacket = receivePacket;
        }

        @Override
        public void run() {
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Echo the message back to the client
            try {
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Sent message to " + receivePacket.getAddress() + ":" + receivePacket.getPort() + ": " + message);
        }
    }
}



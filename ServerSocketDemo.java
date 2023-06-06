import java.net.*;
import java.io.*;
import java.util.*;

class ServerSocketDemo {
    public static void main(String args[]) {
        try {
            ServerSocket ss = new ServerSocket(7);
            while (true) {
                Socket client = ss.accept();
                System.out.println("Socket created");
                System.out.println("client inet address: " + client.getInetAddress());
                System.out.println("client port: " + client.getPort());
                OutputStream out = client.getOutputStream();
                PrintWriter pw = new PrintWriter(out, true);
                System.out.println("Streams created ");
                String x = "Hello, How are you? ";
                Calendar c = Calendar.getInstance();
                pw.println(x);
                pw.println("The server date and time is :" + c.getTime());
                System.out.println("Contents written to" + client.getInetAddress().getHostName());
                pw.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

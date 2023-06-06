// UDP client

import java.net.*;
import java.io.*;

public class DatagramClient {
    public static void main(String args[]) {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            DatagramSocket ds = new DatagramSocket(1024, ia);
            String x = "Hello Server";
            byte[] b = x.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, ia, 8);
            ds.send(dp);
            System.out.println("sending to server: " + (new String(b)));
            byte[] buff = new byte[50];
            DatagramPacket in = new DatagramPacket(buff, buff.length);
            ds.receive(in);
            System.out.println("received from server: " + (new String(buff)));
            ds.close();
        } catch (SocketException se) {
            System.out.println(se);
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
}

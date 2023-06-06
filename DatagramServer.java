// UDP Server

import java.net.*;

public class DatagramServer {
    public static void main(String args[]) {
        try {
            DatagramSocket ds = new DatagramSocket(8);
            byte[] b = new byte[50];
            DatagramPacket in = new DatagramPacket(b, b.length);
            ds.receive(in);
            System.out.println(new String(b));
            String x = "Hello client";
            byte buff[] = x.getBytes();
            DatagramPacket out = new DatagramPacket(buff, buff.length, in.getAddress(), in.getPort());
            ds.send(out);
            ds.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

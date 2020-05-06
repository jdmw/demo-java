package jd.demo.se.io.net;

import jd.util.lang.concurrent.CcUt;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DemoUdp {

    final static int PORT = 8889 ;
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        DatagramSocket server = new DatagramSocket();
        byte[] msg = "Hello!".getBytes() ;
        DatagramPacket sendPacket = new DatagramPacket(msg,msg.length, InetAddress.getLoopbackAddress(),PORT);
        CcUt.start(()->{
            try {
                server.send(sendPacket);
                DemoSocket.printGetter("server",server);
            } catch (IOException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        },false);


        DatagramSocket client = new DatagramSocket(PORT);
        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePackage = new DatagramPacket(receiveBuffer,receiveBuffer.length);
        client.receive(receivePackage);
        System.out.println("receive: " + new String(receivePackage.getData()));
        DemoSocket.printGetter("client",client);

        client.close();
        server.close();

    }

    public static void server() throws IOException {

    }


}

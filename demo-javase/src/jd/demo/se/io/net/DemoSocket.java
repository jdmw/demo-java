package jd.demo.se.io.net;

import jd.util.lang.concurrent.CcUt;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;

public class DemoSocket {


    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        int port = 8888 ;
        ServerSocket serverSocket = new ServerSocket(port);
        printGetter("ServerSocket",serverSocket);

        CcUt.start(()->{
            try {
                printGetter("server socket",serverSocket.accept());
            } catch (IOException|IllegalAccessException|InvocationTargetException e) {
                e.printStackTrace();
            }
        },false);
        Socket socket = new Socket();
        // throw BindException: socket.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(),port));
        socket.connect(new InetSocketAddress(serverSocket.getInetAddress(),port),1000);
        InetAddress inetAddress = socket.getInetAddress();
        InetAddress localAddress = socket.getLocalAddress();
        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        printGetter("client socket",socket);

        socket.close();
        serverSocket.close();
    }

    public static <T> void printGetter(String title,T instance) throws InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder("\n -------------- "+title+ "--------------- \n");
        for ( Method method : instance.getClass().getMethods()){
            if(method.getName().startsWith("get") && method.getParameterCount() == 0){
                Object result = method.invoke(instance);
                sb.append(method.getName().substring(3)).append(" : ").append(result).append("\n");
            }
        }
        System.out.println(sb);
    }
}

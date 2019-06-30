package jd.demo.ds.tech.rpc.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jd.demo.ds.tech.rpc.communication.SerialVO;

public class RegestryServer {

	private final ServerSocket server ;
	public RegestryServer(int port) throws IOException {
		server = new ServerSocket(port);
	}
	
	public void start() throws IOException {
		ExecutorService pool = Executors.newWorkStealingPool();
    	Socket socket = null ;
    	while((socket = server.accept()) != null) {
    		Socket client = socket  ;
    		pool.execute(()->{
    			try(InputStream is = client.getInputStream();
    				OutputStream os = client.getOutputStream();
    				ObjectInputStream ois = new ObjectInputStream(is);
    				ObjectOutputStream oos = new ObjectOutputStream(os)) {
    				
    				int command = ois.readInt();
    				
    				
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                	try {
						client.close();
					} catch (IOException e) {
						try {
							client.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
                }
    		});
    	}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

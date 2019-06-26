package jd.demo.rpc.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import jd.demo.rpc.thrift.bean.Sex;
import jd.demo.rpc.thrift.bean.User;
import jd.demo.rpc.thrift.iface.Interview;

public class DemoTClient {

	public static final String SERVER_IP = "localhost";
    //public static final int SERVER_PORT = 8080;
    public static final int TIMEOUT = 30000;
    
	public static void main(String[] args) throws TException {
		
        TTransport transport = new TSocket(SERVER_IP, DemoTServer.SERVER_PORT, TIMEOUT);
        TProtocol protocol = new TBinaryProtocol(transport);

		Interview.Client client = new Interview.Client(protocol);
        
        transport.open();
        
       
        System.out.println(client.interview(new User("Jim",Sex.M,21)));
		System.out.println(client.interview(new User("Jim",Sex.W,10)));
		System.out.println(client.interview(new User("Jim",Sex.M,21)));
		System.out.println(client.interview(new User("Jim",Sex.W,59)));
		

        transport.close();
        
	}

}

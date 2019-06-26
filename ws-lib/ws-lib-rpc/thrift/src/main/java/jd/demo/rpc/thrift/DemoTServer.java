package jd.demo.rpc.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import jd.demo.rpc.thrift.iface.Interview;
import jd.demo.rpc.thrift.service.InterviewImpl;

public class DemoTServer {

	public static int SERVER_PORT = 8081 ;
	public static void main(String[] args) throws TTransportException {

		// 创建 TProcessor
        TProcessor tprocessor = 
                new Interview.Processor<Interview.Iface>(new InterviewImpl());

        // 创建 TServerTransport, TServerSocket 继承于 TServerTransport
        TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
        
        // 创建 TProtocol
        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
        
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(protocolFactory);

        // 创建 TServer
        TServer server = new TSimpleServer(tArgs);
        // 启动 Server
        server.serve();
	}

}

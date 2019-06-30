package jd.server.debug;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

import jd.util.lang.concurrent.CcUt;

public class DebugClient {

	public static void send(String msg,Socket client) throws IOException {
		OutputStream os = client.getOutputStream();
		IOUtils.write(msg,os);
		os.flush();
		System.out.println("send: " + msg);
		IOUtils.closeQuietly(client);
	}
	
	public static void receive(InputStream is,Socket client,SimpleUI clientUI) {
		CcUt.start(() -> {
			String content = null;
			byte[] bs = new byte[1024];
			int n;
			while (true) {
				try {
					// is = socket.getInputStream();
					// System.out.format("socket closed:%b | connected:%s | keep alive():%b | input
					// stream shutdown:%b
					// \n",client.isClosed(),client.isConnected(),client.isInputShutdown(),client.getKeepAlive());
					if (!client.isClosed()) {
						while ((n = is.read(bs)) != -1) {
							content = new String(bs, 0, n);
							if (clientUI != null) {
								clientUI.showMsg(content);
							}
							System.out.println("receive from Server : " + content);
						}
					} else if (client.getKeepAlive() == false) {
						client.close();
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
	}
	
	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String[] args) throws IOException  {
		//SimpleUI clientUI = new SimpleUI("Client Side");
		Socket client = new Socket("127.0.0.1",DebugServer.PORT);
		InputStream is = client.getInputStream();
		try {
			// System.out.println(IOUtils.toString(is));
			send("Hello",client);
			//clientUI.bind(client);
			//receive(is,client,clientUI);
			//serverUI.readSocket();
			//client.shutdownInput();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//IOUtils.closeQuietly(is, client.getOutputStream());
			//IOUtils.closeQuietly(client);
		}
	}

}
package jd.server.debug;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import jd.util.lang.concurrent.CcUt;

public class DebugServer {

	public static final int PORT = 8011;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		SimpleUI serverUI = new SimpleUI("Server Side");
		ServerSocket ser = null;
		Socket socket = null;
		ser = new ServerSocket(PORT);
		while ((socket = ser.accept()) != null) {
			final InputStream is = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);
			final Socket client = socket ;
			
			my(client,is,serverUI);
		}
	}

	public static void my(Socket client,InputStream is,SimpleUI serverUI) {
		CcUt.start(() -> {
			serverUI.showMsg("connecting to " + client.getPort());
			try {
				// System.out.println(IOUtils.toString(is));
				//
				serverUI.bind(client);
				CcUt.start(() -> {
					String content = null;
					byte[] bs = new byte[1024];
					char[] cs = new char[1024];
					int n;
					while(true) {
						try {
							// is = client.getInputStream();
							//System.out.format("socket closed:%b | connected:%s | keep alive():%b | input stream shutdown:%b \n",client.isClosed(),client.isConnected(),client.isInputShutdown(),client.getKeepAlive());
							//if(!client.isClosed() ) {
								while ((n = is.read(bs)) != -1) {
									content = new String(bs, 0, n);
									System.out.println("from client:" + content);
									serverUI.showMsg(content);
								}
							if (client.getKeepAlive() == false) {
								client.close();
								System.out.println("client closed");
								Thread.currentThread().stop();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				//client.shutdownInput();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//IOUtils.closeQuietly(is,client.getOutputStream());
				//IOUtils.closeQuietly(client);
			}
		});
	}
	
	public static void examble(Socket socket,InputStreamReader reader,SimpleUI serverUI) {
		int len ;
		String temp;
		char[] chars = new char[1024];
		// TODO Auto-generated method stub
		System.out.println("子线程开始工作");
		while (true) {
			try {
				System.out.println("开始从客户端读取数据——>");
				while ((len = ((Reader) reader).read(chars)) != -1) {
					temp = new String(chars, 0, len);
					System.out.println("来自客户端" + socket.getPort() + "的消息:" + temp);
					serverUI.showMsg("来自客户端" + socket.getPort() + "的消息:" + temp);
					serverUI.showMsg("客户端" + socket.getPort() + "的说:" + temp);
				}
				if (socket.getKeepAlive() == false) {
					((Reader) reader).close();
					// temp = "线程"+this.getId()+"——>关闭";
					// System.out.println(temp);
					temp = "客户端" + socket.getPort() + ":退出";
					serverUI.showMsg(temp);
					socket.close();
					Thread.currentThread().stop();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				try {
					((Reader) reader).close();
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}
}
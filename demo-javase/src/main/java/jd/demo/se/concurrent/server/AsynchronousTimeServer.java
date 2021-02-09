package jd.demo.se.concurrent.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;


public class AsynchronousTimeServer extends TimeServer {

	private AsynchronousServerSocketChannel channel ;
	private CountDownLatch latch ;
	public AsynchronousTimeServer(int port){
		try {
			channel = AsynchronousServerSocketChannel.open();
			channel.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		latch = new CountDownLatch(1);
		
		channel.accept(this,new CompletionHandler<AsynchronousSocketChannel,AsynchronousTimeServer>(){

			@Override
			public void completed(AsynchronousSocketChannel result,
					AsynchronousTimeServer attachment) {
				channel.accept(attachment,this);
				
				ByteBuffer buf = ByteBuffer.allocate(1024);
				result.read(buf, buf,new ReadCompletionHandler(result));
			}

			@Override
			public void failed(Throwable exc, AsynchronousTimeServer attachment) {
				exc.printStackTrace();
				attachment.latch.countDown();
			}
		});
		
		
		try {
			latch.await();
			//Thread.currentThread().wait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{

		private AsynchronousSocketChannel sc ;
		
		public ReadCompletionHandler(AsynchronousSocketChannel sc) {
			this.sc = sc ;
		}	

		@Override
		public void completed(Integer result, ByteBuffer rbuf) {
			rbuf.flip();
			byte[] rbs = new byte[rbuf.remaining()];
			rbuf.get(rbs);
			
			String response = response(rbs);
			byte[] wbs = response.getBytes();
			ByteBuffer wbuf = ByteBuffer.allocate(wbs.length);
			wbuf.put(wbs);
			wbuf.flip();
			sc.write(wbuf, wbuf, new CompletionHandler<Integer,ByteBuffer>(){

				@Override
				public void completed(Integer result, ByteBuffer buf) {
					if(buf.hasRemaining()){
						sc.write(buf);
					}
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					exc.printStackTrace();
					try {
						sc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			exc.printStackTrace();
			try {
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new AsynchronousTimeServer(TimeServer.DEFAULT_PORT).run();

	}

}

package jd.demo.netty.start;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;

import static jd.demo.netty.start.NettyTimeServer.PORT;


public class NettyTimeClient {

    private static class TimeClientHandler extends ChannelInboundHandlerAdapter {


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            int cnt = 100 ;
            while(cnt-->0){
                ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer("QUERY".getBytes()));
                //future.addListener(ChannelFutureListener.CLOSE);
                Thread.sleep(1000);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            try{
                String str = ((ByteBuf) msg).toString(Charset.forName("utf-8"));
                System.out.println("read data: " + str);
                ctx.close();
            }finally {
                byteBuf.release();
            }
        }

       /* @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }*/

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.fireExceptionCaught(cause);
            cause.printStackTrace();
            ctx.close();
        }
    }
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture future = bootstrap.connect(InetAddress.getLocalHost(),PORT).sync();
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
}

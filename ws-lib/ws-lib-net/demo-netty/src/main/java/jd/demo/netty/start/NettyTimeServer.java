package jd.demo.netty.start;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;

import java.util.Date;


public class NettyTimeServer {

    protected final static int PORT = 81 ;
    private static class TimeServerHandler extends ChannelInboundHandlerAdapter {

        /*@Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer("Hello".getBytes()));
            future.addListener(ChannelFutureListener.CLOSE);
        }*/

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            try{
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                String str = new String(bytes, "UTF-8");
                System.out.println("read data: " + str);
                String responseMsg = "OK | " + new Date() ;
                ByteBuf responseBuf = Unpooled.copiedBuffer(responseMsg.getBytes());
                ctx.write(responseBuf);
            }finally {
                byteBuf.release();
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.fireExceptionCaught(cause);
            cause.printStackTrace();
            ctx.close();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeServerHandler());
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture future = bootstrap.bind(PORT).sync();
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

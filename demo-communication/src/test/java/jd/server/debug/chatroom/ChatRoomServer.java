package jd.server.debug.chatroom;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jd.server.debug.DebugServer;

public class ChatRoomServer extends JFrame implements ActionListener{

    private Map<Integer, Socket> clients = new HashMap<Integer, Socket>();
    private JTextArea msg = new JTextArea("消息接收器\r\n");
    private JButton msgSend = new JButton("发送群消息");
    public ChatRoomServer() {
        // TODO Auto-generated constructor stub
        super("Server");
        this.setSize(500, 650);
        this.setLayout(new FlowLayout());
        msgSend.addActionListener(this);
        msgSend.setActionCommand("sendMsg");
        msg.setAutoscrolls(true);
        msg.setColumns(40);
        msg.setRows(30);
//      msg.setPreferredSize(new Dimension(this.getWidth(), 300));

        JScrollPane spanel = new JScrollPane(msg);
        this.add(spanel);
        this.add(msgSend);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args){
        new ChatRoomServer().listenClient();
    }

    public void listenClient(){
        int port = DebugServer.PORT;
        String temp = "";
        // 定义一个ServerSocket监听在端口8899上
        try {
            ServerSocket server = new ServerSocket(port);
            // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            while (true) {
                System.out.println("服务器端正在监听");
                Socket socket = server.accept();
                clients.put(socket.getPort(), socket);
                temp = "客户端"+socket.getPort()+":连接";
                this.apppendMsg(temp);
                new ChatRoomServerThread(socket, this).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void apppendMsg(String msg){

        this.msg.append(msg+"\r\n");
    }

    public void sendMsgToAll(Socket fromSocket, String msg) {
        Set<Integer> keset = this.clients.keySet();
        java.util.Iterator<Integer> iter = keset.iterator();
        while(iter.hasNext()){
            int key = iter.next();
            Socket socket = clients.get(key);
            if(socket != fromSocket){
                try {
                    if(socket.isClosed() == false){
                        if(socket.isOutputShutdown() == false){

                            Writer writer = new OutputStreamWriter(
                                    socket.getOutputStream());
                            writer.write(msg);
                            writer.flush();
                        }

                    }
                } catch (SocketException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String temp = "";
        if("sendMsg".equals(e.getActionCommand())){
            System.out.println("开始向客户端群发消息");
            Set<Integer> keset = this.clients.keySet();
            java.util.Iterator<Integer> iter = keset.iterator();
            while(iter.hasNext()){
                int key = iter.next();
                Socket socket = clients.get(key);
                try {
                    if(socket.isClosed() == false){
                        if(socket.isOutputShutdown() == false){
                            temp = "向客户端"+socket.getPort()+"发送消息";
                            System.out.println(temp);
                            Writer writer = new OutputStreamWriter(
                                    socket.getOutputStream());
                            this.apppendMsg(temp);
                            writer.write("来自服务器的问候");
                            writer.flush();
                        }

                    }
                } catch (SocketException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

}


package jd.server.debug;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import org.apache.commons.io.IOUtils;

public class SimpleUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5666699484655083242L;
	private Socket socket ;
	private InputStream is ;
	private OutputStream os ;
	
	//private JButton readBut ;
	private JButton sendBut ;
	private JTextArea ra,wa ;
	
	
	public SimpleUI(String title) {
		super(title);
		//this.readBut = new JButton("Read");
		//this.readBut.addActionListener(e->readSocket());
		this.sendBut = new JButton("Send");
		this.sendBut.addActionListener(e->writeSocket());
		ra = new JTextArea(10,10);
		wa = new JTextArea(10,10);
		this.setLayout(new BorderLayout());
		JSplitPane p1 = new JSplitPane();
		p1.add(new JScrollPane(ra),JSplitPane.LEFT);
		p1.add(new JScrollPane(wa),JSplitPane.RIGHT);
		p1.setDividerLocation(0.5);
		//Panel p2 = new Panel();
		//p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS )); 
		//p2.add(readBut);
		//p2.add(sendBut);
		//readBut.setSize(100, 30);
		//sendBut.setSize(100, 30);
		p1.setDividerSize(1);
		this.add(p1,BorderLayout.CENTER);
		this.add(sendBut,BorderLayout.SOUTH);
		this.setSize(1000, 600);
		this.setLocation(100, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void showMsg(String msg) {
		this.ra.append(msg);
		this.ra.append("\n");
	}
	
	
	public void writeSocket() {
		String content = this.wa.getText();
		try {
			//os = this.socket.getOutputStream();
			System.out.println(this.getTitle() + " is writing:" + content);
			this.os.write(content.getBytes());
			//this.socket.shutdownOutput();
			this.os.flush();
			System.out.println(this.getTitle() + " write successful");
		} catch (IOException e) {
			System.err.println(this.getTitle() + " write error");
			e.printStackTrace();
		}
	}
	
	public void bind(Socket s) throws IOException {
		this.socket = s ;
		os = s.getOutputStream();
		//is = s.getInputStream();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

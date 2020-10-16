package jd.demo.se.ui;

import javax.swing.JFrame;

public class DemoFrameExit {

	public static void main(String[] args) {
		JFrame f = new JFrame("frame 1");
		f.setSize(100,200);
		f.setLocation(100, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		JFrame f2 = new JFrame("frame 2");
		f2.setSize(100,200);
		f2.setLocation(100, 200);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f2.setVisible(true);
		
		// when any frame is close, the jvm exit
	}

}

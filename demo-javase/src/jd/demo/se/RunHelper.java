package jd.demo.se;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jd.util.ui.swing.ComponentUtil;

public class RunHelper extends JFrame {

	JTextField tfPkg = new JTextField(10);
	JTextField tfClassName = new JTextField(20);
	JTextField tfMethod = new JTextField(20);
	public RunHelper() {
		super("java se demo ");
		JPanel top = new JPanel();
		top.add(tfPkg);
		top.add(tfClassName);
		top.add(tfMethod);
		JButton searchButton = new JButton("search");
		searchButton.addActionListener(this::search);
		top.add(searchButton);
		JPanel main = new JPanel();
		JPanel mainLeft = new JPanel();
		JPanel mainBody = new JPanel();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(top,BorderLayout.NORTH);
		this.getContentPane().add(mainLeft,BorderLayout.WEST);
		this.getContentPane().add(mainBody, BorderLayout.CENTER);
		mainBody.add(new JLabel("hello"));
		ComponentUtil.show(this, 500, 400,null,false,true);
		ComponentUtil.setFullScreen(this, true);
		ComponentUtil.setFullScreen(null);
		
		this.setVisible(false);
		JFrame normalFrame = new JFrame("demo");
		normalFrame.setContentPane(this.getContentPane());
		ComponentUtil.show(normalFrame, 500, 400);
	}
	
	void search(ActionEvent e) {
		String pkg = tfPkg.getText();
		String classname = this.tfClassName.getText();
		String method = this.tfMethod.getText();
	}
	
	public static void main(String[] args) {
		new RunHelper();
	}

}

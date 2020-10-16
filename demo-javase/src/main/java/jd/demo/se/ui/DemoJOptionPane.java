package jd.demo.se.ui;

import javax.swing.JOptionPane;

public class DemoJOptionPane {

	public static void main(String[] args) {
		int choice = JOptionPane.showConfirmDialog(null, "OK?","", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) ;
		System.out.println("your choice is " + choice); // yes: 0 no: 1 close: -1
	}

}

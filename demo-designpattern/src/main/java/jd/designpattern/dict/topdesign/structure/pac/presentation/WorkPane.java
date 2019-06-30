package jd.designpattern.dict.topdesign.structure.pac.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jd.designpattern.dict.topdesign.structure.pac.controller.WorkController;

public class WorkPane extends AbstractPresentation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1634125854605378749L;
	private final WorkController wc = new WorkController();
	private final JLabel hint = new JLabel();
	private final JTextArea ta = new JTextArea();
	
	public WorkPane(){
		JButton openBtn = new JButton("Open");
		JButton saveBtn = new JButton("Save");
		JPanel btnPane = new JPanel();
		btnPane.add(openBtn);
		btnPane.add(saveBtn);
		
		//JScrollPane jsPanel = new JScrollPane();
		//jsPanel.add(ta);
		this.setLayout(new BorderLayout());
		this.add(hint,BorderLayout.NORTH);
		this.add(ta,BorderLayout.CENTER);
		this.add(btnPane,BorderLayout.SOUTH);
		
		// save into file
		saveBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				save();
			}
		});
		
		// read from file
		openBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				String text = wc.readFromFile();
				if(text != null){
					ta.setText(text);
				}
			}
		});
	}
	
	public void save(){
		try{
			wc.saveIntoFile(ta.getText());
			hint.setText("save file successfully");
			hint.setForeground(Color.green);
		}catch(Exception e){
			hint.setText("save file failed:"+e.getMessage());
			hint.setForeground(Color.red);
		}
	}
	
}

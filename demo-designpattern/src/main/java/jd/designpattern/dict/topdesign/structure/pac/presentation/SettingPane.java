package jd.designpattern.dict.topdesign.structure.pac.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

import jd.designpattern.dict.topdesign.structure.pac.controller.SettingController;

public class SettingPane extends AbstractPresentation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3288092370695931171L;
	private final SettingController sc;
	final JTextField tf = new JTextField(20);
	
	public SettingPane(){
		sc = new SettingController(this);
		init();
	}
	
	public void init(){
		// init
		
		JButton openBn = new JButton("Open");
		JButton saveBn = new JButton("Save");
		
		this.add(tf);
		this.add(openBn);
		this.add(saveBn);
		openBn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				tf.setText(sc.openFile());
			}
		});
		saveBn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				saveFilePath(tf.getText());
			}
		});
	}
	
	@Override
	public void notifyActive() {
		loadFilePath();
	}
	
	public void loadFilePath(){
		File f = sc.getFilepath();
		if(f != null){
			tf.setText(f.getAbsolutePath());
		}
	}
	
	public boolean saveFilePath(String path){
		if(path != null && !"".equals(path = path.trim())){
			File f = new File(path);
			if(!f.exists()){
				if(!f.getParentFile().exists()){
					f.getParentFile().mkdirs();
				}
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			sc.setFilepath(f);
		}
		return false;
	}
}

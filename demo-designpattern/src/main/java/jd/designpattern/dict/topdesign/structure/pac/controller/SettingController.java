package jd.designpattern.dict.topdesign.structure.pac.controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import jd.designpattern.common.dto.CmdDataDTO;

public class SettingController extends AbstractController {

	private File filepath ;
	private JPanel settingPanel ;
	public SettingController(JPanel settingPanel){
		this.settingPanel = settingPanel;
		super.registerCommand(new PacCommand<Object,File>(){

			@Override
			public CMDS getCmd() {
				return CMDS.CMD_GET_FILEPATH;
			}
			
			@Override
			public void execute(CmdDataDTO<CMDS, Object[],File> dfo,
					Object... args) {
				SettingController.this.setResult(dfo,getFilepath());
			}
		}).registerCommand(new PacCommand<Object,File>(){

			@Override
			public CMDS getCmd() {
				return CMDS.CMD_SET_FILEPATH;
			}
			
			@Override
			public void execute(CmdDataDTO<CMDS, Object[],File> dfo,
					Object... args) {
				String filepath = openFile();
				if(filepath !=null){
					SettingController.this.setResult(dfo,new File(filepath));
				}
			}
		});
	}
	
	
	public String openFile(){
		String filepath = null;
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(settingPanel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		   File file = chooser.getSelectedFile();
		   setFilepath(file);
		   filepath = chooser.getSelectedFile().getAbsolutePath();
		   System.out.println("You chose to open this file: " + filepath);
		}
		return filepath;
	}
	
	public File getFilepath() {
		return filepath;
	}

	public void setFilepath(File filepath) {
		this.filepath = filepath;
	}
	
}

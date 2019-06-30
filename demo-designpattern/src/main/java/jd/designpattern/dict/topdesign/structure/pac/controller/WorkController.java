package jd.designpattern.dict.topdesign.structure.pac.controller;

import java.io.File;

import jd.util.io.IOUt;

public class WorkController extends AbstractController {

	public void saveIntoFile(String text){
		File file = getFile();
		if(file == null){
			file = super.handle(CMDS.CMD_SET_FILEPATH);
			if(file == null){
				throw new RuntimeException("please specify the file for saving the text");
			}
		}else{
			IOUt.saveToFile(file,text);
		}
	}
	
	public String readFromFile(){
		File file = getFile();
		if(file != null){
			return new String(IOUt.toByteArray(file));
		}
		return null;
	}
	
	public File getFile(){
		return this.handle(CMDS.CMD_GET_FILEPATH);
	}
}

package jd.designpattern.dict.topdesign.structure.pac.controller;

import java.util.ArrayList;
import java.util.List;

import jd.designpattern.common.dto.CmdDataDTO;



interface Controller{
	<D,R> void handle(CmdDataDTO<CMDS,D[],R> dfo);
}

public abstract class AbstractController implements Controller {
	
	private static List<PacCommand> cmdList = new ArrayList<>();
	
	public <D,R> void handle(CmdDataDTO<CMDS,D[],R> dfo){
		CMDS cmd = dfo.getCmd();
		for(PacCommand pc: cmdList){
			if(pc.getCmd().equals(cmd)){
				pc.execute(dfo,dfo.getData());
				break;
			}
		}
	}
	

	public <D,R> R handle(CMDS cmd,D ... data){
		CmdDataDTO<CMDS,D[],R> dfo = new CmdDataDTO<>(cmd,data);
		handle(dfo);
		return dfo.getResult();
	}
	
	public <R> void setResult(CmdDataDTO<CMDS,?,R> dfo,R result){
		dfo.setResult(result);
	}
	
	public AbstractController registerCommand(PacCommand cmd){
		if(!cmdList.contains(cmd)){
			cmdList.add(cmd);
		}
		return this;
	}
}

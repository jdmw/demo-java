package jd.designpattern.dict.topdesign.structure.pac.controller;

import jd.designpattern.common.dto.CmdDataDTO;


enum CMDS{
	CMD_OPEN_FILE,
	CMD_SAVE_FILE,
	CMD_GET_FILEPATH,
	CMD_SET_FILEPATH
};

public interface PacCommand<D,R>  {
	public CMDS getCmd() ;
	public void execute(CmdDataDTO<CMDS,D[],R> dfo,D ... args);
}
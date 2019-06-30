package jd.designpattern.common.dto;

public class CmdDataDTO<C,D,R> {

	private C cmd ;
	private D data ;
	private R result ;
	
	public CmdDataDTO(C cmd, D data) {
		this.cmd = cmd;
		this.data = data;
	}
	public C getCmd() {
		return cmd;
	}
	public D getData() {
		return data;
	}
	public void setCmd(C cmd) {
		this.cmd = cmd;
	}
	public void setData(D data) {
		this.data = data;
	}
	public R getResult() {
		return result;
	}
	public void setResult(R result) {
		this.result = result;
	}
	
}

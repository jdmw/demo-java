package jd.designpattern.common.dto;

public class StateDataDTO<S,D,R> {

	private S state ;
	private D data;
	private R result ;
	
	public StateDataDTO(D data) {
		this.data = data;
	}

	public StateDataDTO(S state, D data) {
		this.state = state;
		this.data = data;
	}
	
	public S getState() {
		return state;
	}
	public D getData() {
		return data;
	}
	public R getResult() {
		return result;
	}
	public void setState(S state) {
		this.state = state;
	}
	public void setData(D data) {
		this.data = data;
	}
	public void setResult(R result) {
		this.result = result;
	}
	
	
}

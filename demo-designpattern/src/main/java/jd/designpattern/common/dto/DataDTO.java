package jd.designpattern.common.dto;
public class DataDTO<D,R> {

	private D data;
	private R result ;
	
	public DataDTO(D data, R result) {
		this.data = data;
		this.result = result;
	}

	public DataDTO(D data) {
		this.data = data;
	}

	public D getData() {
		return data;
	}
	public R getResult() {
		return result;
	}
	public void setData(D data) {
		this.data = data;
	}
	public void setResult(R result) {
		this.result = result;
	}
	
	
}

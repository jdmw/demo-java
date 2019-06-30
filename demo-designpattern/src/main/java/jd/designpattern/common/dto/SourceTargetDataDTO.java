package jd.designpattern.common.dto;

public class SourceTargetDataDTO<S,T,D> {
	private S source;
	private T target;
	private D data;
	public SourceTargetDataDTO( D data) {
		this.data = data;
	}
	
	public SourceTargetDataDTO(S source, D data) {
		this.source = source;
		this.data = data;
	}
	
	public S getSource() {
		return source;
	}
	public T getTarget() {
		return target;
	}
	public D getData() {
		return data;
	}
	public void setSource(S source) {
		this.source = source;
	}
	public void setTarget(T target) {
		this.target = target;
	}
	public void setData(D data) {
		this.data = data;
	}
	
}

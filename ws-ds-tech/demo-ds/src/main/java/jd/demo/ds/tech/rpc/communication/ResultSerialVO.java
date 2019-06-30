package jd.demo.ds.tech.rpc.communication;

public class ResultSerialVO<T> {

	private boolean hasResult ;
	private boolean hasError ;
	private T result ;
	private Throwable throwable ;
	
	public boolean isHasResult() {
		return hasResult;
	}
	public void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}

package jd.designpattern.concurrent.guardedsuspension;

public class Request {

	private final String content ;

	public Request(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public String toString() {
		return "[" + content + "]" ;
	}
}

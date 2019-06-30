package jd.demo.se.fundamentals.languagebasic.controlflow;

public class TestReturn {

	{
		/*// can't use in a instance initializer
		return ;*/
	}
	
	static {
		/*// can't use in static initializer
		return ;*/
	}
	public TestReturn() {
		// can use here
		return ;
	}
}

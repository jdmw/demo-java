package jd.demo.se.fundamentals.object_oriented.classobject;

class DemoConstructorBase {
	/* subclass can't access if using private
	 * private DemoConstructorBase() {*/
	DemoConstructorBase(){
		System.out.println(this.getClass().getSimpleName() + ":DemoConstructorBase");
	}
	public void fun() {
		
	}
}

public class TestConstructor extends DemoConstructorBase{
	public TestConstructor() {
		System.out.println(this.getClass().getSimpleName() + ":TestConstructor");
	}
	public static void main(String[] args){
		new TestConstructor();
	}
}

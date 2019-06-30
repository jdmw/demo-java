package jd.demo.se.fundamentals.object_oriented.classobject;


class ClassA {
    public void methodOne(int i) {
    }
    public void methodTwo(int i) {
    	System.out.println(this.getClass().getSimpleName() + ":" + i);
    }
    public static void methodThree(int i) {
    }
    public static void methodFour(int i) {
    	System.out.println("ClassA:" + i);
    }
}

class ClassB extends ClassA {
   /* compile-time error:
    * public static void methodOne(int i) {
    }*/
	@Override
    public void methodTwo(int i) {
    	System.out.println(this.getClass().getSimpleName() + ":" + i);
    }
    /*
     * compile-time error:
     * public void methodThree(int i) {
    }*/
	
    public static void methodFour(int i) { 
    	System.out.println("ClassB:" + i);
    }
}


public class TestOverride {

	public static void main(String[] args) {
		ClassB cb = new ClassB();
		cb.methodTwo(2);
		cb.methodFour(4);
		ClassB.methodFour(4);
		ClassA.methodFour(4);
	}

}

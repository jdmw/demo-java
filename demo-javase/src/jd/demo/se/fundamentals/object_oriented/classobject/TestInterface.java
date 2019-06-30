package jd.demo.se.fundamentals.object_oriented.classobject;

interface DeclareInterface {

	int NUM = 100 ; 		// final static field 
	enum SEX {MALE,FEMALE}; // final static field
	
	String getClassName();  // abstract method
	
	
	default boolean isGreater(int n) {
		return n > NUM ;
	}
	default boolean isLitter(int n) {
		return n < NUM ;
	}
	public static int getNum() { // static method
		return NUM ;
	}
	public class Day {      // static nested class
		public Day() {
			System.out.println(this.getClass().getName());
		}
	}
}




public class TestInterface implements DeclareInterface{

	@Override
	public String getClassName() {
		return this.getClass().getName();
	}
	
	public static void main(String[] args){
		TestInterface ti = new TestInterface();
		System.out.println(ti.NUM);
		System.out.println(DeclareInterface.getNum());
		System.out.println(ti.isGreater(101));
		new DeclareInterface.Day();
		DeclareInterface.SEX sex = SEX.MALE ;
		// ti.NUM = 200 ;
		
	}
	
}
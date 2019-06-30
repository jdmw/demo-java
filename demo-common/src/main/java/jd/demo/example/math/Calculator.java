package jd.demo.example.math;

public class Calculator implements ICalculator{

	public int add(int a,int b) {
		return a + b;
	}
	
	public int substract(int a,int b) {
		return a - b;
	}
	
	public long multiply(int a,int b) {
		return (long)a * (long)b ;
	}
	
	public int divide(int a,int b) {
		return a / b ;
	}
	
}

package jd.demo.se.fundamentals.object_oriented.commonclasses;

public class TestNumberClass {

	public static void main(String[] args) {
		Float f = Float.valueOf(0.1f);
		System.out.println(f.equals(0.1)); // false
		System.out.println(f.equals(0.1f)); // true
	}

}

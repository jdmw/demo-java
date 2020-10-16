package jd.demo.se.fundamentals.object_oriented.commonclasses;

public class TestStringBuilder {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder("Greetings");
		// adds 9 character string at beginning
		sb.replace(0, 1, "g");
		System.out.println(sb);

	}

}

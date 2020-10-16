package jd.demo.se.fundamentals.languagebasic.controlflow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.LOCAL_VARIABLE})
 @Retention(value=RetentionPolicy.SOURCE)
@interface DemoAn{
	
}
public class DemoFor {

	public static void main(String[] args) {
		int[] arr = {1,2} ;
		for(@DemoAn final int j : arr) {
			System.out.println(j);
		}
		
		
		
		for(double j : arr) {
			System.out.println(j);
		}
		
		/* compile-time error
		 * double[] darr = {1.1,2.2,3.3} ;
		for(int j : darr) {
			System.out.println(j);
		}*/
		
		
		// reason:
		int n = 1 ;
		double d = 0.1 ;
		// compile-time error: n = d ;
		d = n ;
	}

}

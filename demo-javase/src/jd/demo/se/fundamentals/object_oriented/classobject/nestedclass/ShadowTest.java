package jd.demo.se.fundamentals.object_oriented.classobject.nestedclass;

public class ShadowTest {

    public int x = 0;
    public int y = 0 ;
    public int z = 0 ;

    class FirstLevel {
    	
        public int x = 1 ;
        public int y = 1 ;

        void methodInFirstLevel(int y) {
            System.out.println("x = " + x); // print: x = 1
            System.out.println("y = " + y); // print: y = 23
            System.out.println("z = " + z); // print: z = 0
            System.out.println("this : " + this.getClass().getSimpleName());// print: this : FirstLevel
            System.out.println("ShadowTest.this:  " + ShadowTest.this.getClass().getSimpleName());//print: ShadowTest.this:  ShadowTest
        }
    }

    public static void main(String... args) {
        new ShadowTest().new FirstLevel().methodInFirstLevel(23);
    }
}

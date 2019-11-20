package jd.demo.se9.lang;

import java.io.*;

/**
 * ref https://docs.oracle.com/javase/9/language/toc.htm
 */
public class DemoJava9LanguageChange {

    //If you use the underscore character ("_") an identifier, your source code can no longer be compiled.
    //var _ = 1 ;

    // @SafeVarargs annotation is allowed on private instance methods.
    @SafeVarargs
    private void demoSafeVarargs(String ... str){}

    // Private interface methods are supported.
    private interface I {
        //  allows nonabstract methods of an interface to share code between them.
        private void fun(){}
        default void  f1(){
            fun();
        }
        default void  f2(){
            fun();
        }
    }
    private void demoTryWithResources() throws IOException {
        File file = new File("");
        // In Java SE 9, you don’t need to declare inputStream
        InputStream inputStream = new FileInputStream(file);
        try(inputStream){
            System.out.println(inputStream);
        }


    }
    public static void main(String[] args) {
        System.out.println("");
    }
}

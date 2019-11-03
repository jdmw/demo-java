package jd.demo.se9.lang;

import java.io.*;

/**
 * ref https://docs.oracle.com/javase/9/language/toc.htm
 */
public class DemoJava9LanguageChange {

    //If you use the underscore character ("_") an identifier, your source code can no longer be compiled.
    //var _ = 1 ;

    @SafeVarargs
    private DemoJava9LanguageChange(String ... str){}

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

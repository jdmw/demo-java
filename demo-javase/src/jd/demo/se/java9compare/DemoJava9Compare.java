package jd.demo.se.java9compare;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DemoJava9Compare {

    // @SafeVarargs
    // @SafeVarargs annotation is not allowed on private instance methods.
    private void funDemoJava9Compare(String ... str){}

    private void demoTryWithResources() throws IOException {
        File file = new File("");
        ;
        InputStream inputStream = new FileInputStream(file);
        //try(new FileInputStream(file)){
        // you have to declare inputStream1
        try(InputStream inputStream1 = inputStream){
            System.out.println(inputStream);
        }

        int _ = 1 ;
    }

    public static void main(String[] args) {
        System.out.println("");
    }
}

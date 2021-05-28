package jd.jvmexample.classfile.instrution;

/**
 * Created by huangxia on 2008/4/8.
 */
public class DemoArray {

    public static int arr(){
        int[] a = new int[0];
        Integer[] b = new Integer[1];
        return a.length + b.length;
    }
}

package jd.jvmexample.instruction.invoke;

/**
 * Created by huangxia on 2008/3/23.
 */
public class EInstInvoke {

    public int f1(){
        int a = 1 ;
        return  a + f2(a,true);
    }

    public int f2(int a,boolean b){
        return b ? a : -a ;
    }


}

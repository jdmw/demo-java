package jd.demo.se.fundamentals.languagebasic.type_variable;


public class DemoTypes {
    public static void main(String[] args){
        int [] a = new int[]{1};
        args[0].intern();
        System.out.println(a.equals(new int[]{1}));
    }
}

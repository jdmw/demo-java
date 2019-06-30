package jd.demo.se.collections.map;

import org.apache.poi.ddf.EscherColorRef;

import java.util.HashMap;
import java.util.Map;

public class DemoMap {

    public static void main(String[] args){
        Map<String,String> map = new HashMap<>();
        map.putIfAbsent("a","1");
        map.putIfAbsent("b","2");
        map.putIfAbsent("c","3");
        map.putIfAbsent("a","3");

        Map<String,String> map1 = new HashMap<>();
        map1.putIfAbsent("t","t");
        map1.merge("t","-001",String::concat);
        System.out.println(map1);


        map1.computeIfAbsent("v ",String::trim);
        map1.computeIfPresent("t",(k,v)-> k+":"+v );

        System.out.println(map1);
    }
}

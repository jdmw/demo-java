package jd.demo.se.concurrent.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortList {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(2,1,4,-1));
        list.sort((a,b)->a-b);
        System.out.println(Arrays.toString(list.toArray()));
    }
}

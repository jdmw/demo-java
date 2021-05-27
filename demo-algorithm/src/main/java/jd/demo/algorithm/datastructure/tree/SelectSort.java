package jd.demo.algorithm.datastructure.tree;


import jd.util.Assert;
import jd.util.LoopUt;
import jd.util.lang.math.RandomUt;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SelectSort {

    public static <T extends Comparable<T>> void sort(@NonNull ArrayList<T> list){
        if( list.size() == 1) return;

        List<T> list1 = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if( list1.size() == 1) return;
        Comparable[] arr = list1.toArray(new Comparable[list.size()]);
        //System.out.println(list1);
        sort(arr);
        //System.out.println(Arrays.asList(arr));
        list.clear();
        list.add((T)arr[0]);
        for (int i = 1; i < arr.length; i++) {
            Assert.isTrue(arr[i-1].compareTo(arr[i]) <=0 ,"sort error");
            list.add((T)arr[i]);
        }
    }

    public static <T extends Comparable<T>> void sort(Comparable[] arr){
        if (arr.length < 2) return ;
        int end = arr.length;

        int min ;
        for (int start=0;start<end-1;start++){
            min = start+1 ;
            if(start>0 && arr[start].compareTo(arr[start-1]) == 0 ){
                continue;
            }
            for (int i=start+1;i<end;i++){
                if(arr[i].compareTo(arr[min]) < 0){
                    min = i ;
                }
            }
            if(arr[start].compareTo(arr[min]) > 0){
                Comparable t = arr[start];
                arr[start] = arr[min] ;
                arr[min] = t ;
            }
            /*if(start > 0){
                Assert.isTrue(arr[start].compareTo(arr[start-1]) >= 0 ,"this node should >= previous node");
            }*/

        }
    }

    public static void main(String[] args){
        ArrayList<Integer> list = new ArrayList<>();
        LoopUt.rangeFrom0To(1000, i-> list.add(RandomUt.random(1,10000)));
        sort(list);
        System.out.println(list);
    }
}

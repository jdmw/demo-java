package jd.demo.algorithm.algorithm.sort;


import jd.util.LoopUt;
import jd.util.lang.math.RandomUt;

import java.util.ArrayList;

public class SelectionSort implements ISort {

    public  <T extends Comparable<T>> void sort(Comparable[] arr){
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
        LoopUt.rangeFrom0To(5000, i-> list.add(RandomUt.random(1,10000)));
        new SelectionSort().sort(list);
        System.out.println(list);
    }
}

package jd.demo.algorithm.algorithm.sort;


import jd.util.LoopUt;
import jd.util.lang.math.RandomUt;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * https://www.bilibili.com/video/BV1NQ4y1f7TH?p=10
 */
public class InsertionSort implements ISort {

    public  <T extends Comparable<T>> void sort(Comparable[] arr){
        if (arr.length < 2) return ;
        int j = 0 ;
        Comparable startVal;
        for (int start=1;start<arr.length;start++){
            startVal = arr[start];
            j = start ;
            while (j-->0){
                if(lt(startVal,arr[j])){
                    swap(j+1,j,arr);
                }else{
                    break;
                }
            }
        }
    }

    public static void main(String[] args){
        new InsertionSort().sort(new ArrayList<Integer>(Arrays.asList(9,6,1,3,5)));
        ArrayList<Integer> list = new ArrayList<>();
        LoopUt.rangeFrom0To(5000, i-> list.add(RandomUt.random(1,10000)));
        new InsertionSort().sort(list);
        System.out.println(list);
    }
}

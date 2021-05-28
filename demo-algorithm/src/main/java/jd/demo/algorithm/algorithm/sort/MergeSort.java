package jd.demo.algorithm.algorithm.sort;

import jd.util.LoopUt;
import jd.util.lang.math.RandomUt;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by huangxia on 2008/5/28.
 */
public class MergeSort implements ISort {
    @Override
    public <T extends Comparable<T>> void sort(Comparable[] arr) {
        Comparable[] mergeArr = new Comparable[arr.length];
        System.arraycopy(arr,0,mergeArr,0,arr.length);
        /*Comparable[][] arrays = */sort(arr, 0, arr.length, mergeArr);
        /*if(mergeArr == arrays[0]){
            System.arraycopy(mergeArr,0,arr,0,arr.length);
        }*/
    }

    public <T extends Comparable<T>> void sort(Comparable[] arr,int begin,int end,Comparable[] mergeArr) {
        if(end - begin <= 2){
            if(end > begin && gt(arr[begin],arr[end-1])){
                swap(begin,end-1,arr);
            }
            return;
        }
        // divide
        int mid = (begin + end) / 2 ;
        sort(arr,begin,mid,mergeArr) ;
        sort(arr,mid,end,mergeArr)  ;
        //System.out.printf("left[%d~%d):%s,right[%d~%d):%s\n",begin,mid,Arrays.asList(arr).subList(begin,mid).toString(), mid,end,Arrays.asList(arr).subList(mid,end).toString());


        merge(arr,begin,end,mid,mergeArr);
        //System.out.printf("merge[%d~%d):%s\n" ,begin,end, Arrays.asList(mergeArr).subList(begin,end));
        System.arraycopy(mergeArr,begin,arr,begin,end-begin);
    }

    private <T extends Comparable<T>> void merge(Comparable[] sourceArr,int begin,int end,int mid,Comparable[] targeArr) {
        int i = begin , j= mid ;
        int targetIdx = begin ;
        for(;targetIdx < end && i < mid && j < end;targetIdx++ ){
            if(le(sourceArr[i],sourceArr[j])){
                targeArr[targetIdx] = sourceArr[i++] ;
            }else{
                targeArr[targetIdx] = sourceArr[j++] ;
            }
        }
        if(targetIdx < end){
            while (i<mid) targeArr[targetIdx ++] = sourceArr[i++];
            while (j<end) targeArr[targetIdx ++] = sourceArr[j++];
        }
    }

    public static void main(String[] args){

        /** sort tree nodes
        Comparable[] arr = {9, 6, 11, 3, 5, 12, 8, 7, 10, 15, 14, 4, 1, 13,2};
        Comparable[] arr1 =new Comparable[5000]; // {9, 6, 11, 3, 5, 12, 8, 7, 10, 15, 14, 4, 1, 13,2};
        Comparable[] expect = new Comparable[arr.length];
        for (int i = 0; i < arr.length; i++) {
            //arr[i] = RandomUt.random(1,10000);
            expect[i] = arr[i];
        }

        Arrays.sort(expect);

        Comparable[] mergeArr = new Comparable[arr.length];
        System.arraycopy(arr,0,mergeArr,0,arr.length);
        new MergeSort().sort(arr,0,arr.length,mergeArr);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(mergeArr));
        System.out.println(Arrays.toString(expect));

        for (int i = 0; i < arr.length; i++) {
            Assert.assertEquals(arr[i],expect[i]);
        }*/

        ArrayList<Integer> list = new ArrayList<>();
        LoopUt.rangeFrom0To(5000, i-> list.add(RandomUt.random(1,10000)));
        new MergeSort().sort(list);
        System.out.println(list);


    }
    public static void test1(){

        /** sort tree nodes */
        Comparable[] arr =/* new Comparable[5000]; // */{9, 6, 11, 3, 5, 12, 8, 7, 10, 15, 14, 4, 1, 13,2};
        Comparable[] expect = new Comparable[arr.length];
        for (int i = 0; i < arr.length; i++) {
            //arr[i] = RandomUt.random(1,10000);
            expect[i] = arr[i];
        }
        Arrays.sort(expect);

        Comparable[] mergeArr = new Comparable[arr.length];
        System.arraycopy(arr,0,mergeArr,0,arr.length);
        new MergeSort().sort(arr,0,arr.length,mergeArr);
        System.out.println(Arrays.toString(arr));

        for (int i = 0; i < arr.length; i++) {
            Assert.assertEquals(arr[i],expect[i]);
        }



    }
}


package jd.demo.algorithm.algorithm.sort;


import jd.util.Assert;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ISort {

    default int[] sort(@NonNull int[] arr){
        if( arr.length == 1) return arr;
        Comparable[] arr1 = new Comparable[arr.length];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = arr[i];
        }
        //System.arraycopy(arr,0,arr1,0,arr.length);
        sort(arr1);
        for (int i = 0; i < arr1.length; i++) {
            arr[i] = (int)arr1[i];
        }
        return arr ;
    }
    default <T extends Comparable<T>> void sort(@NonNull ArrayList<T> list){
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
    interface InnerComparable extends Comparable {
        public int getIndex();
        public Comparable getValue();
    }

    default <T> List<T> sort(@NonNull List<T> list,@NonNull Function<T,Comparable> comparableFunction){
        if( list.size() == 1) return list;
        int idx = 0 ;
        Iterator<T> iterator = list.iterator();
        Comparable[] arr = new Comparable[list.size()];
        while (iterator.hasNext()){
            int idx0 = idx ;
            T thisNode = iterator.next();
            Comparable thisValue = comparableFunction.apply(thisNode);
            arr[idx0] = new InnerComparable() {
                @Override
                public int getIndex() {
                    return idx0;
                }

                @Override
                public Comparable getValue() {
                    return thisValue;
                }

                @Override
                public int compareTo(Object anotherNode) {
                    Comparable anotherValue = ((InnerComparable)anotherNode).getValue();
                    if(thisValue == null){
                        return -1 ;
                    }else if(anotherValue == null){
                        return 1 ;
                    }else{
                        return thisValue.compareTo(anotherValue);
                    }
                }
            };
            idx += 1 ;
        }
        sort(arr);
        List<T> rst = new ArrayList<>();
        for (Comparable comparable : arr) {
            int index = ((InnerComparable) comparable).getIndex();
            rst.add(list.get(index));
        }
        return rst ;
    }

    /**
     * swap array elements with indices
     * @param idx1
     * @param idx2
     * @param arr
     * @param <T>
     */
    default <T> void swap(int idx1,int idx2,T[] arr){
        T t = arr[idx1];
        arr[idx1] = arr[idx2] ;
        arr[idx2] = t ;
    }

    /**
     * check if {@param a} is greater than {@param b}
     * @param a
     * @param b
     * @return
     */
    default boolean lt(Comparable a,Comparable b){
        return a.compareTo(b) < 0 ;
    }
    /**
     * check if {@param a} is greater than or equivalent to {@param b}
     * @param a
     * @param b
     * @return
     */
    default boolean le(Comparable a,Comparable b){
        return a.compareTo(b) <= 0 ;
    }
    /**
     * check if {@param a} is less than {@param b}
     * @param a
     * @param b
     * @return
     */
    public default boolean gt(Comparable a,Comparable b){
        return a.compareTo(b) > 0 ;
    }
    /**
     * check if {@param a} is less than or equivalent to {@param b}
     * @param a
     * @param b
     * @return
     */
    default boolean ge(Comparable a,Comparable b){
        return a.compareTo(b) >= 0 ;
    }

    <T extends Comparable<T>> void sort(Comparable[] arr);

}

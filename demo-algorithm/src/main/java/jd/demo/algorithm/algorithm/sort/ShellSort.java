package jd.demo.algorithm.algorithm.sort;


import jd.demo.algorithm.datastructure.tree.Tree;
import jd.util.LoopUt;
import jd.util.lang.math.RandomUt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://www.bilibili.com/video/BV1NQ4y1f7TH?p=10
 */
public class ShellSort implements ISort {

    public final static int GAP = 16 ;

    public  <T extends Comparable<T>> void sort(Comparable[] arr){
        if(arr.length <= GAP * GAP){
            sort(arr,1);
        }else{
            for (int i=GAP;i>=1;i/=2){
                sort(arr,i);
            }
        }

    }
    public  <T extends Comparable<T>> void sort(Comparable[] arr,int gap){
        if (arr.length < 2) return ;
        //int end = arr.length - arr.length % gap ;
        int i=0,j = 0 ;
        Comparable startVal;
        int end = arr.length - arr.length % gap ;
        int endBound = end - gap ;
        for (int offset=0;offset<gap;offset++){
            for (int start=offset+gap;start<arr.length;start+=gap){
                startVal = arr[start];
                j = start ;
                while ((i=j-gap)>=0){
                    if(lt(startVal,arr[i])){
                        swap(i,j,arr);
                    }else{
                        break;
                    }
                    j = i ;
                }
            }
            //System.out.println(Arrays.toString(arr));
        }
    }

    public static void main(String[] args){

        /** sort tree nodes */
        Comparable[] arr = {9, 6, 11, 3, 5, 12, 8, 7, 10, 15, 14, 4, 1, 13,2};
        Tree<Integer> tree = new Tree();
        Arrays.asList(arr).stream().forEach(e->tree.addChildren((Integer)e));
        List<Tree<Integer>.TreeNode<Integer>> children = tree.getRoot().getChildren();
        List<Tree<Integer>.TreeNode<Integer>> rst = new ShellSort().sort(children, node -> (Comparable) node.getContent());
        System.out.println(rst.stream().map(n->n.getContent()).collect(Collectors.toList()));

        ArrayList<Integer> list = new ArrayList<>();
        LoopUt.rangeFrom0To(5000, i-> list.add(RandomUt.random(1,10000)));
        new ShellSort().sort(list);
        System.out.println(list);


    }
}

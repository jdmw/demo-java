package jd.demo.se.collections.list;


import java.util.LinkedList;
import java.util.List;

public class DemoListAutoSort {

    public static void main(String[] args) {

        // 测试list添加元素后是否自动排序

        // 结论：LinkedList 不自动排序
        List linkedList =new LinkedList<>();
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(1);
        System.out.println(linkedList);
    }
}

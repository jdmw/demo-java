package jd.demo.algorithm.datastructure.tree;

import lombok.Getter;
import lombok.NonNull;

/**
 * Created by huangxia on 2008/6/3.
 */
public class AVLTree<T extends Comparable> extends BinarySortTree<T> {
    private final BinarySortTree.Node root ;

    public AVLTree(T rootContent) {
        this.root = new BinarySortTree.Node(null,rootContent) ;
    }

    public AVLTree() {
        this(null);
    }

    @Getter
    public final class Node extends BinarySortTree.Node{
        int degree ;

        public Node(Node parent, T key){
            super(parent,key);
            if(parent == null){
                degree = 1 ;
            }else{
                degree = parent.degree + 1 ;
            }
        }
    }


    public Node add(@NonNull T key){
        Node newNode = (Node) super.add(key);
        return newNode;
    }
    @Override
    Node newNode(BinarySortTree.Node parent, Object  key){
        return new Node( (Node)parent, (T)key);
    }

    public static void main(String[] args){
        AVLTree<Comparable> tree = new AVLTree<>();
        tree.add(5);
        tree.add(1);
        tree.add(2);
        tree.add(4);
        tree.add(3);

        /*int[] nums = RandomUt.randomIntArray(1, 1000, 100);
        for (int num : nums) {
            tree.add(num);
        }*/
        tree.forEach(System.out::println);
    }
}

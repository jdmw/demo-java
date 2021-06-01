package jd.demo.algorithm.datastructure.tree;

import lombok.Getter;
import lombok.NonNull;

import java.util.Stack;
import java.util.function.Consumer;

/**
 * Created by jdmw on 2008/6/1.
 */
public class AVLTree<T extends Comparable> {

    private final Node root ;

    public AVLTree(T rootContent) {
        this.root = new Node(null,rootContent) ;
    }

    public AVLTree() {
        this(null);
    }

    @Getter
    public final class Node{
        final Node parent ;
        Node left ;
        Node right ;

        T key ;

        public Node(Node parent, T key){
            this.parent = parent ;
            this.key = key ;
        }
        public void add(T content) {

        }
        public Node parent(){
            return parent ;
        }
        public AVLTree tree(){
            return AVLTree.this;
        }
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            if(left != null){
                sb.insert(0,"["+left.key+"]-");
            }
            if(right != null){
                sb.append("["+right.key+"]-");
            }
            return sb.toString();
        }
    }


    public void add(@NonNull T key){
        if(root.key == null){
            root.key = key ;
            return;
        }

        Node node = root ;
        Node next = root ;
        while(next != null){
            node = next ;
            // less than parent node
            if(lt(key,node.key)){
                next = node.left ;
            }else{
                next = node.right;
            }
        }

        if(lt(key,node.key)){
            node.left = new Node(node,key);
        }else{
            node.right = new Node(node,key);
        }
    }

    private enum Direction {UP,DOWN};
    public void forEach(Consumer<T> consumer){
        if(root.key == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        Direction direction = Direction.DOWN ;

        // find minimum
        Node node = root ;
        while (node.left != null ){
            node = node.left;
        }

        consumer.accept(node.key);
        node = node.parent ;
        consumer.accept(node.key);
        if(node.right != null){
            // TODO
        }
    }

    public static boolean lt(@NonNull Comparable a,Comparable b){
        return a.compareTo(b) < 0 ;
    }
    public static void main(String[] args){
        AVLTree<Comparable> tree = new AVLTree<>();
        tree.add(5);
        tree.add(1);
        tree.add(2);
        tree.add(4);
        tree.add(3);

    }
}

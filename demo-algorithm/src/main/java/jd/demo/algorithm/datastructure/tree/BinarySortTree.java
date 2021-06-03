package jd.demo.algorithm.datastructure.tree;

import jd.util.lang.math.RandomUt;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Consumer;


/**
 * Binary Sort Tree
 * https://baike.baidu.com/item/%E4%BA%8C%E5%8F%89%E6%8E%92%E5%BA%8F%E6%A0%91/10905079?fromtitle=%E4%BA%8C%E5%8F%89%E6%9F%A5%E6%89%BE%E6%A0%91&fromid=7077965
 */
public class BinarySortTree<T extends Comparable> {

    private final Node root ;

    public BinarySortTree(T rootContent) {
        this.root = new Node(null,rootContent) ;
    }

    public BinarySortTree() {
        this(null);
    }



    @Getter
    public class Node{
        final Node parent ;
        Node left ;
        Node right ;

        T key ;

        public Node(Node parent, T key){
            this.parent = parent ;
            this.key = key ;
        }
        public Node parent(){
            return parent ;
        }
        public BinarySortTree tree(){
            return BinarySortTree.this;
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

    /*Node newNode(Node parent, T key){
        return new Node( parent, key);
    }*/
    Node newNode(Node parent, Object key){
        return new Node(parent, (T) key);
    }


    public Node add(@NonNull T key){
        if(root.key == null){
            root.key = key ;
            return root;
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

        Node newNode = newNode(node, key);
        if(lt(key,node.key)){
            node.left = newNode;
        }else{
            node.right = newNode;
        }
        return newNode;
    }

    private enum Direction {DOWN,UP};

    public void forEach(Consumer<T> consumer){
        if(root.key == null){
            return;
        }
        Node node = root,previousNode = null ,nextNode = null ;
        Direction status = Direction.DOWN ;
        while (node != null){
            if(status == Direction.DOWN){
                if(node.left != null){
                    nextNode = node.left;
                    status = Direction.DOWN;
                }else{
                    consumer.accept(node.key);
                    if(node.right != null){
                        nextNode = node.right ;
                        status = Direction.DOWN;
                    }else{
                        nextNode = node.parent ;
                        status = Direction.UP ;
                    }
                }
            }else {
                if (previousNode == node.left){
                    consumer.accept(node.key);
                    if(node.right != null){
                        nextNode = node.right ;
                        status = Direction.DOWN;
                    }else{
                        nextNode = node.parent ;
                    }
                }else{
                    nextNode = node.parent ;
                }
            }
            previousNode = node ;
            node = nextNode ;
        }
    }

    public static boolean lt(@NonNull Comparable a,Comparable b){
        return a.compareTo(b) < 0 ;
    }
    public static void main(String[] args){
        BinarySortTree<Comparable> tree = new BinarySortTree<>();
        tree.add(5);
        tree.add(1);
        tree.add(2);
        tree.add(4);
        tree.add(3);

        int[] nums = RandomUt.randomIntArray(1, 1000, 100);
        for (int num : nums) {
            tree.add(num);
        }
        tree.forEach(System.out::println);
    }
}

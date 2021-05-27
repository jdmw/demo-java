package jd.demo.algorithm.datastructure.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Data
@AllArgsConstructor
public class Tree<T>{
    private final TreeNode<T> root ;

    public Tree(T rootContent) {
        this.root = new TreeNode(null,rootContent) ;
    }

    public Tree() {
        this.root = new TreeNode(null,null) ;
    }

    @Getter
    public final class TreeNode<T>{
        final TreeNode<T> parent ;
        T  content ;
        public TreeNode(TreeNode<T> parent, T content){
            this.parent = parent ;
            this.content = content ;
        }
        List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();
        public TreeNode addChildren(T content){
            TreeNode node = new TreeNode(this, content);
            children.add(node);
            return node;
        }
        public void visit(Consumer<T> visitFun ){
            if(content != null){
                visitFun.accept(content);
            }
            if(!children.isEmpty()) {
               children.forEach(node->node.visit(visitFun));
            }
        }
        public TreeNode<T> addSibling(T content){
            if(parent == null){
                throw new  IllegalStateException("root node has no siblings");
            }
            return parent.addChildren(content);
        }
        public TreeNode<T> parent(){
            return parent ;
        }
        public Tree<T> tree(){
            return (Tree<T>) Tree.this;
        }
    }
    public TreeNode addChildren(T content){
        return root.addChildren(content);
    }
    public void visit(Consumer<T> visitFun ){
        root.visit(visitFun);
    }

    public static void main(String[] args){
        Tree<Integer> tree = new Tree<>(10);
        tree.addChildren(11).addSibling(13)
                .addChildren(12);
        tree.visit(System.out::println);
    }
}
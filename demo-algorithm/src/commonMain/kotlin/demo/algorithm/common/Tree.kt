package demo.algorithm.common


class Tree<T>(rootContent : T){

    interface VisitFun<T>{
        fun visit(content:T )
    }
    open class TreeNode<T>(content : T) {
        var content:T = content
        var children = ArrayList<TreeNode<T>>()
        fun addChildren(content: T){
            children.add(TreeNode(content))
        }
        fun visit(visitFun : VisitFun<T>){
            if(content != null){
                visitFun.visit(content)
            }
            if(children.isNotEmpty()) return
            children.forEach { visitFun }
        }
    }
    var root:TreeNode<T>  = TreeNode(rootContent)

    fun visit(visitFun : VisitFun<T>){
        root.visit(visitFun)
    }
}
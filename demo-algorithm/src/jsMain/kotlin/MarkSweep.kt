package demo.algorithm.common

import demo.algorithm.common.Tree

class MarkSweepJs<T>(size: Int) {
    interface VisitFun<T>{
        fun visit(content: MarkSweepTreeNode<T> )
    }
    class MarkSweepTreeNode<T>(ctx : T?) {
        var content:T? = ctx
        var markFlag = false
        var children = ArrayList<MarkSweepTreeNode<T>>()
        fun addChild(content: T): MarkSweepTreeNode<T> {
            val child = MarkSweepTreeNode(content)
            children.add(child)
            return child
        }
        fun mark(){
            if(!markFlag){
                markFlag = true
                if(children.isNotEmpty()) {
                    for (child in children) {
                        child.mark()
                    }
                }
            }
        }
        override fun toString(): String {
            if(this.content != null){
                return this.content.toString()
            }
            return ""
        }
    }

    var tree = MarkSweepTreeNode<T>(null)
    var heap = ArrayList<MarkSweepTreeNode<T>>(size)
    var freeList = mutableListOf<MarkSweepTreeNode<T>>()

    fun markSweep(){
        tree.mark()
        sweepPhrase()
    }

    fun sweepPhrase(){
        for (item in heap) {
            if(!item.markFlag){
                freeList.add(item)
            }
            item.markFlag = false
        }
    }


}
fun main(args: Array<String>){
    var ms = MarkSweepJs<String>(100)
    ms.tree.content = "A"
    var b =ms.tree.addChild("B")
    var c = ms.tree.addChild("C")
    var d = b.addChild("D")
    var e = c.addChild("E")
    var f = c.addChild("F")
    c.children.remove(f)
    var g = c.addChild("G")
    ms.heap.add(ms.tree)
    ms.heap.add(b)
    ms.heap.add(c)
    ms.heap.add(d)
    ms.heap.add(e)
    ms.heap.add(f)
    ms.heap.add(g)
    ms.markSweep()
    ms.freeList.forEach { item -> println(item) }


}
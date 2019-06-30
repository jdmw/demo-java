package demo.basic.controlflow

fun max(a:Int, b: Int) = if( a>b ) a else b ;

fun demoWhen() {
    var x = 2 ;
    when(x) {
        1 -> println("x == 1");
        2 -> println("x == 2");
        else -> println("x = $x")
    }
    var y = 22 ;
    when(y){
        in 1..100 -> println("y is in the range")
        else -> println("y is invalid");
    }
}
fun main(vars:Array<String>) {
    demoWhen();
}
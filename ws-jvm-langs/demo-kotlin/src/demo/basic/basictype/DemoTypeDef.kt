package demo.basic.basictype

fun defInteger() {
    var n: Int = 1 ;
}

fun testLazyjunction(b: Int): Int {
    print(" b="+b);
    return b ;
}

fun defBoolan() {
    var f: Boolean? = true ;
    var fd = false ;
    var a=1;
    var b=2 ;
    print("condition('a == 1 || testLazyjunction(b) == 1')");
    if( a == 1 || testLazyjunction(b) == 2){ // lazy disjunction
        println(" a="+a+" b="+b);
    }
    print("condition('a == 2 || testLazyjunction(b) == 2')");
    if( a == 2 || testLazyjunction(b) == 2){ // lazy disjunction
        println(" a="+a+" b="+b);
    }
    print("condition('a == 1 && testLazyjunction(b) == 2')");
    if( a == 1 && testLazyjunction(b) == 2){ // lazy conjunction
        println(" a="+a+" b="+b);
    }
    print("condition('a == 2 && testLazyjunction(b) == 2')");
    if( a == 2 && testLazyjunction(b) == 2){ // lazy conjunction
        println(" a="+a+" b="+b);
    }
}

fun defString() {
    var s = """
        A
            B
                C
    """.trimIndent();
    println("\n$s.length = ${s.length }");
}
fun main(args: Array<String>) {
    //println("Hello");
    defBoolan();
    defString();
}

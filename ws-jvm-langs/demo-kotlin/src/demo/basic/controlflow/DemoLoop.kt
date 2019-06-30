package demo.basic.controlflow


fun demoFor() {
    var n = 100 ;
    print("loop from 1 to $n in step 10: ");
    for( i in 1..n step 10){
        print("$i ");
    }
    println();
}
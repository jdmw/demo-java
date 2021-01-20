package jd.jvmexample.instruction.conparision;

public class EInstIfCond {


    /**
     * 
     * Code:
     * stack=2, locals=3, args_size=3
     * ---------------------
     * locals :[a,b]
     *    0: iload_1
     *    1: iload_2 -> stack: [..,a(value1),b(value2)]
     *    2: if_icmplt  9 -> stack pop a,b : if (a < b ) goto 9 
     *    5: iconst_1
     *    6: goto          10
     *    9: iconst_0
     *   10: ireturn
     * 
     *
     */
    public boolean ifIcmp(int a,int b){
        return a >= b ;
    }

    public boolean ifcond(int a){
        if(  a > 0 ){
          return true ;
        }else{
          return false ;
        }
    }

    public boolean equals(Object a , Object b){
        /**
         * locals :[this,a,b]
         * code:
         *   0 aload_1
             1 ifnull 9 (+8)
             4 aload_1
             5 aload_2
             6 if_acmpne 13 (+7)
             9 iconst_1
             10 goto 14 (+4)
             13 iconst_0
             14 ireturn
         */
        return a != null && a == b ;
    }
    
}

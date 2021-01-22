public class DemoExceptionTable {
    int fun(int a){
        try {
          switch(a){
            case 1 : return 1 ;
            case 2 : return 2 ;
            case 3 : return 3 ;
            default : return 0 ;
          }
        } catch(RuntimeException e){
          throw e;
        }
    }
    
    int fun1(int a){
        try {
          // switch(a){
            // case 1 : return 1 ;
            // case 2 : return 2 ;
            // case 3 : return 3 ;
            // default : return 0 ;
          // }
          return a / 0 ;
        } catch(NullPointerException e){
          throw e ;
        } catch(RuntimeException e){
          throw e;
        } finally{
          System.out.println("finally");
        }
    }
}

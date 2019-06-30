public class DemoReturnAddress {
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
}

package jd.demo.se.fundamentals.object_oriented;


public class TestException {


    public static void main(String[] args) throws Exception {
        testEx(true);
        System.out.println();
        testEx(false);
    }

    static boolean testEx(boolean printReturn) throws Exception {
        boolean ret = false;
        try {
            ret = testEx1( printReturn);
        } catch (Exception e) {
            System.out.println("testEx()---catch exception");
            throw e;
        } finally {
            System.out.println("testEx()---finally; return value=" + ret);
            return ret;
        }
    }


    static boolean testEx1(boolean printReturn) throws Exception {
        boolean ret = false;
        try {
            int b = 0 / 0 ;
            ret = true;
            return ret ;
        } catch (Exception e) {
            System.out.println("testEx1()---catch exception");
            throw e;
        } finally {
            System.out.println("testEx1()---finally; return value=" + ret);
            // 有return和没return,上层catch时结果不同
            if (printReturn){
                return ret ;
            }
        }
    }

    static int testEx3() throws Exception {
        try {
            return 1 / 0 ;
        } catch (Throwable e) {
            throw e;
        } finally {
            return 0 ;
        }
    }

    static int testEx4() throws Exception {
        try {
            return 1 / 0 ;
        } catch (Throwable e) {
            throw e;
        } finally {

        }
    }



}
package jd.jvmexample.classfile.start;


public class MemoryMap {

    public static class DemoThread extends Thread{


        public static void main(String[] args){
            new DemoThread().start();
            new DemoThread().start();
            f1();
        }

        public static void f1(){
            f2();
        }

        public static void f2(){
            while(true);
        }

        @Override
        public void run() {
            /*while (true){
                System.out.println(Thread.currentThread().getName());
            }*/
            f1();
        }
    }
}

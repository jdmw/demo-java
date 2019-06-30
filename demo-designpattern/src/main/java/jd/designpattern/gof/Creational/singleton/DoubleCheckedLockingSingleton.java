package jd.designpattern.gof.Creational.singleton;

// double-checked locking
// lazy-loading thread-safe
public class DoubleCheckedLockingSingleton {
    private static DoubleCheckedLockingSingleton singleton ;
    // 私有构造方法，防止从外部创建实例
    private DoubleCheckedLockingSingleton(){

    }
    public static DoubleCheckedLockingSingleton getInstance(){
        if( singleton == null) {
            synchronized (singleton){
                singleton = new DoubleCheckedLockingSingleton();
            }
        }
        return singleton;
    }
}


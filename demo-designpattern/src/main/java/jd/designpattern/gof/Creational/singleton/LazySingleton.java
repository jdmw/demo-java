package jd.designpattern.gof.Creational.singleton;

// lazy loading
public class LazySingleton {
    private static LazySingleton singleton ;
    // 私有构造方法，防止从外部创建实例
    private LazySingleton(){

    }
    public static LazySingleton getInstance(){
        if( singleton == null) {
            singleton = new LazySingleton();
        }
        return singleton;
    }
    // visit: LazySingleton singleton = LazySingleton.getInstance();
}


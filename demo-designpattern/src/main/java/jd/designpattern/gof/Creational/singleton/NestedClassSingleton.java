package jd.designpattern.gof.Creational.singleton;

// 使用静态内部类，由JVM保证线程安全，并同时实现延迟加载
public class NestedClassSingleton {
    private static NestedClassSingleton singleton = new NestedClassSingleton();
    private NestedClassSingleton(){}

    private static class SingletonHolder {
        private static NestedClassSingleton instance = new NestedClassSingleton();
    }
    public static NestedClassSingleton getInstance(){
        return SingletonHolder.instance;
    }
    // visit: NestedClassSingleton singleton = NestedClassSingleton.getInstance();
}


package jd.designpattern.gof.Creational.singleton;

public class Singleton {
    private static Singleton singleton = new Singleton();
    // 私有构造方法，防止从外部创建实例
    private Singleton(){

    }
    public static Singleton getInstance(){
        return singleton;
    }
    // visit: Singleton singleton = Singleton.getInstance();
}


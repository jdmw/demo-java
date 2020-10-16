package jd.demo.se.reflect.dynamicproxy;

import jd.demo.example.person.job.Actor;
import jd.demo.example.person.job.IActor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

/**
 * 利用动态代理，惰性实例化对象
 * 仅当方法被调用时实例化，然后代理调用目标方法
 *
 */
public class LazyInstantiateProxy<T> implements InvocationHandler {

    private Supplier<T> targetSupplier;
    private T target ; // 委托对象

    private LazyInstantiateProxy(Supplier<T> targetSupplier) {
        this.targetSupplier = targetSupplier;
    }

    private T get(){
        if(target == null){
            synchronized (this){
                target = targetSupplier.get();
                if(target == null){
                    throw new NullPointerException("the target is null");
                }
            }
        }
        return target ;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { // proxy is this target
        return method.invoke(get(), args);
    }
    public static <I,T extends I> I newProxyInstance(Supplier<T> targetSupplier, Class<?>  interfaceClass){
        if( targetSupplier == null){
            throw new IllegalArgumentException("supplier is null");
        }
        return (I) Proxy.newProxyInstance(IActor.class.getClassLoader(),new Class[]{interfaceClass},
                new LazyInstantiateProxy<T>(targetSupplier));
    }

    public static void main(String[] args) {
        IActor actor = newProxyInstance(()->{
            System.out.println("instantiating Actor");
            return new Actor("Mary");
        },IActor.class);
        System.out.println("created a actor proxy");
        actor.act();
    }

}
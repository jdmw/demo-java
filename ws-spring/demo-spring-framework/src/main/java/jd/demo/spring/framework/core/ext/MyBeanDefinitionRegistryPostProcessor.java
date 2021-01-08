package jd.demo.spring.framework.core.ext;

import jd.demo.example.service.Greeting;
import jd.demo.example.service.impl.GreetingImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;


class MyBeanFactory {

    interface AService {
        void print();
    }
    interface BService {
        void print();
    }
    interface CService {
        void print();
    }

    public static <T> T getBean(Class<T> beanClass){
        if( AService.class.equals(beanClass)) {
            return (T)new AService(){
                @Override
                public void print() {
                    System.out.println("this is child class of "+beanClass.getSimpleName());
                }
            };
        }else if( BService.class.equals(beanClass)) {
            return (T)new BService(){
                @Override
                public void print() {
                    System.out.println("this is child class of "+beanClass.getSimpleName());
                }
            };
        }else if( CService.class.equals(beanClass)) {
            return (T)new CService(){
                @Override
                public void print() {
                    System.out.println("this is child class of "+beanClass.getSimpleName());
                }
            };
        }
        return null ;
    }
}

/*@Component
@Scope("prototype")*/
//@AllArgsConstructor
class MyInstanceBeanFactory extends  MyBeanFactory{

    private Class<?> beanClass ;

    public MyInstanceBeanFactory(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public <T> T getBean1(){
        return getBean((Class<T>) this.beanClass);
    }

}

/**
 * Created by jdmw on 2008/1/7.
 */
@Component
@Slf4j
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("MyBeanDefinitionRegisterPostProcessor...postProcessBeanDefinitionRegistry");
        System.out.println("count of beans："+beanDefinitionRegistry.getBeanDefinitionCount());


        // manually register a bean
        // RootBeanDefinition rootBeanDefinition=new RootBeanDefinition(Car.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(GreetingImpl.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("greeting",beanDefinition);

        // user supplier
        AbstractBeanDefinition rawBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MyBeanFactory.AService.class, () ->{
            return MyBeanFactory.getBean(MyBeanFactory.AService.class);
        }).getRawBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("a",rawBeanDefinition);

        // use a static factory
        // use a instance factory
        AbstractBeanDefinition bDef = BeanDefinitionBuilder.rootBeanDefinition(MyBeanFactory.class,"getBean")
                .addConstructorArgValue(MyBeanFactory.BService.class)
                .getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("b",bDef);

        // use a instance factory
        AbstractBeanDefinition instanceFactoryDefinition = BeanDefinitionBuilder
                .rootBeanDefinition(MyInstanceBeanFactory.class)
                .addConstructorArgValue(MyBeanFactory.CService.class)
                .getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("instanceFactoryDefinition",instanceFactoryDefinition);
        AbstractBeanDefinition cDef = BeanDefinitionBuilder
                .genericBeanDefinition()
                .setFactoryMethodOnBean("getBean1","instanceFactoryDefinition")
                //.addConstructorArgValue(MyBeanFactory.CService.class)
                .getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("c",cDef);



    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        System.out.println("count of beans："+factory.getBeanDefinitionCount());
        Greeting bean = factory.getBean(Greeting.class);
        System.out.println(bean.sayHello("Jim"));
        factory.getBean(MyBeanFactory.AService.class).print();
        factory.getBean(MyBeanFactory.BService.class).print();

        // take care: spring has a bug here , you should get bean by name 'c' , then you can get singleton Bean CService
        // or spring ioc throw a exception
        factory.getBean("c");
        factory.getBean(MyBeanFactory.CService.class).print();

    }


}

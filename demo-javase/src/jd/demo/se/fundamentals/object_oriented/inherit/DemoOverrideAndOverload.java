package jd.demo.se.fundamentals.object_oriented.inherit;

import java.util.HashMap;
import java.util.Map;

/**
 * article : https://mp.weixin.qq.com/s/Fn00Fyh9e02QumMF_JO9mA
 *
 * 最佳实现：
 * 1 Override方法上始终添加@Override注解
 * 2 不要用实例调用静态方法，使用使用类名调用。
 * 3 慎用继承，能组合就不要用继承
 */
public class DemoOverrideAndOverload {


    public static class SingleAllocate {
        static class Parent {
            void print(String a) { System.out.println("Parent - String"); }
            void print(Object a) { System.out.println("Parent - Object"); }
        }
        static class Child extends Parent {
            @Override
            void print(String a) { System.out.println("Child - String"); }
            @Override
            void print(Object a) { System.out.println("Child - Object"); }
        }
        public static void main(String[] args){
            String string = "";
            Object stringObject = string;

            Child child = new Child();
            child.print(string);
            child.print(stringObject);
            Parent parent = new Child();
            parent.print(string);
            parent.print(stringObject);
            /**
             * 1. 不论把Child实例对象定义为Child还是Parent，实际都会调用 Child::print
             * 2. stringObject 和 string 是完全相同的字符串，唯一的区别在于 string 声明为 String，而 stringObject 声明为 Object。
             *    Java 不支持双重分派（double-dispatch），因此在处理方法参数时，参数的“声明”类型优先于“实际”类型。
             *    即使参数“实际”类型是 String 还是会调用 print(Object)。
             */
        }
    }

    public static class ImplicitOverride {
        static class Parent {
            void print(String a) {
                System.out.println("Parent - String");
            }
            void print(Object a) {
                System.out.println("Parent - Object");
            }
        }
        static class Child extends Parent {
            @Override
            void print(String a) {
                System.out.println("Child - String");
            }
        }
        public static void main(String[] args){
            String string = "";
            Object stringObject = string;

            Parent parent = new Child();
            parent.print(string);
            parent.print(stringObject);
            Child child = new Child();
            child.print(string);
            child.print(stringObject);
            /**
             * parent.print(string): Child - String
             * 实例声明的类型是 Parent，首先匹配到Parent::print(String)。
             * 接着，Java 会检查到Parent::print(String) 有没有潜在的 Override 方法，结果没有找到,因此最后执行Parent::print(Object)。
             */
        }

    }

    public static class ExplicitOverride {
        static class Parent {
            void print(Object a) {
                System.out.println("Parent - Object");
            }
        }
        static class Child extends Parent {
            void print(String a) {
                System.out.println("Child - String");
            }
        }
        public static void main(String[] args){
            String string = "";
            Object stringObject = string;

            Parent parent = new Child();
            parent.print(string);
            parent.print(stringObject);
            Child child = new Child();
            child.print(string);
            child.print(stringObject);
            /**
             * parent.print(string): Parent - Object
             * 实例声明的类型是 Parent，而 Parent 中唯一匹配的方法是 Parent::print(Object)。
             * 接着，Java 会检查 Parent::print(Object) 有没有潜在的 Override 方法，结果没有找到,因此最后执行Parent::print(Object)。
             */
        }

    }


    public static class Ambiguity {
        static class Foo {
            void print(Map map) {
                System.out.println("Map");
            }
            void print(Cloneable cloneable) {
                System.out.println("Cloneable");
            }
        }
        public static void main(String[] args){
            HashMap hashMap = new HashMap();
            Map map = hashMap ;
            Cloneable cloneable = hashMap;

            Foo foo = new Foo();
            //foo.print(hashMap); // 编译失败
            foo.print(map);
            foo.print(cloneable);
            /**
             * 与单一分派类似，参数声明的类型优先于实际类型。如果传入参数有多个方法可以匹配，
             * Java 会抛出编译错误，要求明确指定调用哪个方法。
             */
        }

    }

    public static class MultiInheritance {
        public static class ParentClass {
            public void print() {
                System.out.println("ParentClass");
            }
        }
        public interface ParentInterface {
            default void print() {
                System.out.println("ParentInterface ");
            }
        }
        static class Child extends ParentClass implements ParentInterface {

        }
        public static void main(String[] args){

            Child child = new Child();
            child.print();
            /**
             * 结果： ParentClass
             * 继承类优先
             */
        }

    }

    public static class OverrideDeliver {
        public static class Parent {
            public void print() {
                //System.out.println("Parent - print()");
                foo();
            }
            public void foo() {
                System.out.println("Parent - foo()");
            }
        }
        static class Child extends Parent {
            public void foo() {
                System.out.println("Child - foo()");
            }
        }
        public static void main(String[] args){
            new Child().print();
            /**
             * Parent.print()调用Parent::foo,但Parent::foo被Override了，于是调用Child::foo
             */
        }
    }

    public static class OverridePrivate {
        public static class Parent {
            public void print() {
                //System.out.println("Parent - print()");
                foo();
            }
            private void foo() {
                System.out.println("Parent - foo()");
            }
        }
        static class Child extends Parent {
            public void foo() {
                System.out.println("Child - foo()");
            }
        }
        public static void main(String[] args){
            new Child().print();
            /**
             * 与上例不同，Parent::foo是私有方法无法被Override了，于是调用Parent::foo
             */
        }
    }

    public static class OverrideStatic {
        public static class Parent {
            public static void print() {
                System.out.println("Parent - print()");
            }
        }
        static class Child extends Parent {
            public static void print() {
                System.out.println("Child - print()");
            }
        }
        public static void main(String[] args){
            Child child = new Child();
            Parent parent = child;
            parent.print();
            child.print();
            /**
             * static方法不可Override，如果父类和子类中定义了相同的静态方法，仅根据声明类型决定调用哪个方法，不考虑实例类型。
             *
             * 最佳实现：
             * 1 不要用实例调用静态方法，使用使用类名调用。
             * 2 Override方法上始终添加@Override注解
             */
        }
    }

    public static class CombineExample {
        static class Parent {
            void print() { staticMethod(); instanceMethod(); }
            static void staticMethod() { System.out.println("Parent::staticMethod"); }
            void instanceMethod() { System.out.println("Parent::instanceMethod"); }
        }
        static class Child extends Parent {
            static void staticMethod() { System.out.println("Child::staticMethod"); }
            void instanceMethod() { System.out.println("Child::instanceMethod"); }
        }
        public static void main(String[] args){
            Child child = new Child();
            child.print();
        }
    }


    public static void main(String[] args){

    }
}

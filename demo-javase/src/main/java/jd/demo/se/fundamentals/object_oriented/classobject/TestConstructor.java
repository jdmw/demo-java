package jd.demo.se.fundamentals.object_oriented.classobject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Arrays;

class DemoConstructorBase {
	/* subclass can't access if using private
	 * private DemoConstructorBase() {*/
	DemoConstructorBase(){
		System.out.println(this.getClass().getSimpleName() + ":DemoConstructorBase");
	}
	public void fun() {
		
	}
}

public class TestConstructor extends DemoConstructorBase{
	public TestConstructor() {
		System.out.println(this.getClass().getSimpleName() + ":TestConstructor");
	}
	public static void main(String[] args){
		new TestConstructor();
	}
}

/**
 * 测试目标:
 * 1 动态内部类(non-static nested class)的构造器默认第一个参数为外部类实例
 *
 * 2 方法内定义的嵌套类的无参构造
 * 	(1)静态方法定义: 构造方法参数不包含当前方法所属示例
 * 	(1)实例方法定义: 构造方法第一个参数为当前方法所属示例
 *
 * 3 lambda表达式实例不具备构造器
 *
 * 测试结果: 全部符合预期
 */

class TestConstructorOfNestedClasses {
	class NonStaticNestedClass {
		public NonStaticNestedClass(){}
	}
	static class StaticNestedClass {
		public StaticNestedClass(){}
	}

	public static void main(String[] args) throws NoSuchMethodException {
		System.out.println("\n----------case 1 : non-static nested class VS static nested class ");
		Type[] genericParameterTypesOfNonStaticNestedClass = NonStaticNestedClass.class.getDeclaredConstructors()[0].getGenericParameterTypes();
		System.out.println("inner class - constructor parameter: " + Arrays.toString(genericParameterTypesOfNonStaticNestedClass));

		Type[] genericParameterTypesOfStaticNestedClass = StaticNestedClass.class.getDeclaredConstructors()[0].getGenericParameterTypes();
		System.out.println("static nested class - constructor parameter: " + Arrays.toString(genericParameterTypesOfStaticNestedClass));

		TestConstructorOfLocalClass.main(null);

		System.out.println("\n----------case 4 : lambda ");
		int a = 1 ;
		Runnable r = ()->{
			System.out.println(a);
		} ;
		Constructor<?>[] constructors = r.getClass().getConstructors();
		System.out.println("lambda - constructor : " +Arrays.toString( constructors));

	}


	private static class TestConstructorOfLocalClass {


		public static void main(String[] args) throws NoSuchMethodException {
			localClassesInStaticClass();
			new TestConstructorOfLocalClass().localClassesInInstanceClass();
		}
		public static void localClassesInStaticClass() throws NoSuchMethodException {

			// 方法内定义的局部类
			class OuterClass {
				class InnerClass {
					public InnerClass(){};
				}
			}
			new OuterClass();
			new OuterClass().new InnerClass();
			System.out.println("\n----------case 2 : local classes(outer & inner ) declared in a static method ");
			Type[] genericParameterTypesOfInnerClass = OuterClass.InnerClass.class.getDeclaredConstructors()[0].getGenericParameterTypes();
			System.out.println("inner class - constructor parameter: " + Arrays.toString(genericParameterTypesOfInnerClass));

			Type[] genericParameterTypesOfOuterClass = OuterClass.class.getDeclaredConstructors()[0].getGenericParameterTypes();
			System.out.println("outer class - constructor parameter: " + Arrays.toString(genericParameterTypesOfOuterClass));
		}


		public void localClassesInInstanceClass() throws NoSuchMethodException {

			// 方法内定义的局部类
			class OuterClass {
				class InnerClass {
					public InnerClass(){};
				}
			}
			new OuterClass();
			new OuterClass().new InnerClass();
			System.out.println("\n----------case 3 : local classes(outer & inner ) declared in a instance method ");
			Type[] genericParameterTypesOfInnerClass = OuterClass.InnerClass.class.getDeclaredConstructors()[0].getGenericParameterTypes();
			System.out.println("inner class - constructor parameter: " + Arrays.toString(genericParameterTypesOfInnerClass));

			Type[] genericParameterTypesOfOuterClass = OuterClass.class.getDeclaredConstructors()[0].getGenericParameterTypes();
			System.out.println("outer class - constructor parameter: " + Arrays.toString(genericParameterTypesOfOuterClass));
		}
	}


}


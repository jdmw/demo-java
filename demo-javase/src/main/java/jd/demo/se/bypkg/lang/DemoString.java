package jd.demo.se.bypkg.lang;

import jd.util.lang.Console;

import java.util.UUID;

public class DemoString {

	/**
	 * Q: StringBuilder VS StringBuffer
	 * 相同点:
	 * 1. StringBuilder和StringBuffer都是可变字符串，均继承AbstractStringBuilder,内部使用动态扩容的数组存储.
	 * 2. 默认容量都是16
	 *
	 * 不同点:
	 * 1. StringBuilder线程不安全;StringBuffer线程安全,线程安全的原理是加锁,因此性能有所丧失.StringBuffer.toString()方面采用了缓存.
	 *
	 * AbstractStringBuilder扩容机制: 首先是把容量变为原来容量的2倍加2。最大容量是Integer.MAX_VALUE
	 * 	 源码位置:AbstractStringBuilder.newCapacity()
	 */
	public static final String g1 = "g1" ;
	public static class DemoStringBuilder{
		public static void main(String[] args) {
			// 观察字节码可以看出编译器自动将字符串拼接操作编译成StringBuilder.append()
			String s = "abc" + "def";
			String s2 = g1 + s ;
			System.out.println(s2);
			for(int i = 0 ; i < 10000 ; i ++) // For 模拟程序的多次调用
			{
				s += i ;
			}

			StringBuilder sb = new StringBuilder();
			sb.append(1).append(3);
			sb.append("231313123131231231231231");
			System.out.println(sb);

			StringBuffer buf = new StringBuffer();
			buf.append(UUID.randomUUID().toString()+UUID.randomUUID().toString());
			System.out.println(buf);

		}

	}
	public static void main(String[] args) {
		Console.ln(" Bi:1".split(":")[0].trim());
		String protocol = "HTTP/1.1";
		protocol = protocol.split("/")[0].toLowerCase();
		System.out.println(protocol);
		String urlPattern = "/*".replace("*", ".*");
		System.out.println(urlPattern);
		System.out.println("/A/B".matches(urlPattern));

	}

}

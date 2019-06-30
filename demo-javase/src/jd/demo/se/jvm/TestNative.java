/*
 * package jd.demo.se.jvm;
 * 
 * import com.sun.jna.Native; import com.sun.jna.win32.StdCallLibrary;
 * 
 * public class TestNative { interface api extends StdCallLibrary { api INSTANCE
 * = (api) Native.loadLibrary("kernel32", api.class);
 * 
 * int GetStdHandle(int stdHand);
 * 
 * boolean SetConsoleTextAttribute(int hConsoleOutput, int textAtt); }
 * 
 * public static void out(String str, int color) { int ptr =
 * api.INSTANCE.GetStdHandle(-11); api.INSTANCE.SetConsoleTextAttribute(ptr,
 * color); System.out.println(str); }
 * 
 * public static void main(String[] args) { out("hello ", 2); out("world", 6); }
 * }
 */
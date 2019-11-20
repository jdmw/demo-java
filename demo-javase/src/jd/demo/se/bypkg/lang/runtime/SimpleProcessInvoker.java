package jd.demo.se.bypkg.lang.runtime;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class SimpleProcessInvoker {

	
	private static Runtime RUNTIME = Runtime.getRuntime();
	static {
		RUNTIME.addShutdownHook(new Thread(()->{
			Console.tpln("JVM stop");
		}));
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//simpleRunDontWait("winver");
		CcUt.start(()->{
			try {
				simpleRunDontWait("cmd");
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		},100);
		System.in.read();
		//simpleRunThenDestroy("winver");
		//simpleRunWaitForTime("winver");
		/*
		 * Process p = RUNTIME.exec("winver"); TimeUnit.SECONDS.sleep(2);
		 * RUNTIME.addShutdownHook(new Thread(()->{ Console.tpln("JVM stop");
		 * p.destroy(); }));
		 */
		/*
		Process p = RUNTIME.exec("cmd");
		p.waitFor(3,TimeUnit.SECONDS);
		Console.tpln("executed command cmd,proccess alive: " + p.isAlive()); // alive
		p.destroyForcibly();
		Console.tpln("executed command cmd,proccess alive: " + p.isAlive()); // dead
		TimeUnit.SECONDS.sleep(49);*/
		
	}
	
	/** run 'winver' output:
	 * after executed, the window shows,and the process is alive
	 * and immediately JVM exit,but winver.exe is still running 
	 **/
	public static Process simpleRunDontWait(String command) throws IOException, InterruptedException {
		Process process = RUNTIME.exec(command);
		Thread.sleep(3000);
		Console.tpln("executed command " + command + ",proccess alive: " + process.isAlive());
		return process ;
	}
	
	public static Process simpleRunThenDestroy(String command) throws IOException, InterruptedException {
		Process p = RUNTIME.exec(command);
		Console.tpln("executed command " + command + ",proccess alive: " + p.isAlive());
		//Thread.sleep(3000);
		p.destroy();
		Console.tpln("after destory() invoked, proccess alive: " + p.isAlive());
		return p ;
	}

	/**
	 * run winver output:
	 * when the window is show �� jvm is waitting ��at p��waitFor()��,the process is alive 
	 * till i I processed confirm button,then console output: finish command winver,proccess alive: false
	 */
	public static Process simpleRunWaitFor(String command) throws IOException, InterruptedException {
		Process p = RUNTIME.exec(command);
		p.waitFor();
		Console.tpln("finish command " + command + ",proccess alive: " + p.isAlive());
		//p.destroy();
		return p ;
	}
	
	
	/**
	 * run winver output:
	 * when the window is show �� jvm is waitting ��at p��waitFor()��,the process is alive 
	 * till i I processed confirm button,then console output: finish command winver,proccess alive: false
	 */
	public static Process simpleRunWaitForTime(String command) throws IOException, InterruptedException {
		Process p = RUNTIME.exec(command);
		p.waitFor(3,TimeUnit.SECONDS);
		Console.tpln("finish command " + command + ",proccess alive: " + p.isAlive());
		//p.destroy();
		return p ;
	}
	
	

}

package jd.demo.se.bypkg.lang.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;

public class InteractProcessInvoker {

	private static Runtime RUNTIME = Runtime.getRuntime();
	static {
		RUNTIME.addShutdownHook(new Thread(()->{
			Console.tpln("JVM stop");
		}));
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//syncRead("shutdown -?");
		//syncNonblockingRead("ping -t bing.com");
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();
		BlockingQueue<String> writeque = new LinkedBlockingQueue<>();
		Process p = bothReadWrite("cmd /k echo off & echo a",queue,writeque);
		CcUt.start(()->{CcUt.sleepS(10);p.destroy();});
		String str,echo ;
		while((str = writeque.take()) != null) {
			//os.println(echo.getBytes());
			System.out.println("read : " + str);
			if(str == null || "".equals(str = str.trim())) {
				echo = null ;
			}
			String[] lines = str.split("\n");
			String lastLine = lines[lines.length -1].trim();
			switch(str){
				//case "" : return "echo a" ;
				case "a" : echo= "echo b\n" ;break;
				case "b" : echo="echo c\n" ;break;
				case "c" : echo= "exit\n" ;break;
				default: echo = null ;
			}
			if(echo!= null) {
				System.out.println("write: " + echo);
				writeque.add(echo);
			}
			
		}
		
	}

	
	public static Process syncBlockingRead(String command) throws IOException, InterruptedException {
		Process p = RUNTIME.exec(command);
		System.out.println("executed command " + command + ",proccess alive: " + p.isAlive());
		//Thread.sleep(3000);
		try(Reader is = new BufferedReader(new InputStreamReader(p.getInputStream(),Charset.defaultCharset()))){
			int b = -1 ;
			while((b = is.read()) != -1) {
				System.out.append((char)b);
			}
			//System.out.println(IOUt.toString(is));
		}

		System.out.println(" proccess alive: " + p.isAlive());
		//p.destroy();
		return p ;
	}
	
	public static Process syncNonblockingRead(String command) throws IOException, InterruptedException {
		Process p = RUNTIME.exec(command);
		System.out.println("executed command " + command + ",proccess alive: " + p.isAlive());
		//Thread.sleep(3000);
		StringBuilder  sb = new StringBuilder();
		new Thread(()->{
			try(Reader is = new BufferedReader(new InputStreamReader(p.getInputStream(),Charset.defaultCharset()))){
				int b = -1 ;
				while((b = is.read()) != -1) {
					sb.append((char)b);
				}
				//System.out.println(IOUt.toString(is));
			}catch(IOException e) {
				e.printStackTrace(System.err);
			}
		}).start();

		p.waitFor(3, TimeUnit.SECONDS);
		System.out.println(" proccess alive: " + p.isAlive());
		if(p.isAlive()) p.destroy();
		return p ;
	}
	
	
	public static Process bothReadWrite(String command,BlockingQueue<String> queue,BlockingQueue<String> writeque) throws IOException, InterruptedException {
		Process p = RUNTIME.exec(command);
		System.out.println("executed command " + command + ",proccess alive: " + p.isAlive());
		//Thread.sleep(3000);

		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(()->{
			try(BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream(),Charset.defaultCharset()))){
				String str = null ;
				while((str = is.readLine()) != null) {
					//System.out.append((char)b);
					//outsb.append((char)b);
					queue.add(str);
				}
			}catch(IOException e) {
				e.printStackTrace(System.err);
			}
		});
		pool.submit(()->{
			StringBuilder errsb = new StringBuilder();
			try(Reader is = new BufferedReader(new InputStreamReader(p.getInputStream(),Charset.defaultCharset()))){
				int b = -1 ;
				while((b = is.read()) != -1) {
					//System.err.append((char)b);
					errsb.append((char)b);
				}
				System.err.println(errsb);
			}catch(IOException e) {
				e.printStackTrace(System.err);
			}
		});
		pool.submit(()->{
			//StringBuilder outsb = new StringBuilder();
			String str = null ;
			try (PrintStream os = new PrintStream(p.getOutputStream())){
				String echo = null ;
				while((echo = writeque.take()) != null) {
					os.println(echo.getBytes());
					System.out.println("write: " + echo);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		
		System.out.println(" proccess alive: " + p.isAlive());
		p.waitFor();
		pool.shutdown();
		return p ;
	}
}

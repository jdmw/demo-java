import com.fourinone.BeanContext;
import com.fourinone.CoolHashClient;

public class ThreadClient implements Runnable{
	public CoolHashClient chc;
	
	public ThreadClient(CoolHashClient chc){
		this.chc=chc;
	}
	
	//����߳������޸�threadname, ���Լ��߳�������Ϊ��value�������ɵ�valueֵ���
	public void putTest(){
		try{
			long begintime = System.currentTimeMillis();
            String threadname = (String)chc.put("threadname",Thread.currentThread().getName());
           	System.out.println(Thread.currentThread().getName()+" put time:"+(System.currentTimeMillis()-begintime)+" old:"+threadname);
           	
           	//chc.exit();//���̲߳���δ������Ҫ�رտͻ���
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//д������У�ͬʱ�ж���߳�������ȡthreadname�����
	public void getTest(){
		try{
			long begintime = System.currentTimeMillis();
            String threadname = (String)chc.get("threadname");
           	System.out.println(Thread.currentThread().getName()+" get time:"+(System.currentTimeMillis()-begintime)+" get:"+threadname);
           	
           	//chc.exit();//���̲߳���δ������Ҫ�رտͻ���
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void putNxTest(){
		try{
			long begintime = System.currentTimeMillis();
			String threadname = (String)chc.putNx("threadname",Thread.currentThread().getName());
           	System.out.println(Thread.currentThread().getName()+" putNx time:"+(System.currentTimeMillis()-begintime)+" return:"+threadname);
        }catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void putPlusTest(){
		try{
			long begintime = System.currentTimeMillis();
			Integer count = (Integer)chc.putPlus("count",1);
           	System.out.println(Thread.currentThread().getName()+" putPlus time:"+(System.currentTimeMillis()-begintime)+" return:"+count);
        }catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void pvTest(){
		try{
			long start = System.currentTimeMillis();
			for(int i=0;i<1000;i++){
				Object nx = chc.putNx("v0_"+i, i);
				if(nx==null)
					chc.putPlus("pv_v0",1);
			}
			System.out.println("time taken in MS--"+(System.currentTimeMillis()-start));
			
			int v0 = (int)chc.get("pv_v0");
			System.out.println("v0 count:"+v0);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void run(){
		putTest();//�߳���д��
		getTest();//�߳��ж�ȡ
		//putNxTest();
		//putPlusTest();
		//pvTest();
	}
	
	//�������java -cp fourinone.jar; ThreadPutGet localhost 2014 1000
	//Ϊ�������鿴���߳������������Թر�һЩϵͳ��������ͻ��˵�config.xml��LOG���ֵ�DESC="LOGLEVEL"���õ�OFF����
	public static void main(String[] args){
		String host = args[0];
		int ip = Integer.parseInt(args[1]);
		int threadcount=Integer.parseInt(args[2]);
		
		for(int i=0;i<threadcount;i++){
			CoolHashClient chc = BeanContext.getCoolHashClient(host,ip);
			ThreadClient tc = new ThreadClient(chc);
			new Thread(tc).start();
        }
	}
}
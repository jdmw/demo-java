import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

public class WorkerMul extends MigrantWorker
{
	public WareHouse doTask(WareHouse inhouse)
	{
		String taskId =  inhouse.getString("taskId");
		System.out.print("taskId"+taskId+"��������...");
		try{Thread.sleep(3000L);}catch(Exception ex){}
		System.out.println("taskId"+taskId+"������ɡ�");
		
		return new WareHouse("result", "ok");
	}
	
	public static void main(String[] args)
	{
		WorkerMul wd = new WorkerMul();
		wd.waitWorking(args[0],Integer.parseInt(args[1]),"WorkerMul");
	}
}
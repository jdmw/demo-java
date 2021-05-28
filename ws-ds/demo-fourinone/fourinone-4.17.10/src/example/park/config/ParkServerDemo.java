package example.park.config;

import com.fourinone.BeanContext;
import com.fourinone.LastestEvent;
import com.fourinone.LastestListener;
import com.fourinone.pattern.park.ParkLocal;
import com.fourinone.pattern.warehouse.ObjectBean;

/**
 * 分布式协调:维持统一的配置信息
 *
 * 在分布式多台机器环境下,维持统一的配置信息是最常见的需求, 当配置信息改变时,所有的机器能实时获取并更新。

 fourinone通过park进行配置信息管理，park提供创建和修改信息的方法，并支持轮循和事件响应两种方式获取变化的对象，两种方式的效果一样。

 SetConfig：在parkserver上建立一个“domain=浙江、node=杭州、value=西湖”的配置信息，并且在10秒后把“西湖”改为“余杭”
 GetConfigA：演示了轮循方式监控配置信息变化，它调用一个getLastest的方法,该方法可以传入一个旧版本的对象，并对比获取最新版本的对象，如果有就打印，如果没有最新版本，就返回null

 GetConfigB：演示了事件响应方式监控配置信息变化，它实现一个LastestListener的事件接口并进行注册，当信息变化时，会产生事件并获取到变化后的对象进行处理，LastestListener的happenLastest方法有个boolean返回值，如果返回false，它会一直监控配置信息变化，继续有新的变化时还会进行事件调用；如果返回true，它完成本次事件调用后就终止。

 运行步骤：
 1、启动ParkServerDemo（它的IP端口已经在配置文件指定）
 2、运行GetConfigA
 3、运行GetConfigB
 4、运行SetConfig

 如果是线上环境,为避免ParkServer宕机, parkServer可以配置master和任意数量的slave，请使用ParkMasterSlave替换上面的ParkServerDemo即可，每次输入M或者S启动master或者slave,运行过程关掉master，GetConfig仍然可以从slave获取配置信息。

 fourinone对比zookeeper的优势：zookeeper没有获取最新版本信息的方法支持，它只能粗暴的在每次写入更新等方法时注册一个watch，当这些方法被调用后就回调，它不考虑信息内容是否变化，对于没有使信息内容发生改变的更新，zookeeper仍然会回调，并且zookeeper的回调比较呆板，它只能用一次，如果信息持续变化，必须又重新注册watch, 而fourinone的事件处理则可以自由控制是否持续响应信息变化。
 */
public class ParkServerDemo
{
	public static class GetConfigA
	{
		public static void main(String[] args)
		{
			ParkLocal pl = BeanContext.getPark();
			ObjectBean oldob = null;
			while(true){
				ObjectBean newob = pl.getLastest("zhejiang", "hangzhou", oldob);
				if(newob!=null){
					System.out.println(newob);
					oldob = newob;
				}
			}
		}
	}

	public static class ParkMasterSlave
	{
		public static void main(String[] args)
		{
			String[][] master = new String[][]{{"localhost","1888"},{"localhost","1889"}};
			String[][] slave = new String[][]{{"localhost","1889"},{"localhost","1888"}};

			String[][] server = null;
			if(args[0].equals("M"))
				server = master;
			else if(args[0].equals("S"))
				server = slave;

			BeanContext.startPark(server[0][0],Integer.parseInt(server[0][1]), server);
		}
	}

	public static class GetConfigB implements LastestListener
	{
		public boolean happenLastest(LastestEvent le)
		{
			ObjectBean ob = (ObjectBean)le.getSource();
			System.out.println(ob);
			return false;
		}

		public static void main(String[] args)
		{
			ParkLocal pl = BeanContext.getPark();
			pl.addLastestListener("zhejiang", "hangzhou", null, new GetConfigB());
		}
	}


	public static class SetConfig
	{
		public static void main(String[] args)
		{
			ParkLocal pl = BeanContext.getPark();
			ObjectBean xihu = pl.create("zhejiang", "hangzhou", "xihu");
			try{Thread.sleep(8000);}catch(Exception e){}
			ObjectBean yuhang = pl.update("zhejiang", "hangzhou", "yuhang");
		}
	}

	public static void main(String[] args)
	{
		BeanContext.startPark();
	}
}


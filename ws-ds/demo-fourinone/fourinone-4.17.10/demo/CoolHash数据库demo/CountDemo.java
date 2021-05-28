import com.fourinone.BeanContext;
import com.fourinone.CoolHashClient;
import com.fourinone.CoolHashMap;
import com.fourinone.CoolBitSet;
import com.fourinone.StringBitMap;
import java.util.Random;

public class CountDemo
{
	public static void countTest(String[] args){
		CoolHashClient chc = BeanContext.getCoolHashClient(args[0],Integer.parseInt(args[1]));
		try{
			Random r = new Random(); 
			int[] vcount = {0,0,0};
			
			long start = System.currentTimeMillis();
			CoolHashMap hm=new CoolHashMap();
			for(long i=0;i<1000000;i++){
				int v = r.nextInt(3);
				hm.put("id"+i, v);//value随机取值三选一
				vcount[v]++;//统计每种取值的count
			}
			System.out.println("load time taken in MS--"+(System.currentTimeMillis()-start));
			
			start = System.currentTimeMillis();
			int n=chc.put(hm);
			System.out.println("putBatch time taken in MS--"+(System.currentTimeMillis()-start));
			System.out.println("putBatch total:"+n);
			
			//value每种取值的自增更新
			chc.putPlus("index.v0",vcount[0]);
			chc.putPlus("index.v1",vcount[1]);
			chc.putPlus("index.v2",vcount[2]);
			
			int v0 = (int)chc.get("index.v0");
			int v1 = (int)chc.get("index.v1");
			int v2 = (int)chc.get("index.v2");
			System.out.println("v0 count:"+v0);
			System.out.println("v1 count:"+v1);
			System.out.println("v2 count:"+v2);
			System.out.println("total count:"+(v0+v1+v2));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		chc.exit();
	}
	
	public static void pvTest(String[] args){
		CoolHashClient chc = BeanContext.getCoolHashClient(args[0],Integer.parseInt(args[1]));
		try{
			long start = System.currentTimeMillis();
			for(int i=0;i<100000;i++){
				Object nx = chc.putNx("v0_"+i, i);//v0id
				if(nx==null)
					chc.putPlus("pv_v0",1);
			}
			System.out.println("time taken in MS--"+(System.currentTimeMillis()-start));
			
			int v0 = (int)chc.get("pv_v0");
			System.out.println("v0 count:"+v0);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		chc.exit();
	}
	
	//本地计算留存
	public static void retainLocal(String[] args){
		CoolHashClient chc = BeanContext.getCoolHashClient(args[0],Integer.parseInt(args[1]));
		try{
			CoolBitSet time1 = new CoolBitSet(100000000);//第1天去重用户，bitmap空间1亿
			CoolBitSet time2 = new CoolBitSet(100000000);//第2天去重用户，bitmap空间1亿
			CoolBitSet time3 = new CoolBitSet(100000000);//第3天去重用户，bitmap空间1亿
			
			Random r = new Random(); 
			long start = System.currentTimeMillis();
			//1000万用户流量，随机模拟用户id写入bitmap去重
			for(int i=0;i<10000000;i++){
				time1.set(r.nextInt(100000000));
				time2.set(r.nextInt(100000000));
				time3.set(r.nextInt(100000000));
			}	
			System.out.println("set 1,2,3 time taken in MS--"+(System.currentTimeMillis()-start));
			
			System.out.println("time1:"+time1.toString(8));//输出第1天bitmap前8个byte二进制结果串
			System.out.println("time2:"+time2.toString(8));//输出第2天
			
			start = System.currentTimeMillis();
			CoolBitSet time2new = time1.setNew(time2);//求第2天的新增用户
			System.out.println("set new time taken in MS--"+(System.currentTimeMillis()-start));
			System.out.println("t2new:"+time2new.toString(8));
			System.out.println("t2new total:"+time2new.getTotal());//输出新增用户总数
			
			
			start = System.currentTimeMillis();
			CoolBitSet time2left = time2new.and(time3);//通过第2天新增用户和第3天去重用户求留存
			System.out.println("set left time taken in MS--"+(System.currentTimeMillis()-start));
			System.out.println("time3:"+time3.toString(8));
			System.out.println("tleft:"+time2left.toString(8));
			System.out.println("t1left total:"+time2left.getTotal());//输出留存总数
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		chc.exit();
	}
	
	//存储引擎计算留存
	public static void retainServer(String[] args){
		CoolHashClient chc = BeanContext.getCoolHashClient(args[0],Integer.parseInt(args[1]));
		try{
			
			CoolBitSet time1 = new CoolBitSet(10000000);//每个bitmap最大1000万
			CoolBitSet time2 = new CoolBitSet(10000000);
			CoolBitSet time3 = new CoolBitSet(10000000);
			Random r = new Random(); 
			//1000万用户流量去重
			for(int i=0;i<10000000;i++){
				time1.set(r.nextInt(10000000));
				time2.set(r.nextInt(10000000));
				time3.set(r.nextInt(10000000));
			}	
			
			int n1 = chc.putBitSet("time1", time1);//写入kv存储并gzip压缩，同key多次写入引擎会进行合并
			int n2 = chc.putBitSet("time2", time2);
			int n3 = chc.putBitSet("time3", time3);
			
			System.out.println("time1:"+time1.toString(8));
			System.out.println("time2:"+time2.toString(8));
			
			long start = System.currentTimeMillis();
			CoolBitSet time2new = (CoolBitSet)chc.putBitSet("time1",time2,"new");//求第2天的新增用户
			System.out.println("set new time taken in MS--"+(System.currentTimeMillis()-start));
			System.out.println("t2new:"+time2new.toString(8));
			System.out.println("t2new total:"+time2new.getTotal());
			
			
			start = System.currentTimeMillis();
			CoolBitSet time2left = (CoolBitSet)chc.putBitSet("time1",time3,"and");//通过第2天新增用户和第3天去重用户求留存
			System.out.println("set left time taken in MS--"+(System.currentTimeMillis()-start));
			System.out.println("time3:"+time3.toString(8));
			System.out.println("tleft:"+time2left.toString(8));
			System.out.println("t1left total:"+time2left.getTotal());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		chc.exit();
	}
	
	//通过bitmap做大数据实时统计
	public static void realtimeStatistics(String[] args){
		CoolHashClient chc = BeanContext.getCoolHashClient(args[0],Integer.parseInt(args[1]));
		try{
			CoolBitSet cbs1 = new CoolBitSet(100000000);//第1分钟去重用户，bitmap空间1亿
			CoolBitSet cbs2 = new CoolBitSet(100000000);//第2分钟去重用户，bitmap空间1亿
			
			long start = System.currentTimeMillis();
			//模拟1亿用户流量，为方便验证统计结果，第1分钟放偶数用户，第2分钟放奇数用户
			for(int i=0;i<10;i++){
				if(i%2==0)
					cbs1.set(i);
				else
					cbs2.set(i);
			}
			cbs1.set(1);
			System.out.println("set 1,2 time taken in MS--"+(System.currentTimeMillis()-start));
			
			//去重用户
			start = System.currentTimeMillis();
			System.out.println("cbs1:"+cbs1.getTotal());
			System.out.println("cbs2:"+cbs2.getTotal());
			System.out.println("distinct user: time taken in MS--"+(System.currentTimeMillis()-start));
			
			//活跃用户（分钟、小时、日（DAU）、周、月(MAU)）
			CoolBitSet activeCbs = new CoolBitSet(100000000);
			activeCbs.set(cbs1);
			start = System.currentTimeMillis();
			activeCbs.or(cbs2);//本地bitmap或操作，如果在引擎端完成可以使用chc.putBitSet("cbs1",cbs2,"or");
			System.out.println("active user:"+activeCbs.getTotal()+",time taken in MS--"+(System.currentTimeMillis()-start));
			
			//非活跃用户
			CoolBitSet notactiveCbs = new CoolBitSet(100000000);
			notactiveCbs.set(activeCbs);
			start = System.currentTimeMillis();
			notactiveCbs.andnot();//本地bitmap否操作，如果在引擎端完成可以使用chc.putBitSet("cbs3",CoolBitSet,"andnot");
			System.out.println("not active user:"+notactiveCbs.getTotal()+",time taken in MS--"+(System.currentTimeMillis()-start));
			
			//重度用户
			CoolBitSet hotCbs = new CoolBitSet(100000000);
			hotCbs.set(cbs1);
			start = System.currentTimeMillis();
			hotCbs.and(cbs2);//本地bitmap且操作，如果在引擎端完成可以使用chc.putBitSet("cbs1",cbs2,"and");
			System.out.println("hot user:"+hotCbs.getTotal()+",time taken in MS--"+(System.currentTimeMillis()-start));
			
			//新增用户
			CoolBitSet newCbs = new CoolBitSet(100000000);
			newCbs.set(cbs1);
			start = System.currentTimeMillis();
			int n = newCbs.set(cbs2);//本地bitmap且操作，如果在引擎端完成可以使用chc.putBitSet("cbs1",cbs2,"and");
			//newCbs.setNew(cbs2);
			System.out.println("new user:"+n+",time taken in MS--"+(System.currentTimeMillis()-start));
			
			//多重指标组合
			//cbs1.and(csb2).and(cbs3)...
			
			//导入到k/v存储
			start = System.currentTimeMillis();
			int n1 = chc.putBitSet("cbs1",cbs1);//存储为bitSet格式
			//Object n1 = chc.put("cbs1",cbs1.getBytes());//普通kv存储格式
			System.out.println("putBitSet n1:"+n1+",time taken in MS--"+(System.currentTimeMillis()-start));
			
			start = System.currentTimeMillis();
			int n2 = chc.putBitSet("cbs2",cbs2);
			//Object n2 = chc.put("cbs1",cbs2.getBytes());
			System.out.println("putBitSet n2:"+n2+",time taken in MS--"+(System.currentTimeMillis()-start));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		chc.exit();
	}	
	
	
	public static void bitSetTest(String[] args){
		CoolHashClient chc = BeanContext.getCoolHashClient(args[0],Integer.parseInt(args[1]));
		try{
			//写入10亿数据到一个bitmap
			CoolBitSet cbs = new CoolBitSet(1000000000);
			long start = System.currentTimeMillis();
			for(int i=0;i<1000000000;i++){
				cbs.set(i);
			}
			System.out.println("set CoolBitSet time taken in MS--"+(System.currentTimeMillis()-start));
			
			long maxnum = 100000000l;//最大值1亿
			int everynum = 10000000;//每bitmap大小1000万
			CoolBitSet[] cbsArr = new CoolBitSet[(int)(maxnum/everynum)];
			
			//分10个bitmap区间写入1亿数据
			start = System.currentTimeMillis();
			for(long i=0;i<maxnum;i++){
				long num = i;
				int keyIndex = (int)(num/everynum);
				int bitIndex = (int)(num%everynum);
				
				if(cbsArr[keyIndex]==null)
					cbsArr[keyIndex] = new CoolBitSet(everynum);
				cbsArr[keyIndex].set(bitIndex);
			}
			System.out.println("set CoolBitSet time taken in MS--"+(System.currentTimeMillis()-start));
			
			start = System.currentTimeMillis();
			for(int i=0;i<cbsArr.length;i++){
				if(cbsArr[i]!=null){
					chc.put(i+"",cbsArr[i]);//把10个bitmap写入db
					System.out.println(i+" total:"+cbsArr[i].getTotal());
				}
			}
			System.out.println("put CoolHash time taken in MS--"+(System.currentTimeMillis()-start));
			
			long a = 19999999;
			System.out.println(a+":"+chc.getBitSet((int)(a/10000000)+"",(int)(a%10000000)));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		chc.exit();
	}
	
	public static void stringBitMapTest(){
		StringBitMap sbm = new StringBitMap();
			
		System.out.println(sbm.get("aabbcc"));
		
		//写入字符串
		sbm.set("aabbcc");
		
		//判断该字符串是否存在
		System.out.println(sbm.get("aabbcc"));
		
		//测试1000万随机生成的15位IMEI设备号，返回碰撞个数
		System.out.println("collision:"+sbm.collisionTest(10000000));
	}
	
	public static void main(String[] args){
		//java  -cp fourinone.jar; CountDemo localhost 2014
		//countTest(args);
		//pvTest(args);
		bitSetTest(args);
		//realtimeStatistics(args);
		//stringBitMapTest();
	}
}
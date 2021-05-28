import com.fourinone.BeanContext;
import com.fourinone.CoolHashClient;
import com.fourinone.CoolHashMap;
import com.fourinone.CoolHashResult;
import com.fourinone.Filter;
import com.fourinone.Result;
import com.fourinone.Filter.ValueFilter;

public class AsynClient{
	public static void backupTest(){
		//��������CoolHash Server������������server�Ŀͻ��ˣ������̨ͬ������������CoolHash Server�����ò�ͬ�˿ں�
		CoolHashClient c1 = BeanContext.getCoolHashClient("192.168.0.1",2014);
		CoolHashClient c2 = BeanContext.getCoolHashClient("192.168.0.2",2014);
		try{
			//ͬ������д��key0������server
			c2.put("key0","value0");
			System.out.format("server2 get key0: %s\n", c2.get("key0"));
			c1.put("key0","value0");
			System.out.format("server1 get key0: %s\n", c1.get("key0"));
			
			//�첽д�뵽server2�����ȷ�������д��server1
			Result rs = c2.operateAsyn("put", new Class[]{String.class, Object.class}, new Object[]{"key1", "value1"});
			System.out.format("server2 put asyn...\n");//c2.get("key1")
			c1.put("key1","value1");
			System.out.format("server1 get key1: %s\n", c1.get("key1"));
			//�����ȴ��첽����������Ҳ���Բ��ȴ�����д��ɹ�
			Object obj = rs.getResultWait();
			System.out.format("server2 put ok and get key1: %s\n", obj);
			
			CoolHashMap hm=new CoolHashMap();
			for(long i=0;i<1000;i++){
				hm.put("key"+i, "value"+i);
			}
			//�첽����д�����ݵ�server2�����ȷ���
			Result rsmap = c2.operateAsyn("put", new Class[]{CoolHashMap.class}, new Object[]{hm});
			System.out.format("server2 put %s keys.\n", rsmap.getResult());
			//����д�뵽server1
			int count = c1.put(hm);
			System.out.format("server1 put %s keys.\n", count);
			//�����ȴ��첽����д��ɹ��������ǲ��в������첽���ݵȴ���ɼ���ȷ���ɹ����ܱȴ��б������ܸ���
			Object objmap = rsmap.getResultWait();
			System.out.format("server2 put ok %s keys.\n", objmap);
		}catch(Exception ex){
			System.out.println(ex);
			
		}
		c1.exit();//����첽���ȴ���������д��δ����˳��ᶪʧ���ݣ���ʱ���Բ��رտͻ���
		c2.exit();
	}
	
	public static void findTest(){
		//��������server�Ŀͻ���
		CoolHashClient c1 = BeanContext.getCoolHashClient("192.168.0.1",2014);
		CoolHashClient c2 = BeanContext.getCoolHashClient("192.168.0.2",2014);
		try{
			Result[] rs = new Result[2];
			//�첽��ʽͬʱ��ѯ����server��server1��ѯvalue����8�����ݣ�server2��ѯvalue����6������
			rs[0] = c1.operateAsyn("find", new Class[]{String.class, Filter.class}, new Object[]{"*", ValueFilter.contains("8")});
			rs[1] = c2.operateAsyn("find", new Class[]{String.class, Filter.class}, new Object[]{"*", ValueFilter.contains("6")});
			//�ȴ�����server��ѯȫ������
			Object[] rsobj = Result.getResultWait(rs);
			CoolHashMap chmb1 = ((CoolHashResult)rsobj[0]).nextBatch(1000);
			CoolHashMap chmb2 = ((CoolHashResult)rsobj[1]).nextBatch(1000);
			//������server�Ĳ�ѯ����󲢼����õ�������8�ֺ���6������
			CoolHashMap andmap = chmb1.and(chmb2);
			System.out.println("and:"+andmap);
		}catch(Exception ex){
			System.out.println(ex);
		}
		c1.exit();
		c2.exit();
	}
	
	public static void main(String[] args){
		backupTest();//�첽д�뱸��
		findTest();//�첽��ѯ�ϲ�
	}
}
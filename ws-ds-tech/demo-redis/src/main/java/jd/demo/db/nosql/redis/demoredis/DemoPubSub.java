package jd.demo.db.nosql.redis.demoredis;


import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

class Subscriber extends JedisPubSub implements Runnable{

	private final JedisPool jedisPool;
    private final String channel ;
    
	public Subscriber(JedisPool jedisPool,String channel ) {
		this.jedisPool = jedisPool ;
		this.channel = channel ;
	}
	
	@Override
	public void onMessage(String channel, String message) {
		Console.tp("[%t]receive message: %s\n" , message);
	}
	
	@Override
	public void run() {
        try (Jedis jedis = jedisPool.getResource()) {
        	Console.tp("[%t]subscribe channel: %s\n",channel);
            jedis.subscribe(this, channel);    //通过subscribe 的api去订阅，入参是订阅者和频道名
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}

public class DemoPubSub {

	static JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
	
	
	
	public static void main(String[] args) throws InterruptedException {
		String channel = "tchanel" ;
		CcUt.start(new Subscriber(jedisPool,channel),true);
		CcUt.start(new Subscriber(jedisPool,channel),true);
		CcUt.start(new Subscriber(jedisPool,channel),true);
		
		try (Jedis jedis = jedisPool.getResource()) {
			CcUt.start(()->jedis.publish(channel, "Hello"),true);
        } catch (Exception e) {
            System.err.println(String.format("publish channel error, %s", e));
        }

		Thread.sleep(1000);
	}

}

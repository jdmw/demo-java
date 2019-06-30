package jd.demo.db.nosql.redis.demoredis;

import redis.clients.jedis.Jedis;

public class DemoRedisCommand {

	static Jedis redis = new Jedis("localhost");
	
	
	public static void main(String[] args) {
		demoVar();
		demoList();
		System.out.println(redis.keys("*"));
	}

	public static void demoVar() {
		System.out.println(redis.get("name"));
		redis.set("name", "Mr.Gold");
		System.out.println(redis.get("name"));
	}
	
	public static void demoList() {
		redis.lpush("city-list", "Beiging");
		redis.lpush("city-list", "Wuhang");
		redis.rpush("city-list", "Shanghai");
		redis.rpush("city-list", "New York");
		System.out.println(redis.lrange("city-list", 0, -1));
	}
}

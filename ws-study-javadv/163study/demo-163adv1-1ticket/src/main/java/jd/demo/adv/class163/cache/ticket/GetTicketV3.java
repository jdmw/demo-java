package jd.demo.adv.class163.cache.ticket;

import jd.demo.adv.class163.cache.ticket.service.TicketService;
import jd.util.StrUt;
import jd.util.lang.Console;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GetTicketV3 {

    //@Autowired
    StringRedisTemplate mainRedisTemplate ;
    StringRedisTemplate bakRedisTemplate;

    //@Autowired
    TicketService service;

    public GetTicketV3(TicketService service, StringRedisTemplate mainRedisTemplate,StringRedisTemplate bakRedisTemplate) {
        this.mainRedisTemplate = mainRedisTemplate;
        this.bakRedisTemplate = bakRedisTemplate;
        this.service = service;
    }

    // 锁的精度更细，每个班次一把锁
   Map<String,String> map = new ConcurrentHashMap<>();

    public int getTicket(String line){
        String key = "ticket."+line ;
        String left = mainRedisTemplate.opsForValue().get(key);
        if(StrUt.isNotBlank(left)){
            Console.tpln("[%t]get from redis : " + left);
            return Integer.parseInt(left.trim());
        }


        // 拿到锁的线程访问db，没拿到锁降级
        // 降级策略可以是访问另一个时效性较差的缓存，可以是访问较慢的缓存，甚至可以是给定固定值、展示等待提示等，总之降低服务要求
        boolean lock = false ;
        try{
            lock = map.putIfAbsent(key,"true") == null;

            if(lock){
                int avaliable = service.getTicket(line).getAvaliable();
                Console.tpln("[%t]get from mysql : " + avaliable);
                mainRedisTemplate.execute((RedisCallback<? extends Boolean>) con->{
                    return con.setEx(key.getBytes(),2,String.valueOf(avaliable).getBytes());
                });
                bakRedisTemplate.opsForValue().set(key,avaliable+"");
                return avaliable;
            }else{
                left = bakRedisTemplate.opsForValue().get(key);
                if(StrUt.isNotBlank(left)){
                    Console.tpln("[%t]get from redis : " + left);
                    return Integer.parseInt(left.trim());
                }else {
                    return 0 ;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(lock){
                map.remove(key);
            }
        }
        return  0 ;
    }
}

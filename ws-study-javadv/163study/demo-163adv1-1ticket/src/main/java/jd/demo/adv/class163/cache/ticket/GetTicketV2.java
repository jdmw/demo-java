package jd.demo.adv.class163.cache.ticket;

import jd.demo.adv.class163.cache.ticket.service.TicketService;
import jd.util.StrUt;
import jd.util.lang.Console;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GetTicketV2 {

    //@Autowired
    StringRedisTemplate redisTemplate ;

    //@Autowired
    TicketService service;

    public GetTicketV2(TicketService service, StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.service = service;
    }

    Lock lock = new ReentrantLock();

    public int getTicket(String line){
        String key = "ticket."+line ;
        String left = redisTemplate.opsForValue().get(key);
        if(StrUt.isNotBlank(left)){
            Console.tpln("[%t]get from redis : " + left);
            return Integer.parseInt(left.trim());
        }


        // 为防止Redis雪崩，进行加锁，获得锁的线程才能访问DB，防止大量线程同时访问DB
        // (线程数量不要配置过大，CPU核心数1-2倍即可)(163老师说的是1倍，1-2倍是自己的理解，阿里dubbo研发APP上游线程数确定的计算公式)

        try{
            lock.lock();

            // 拿到锁后再查一次redis，否则的所有竞争锁的线程都会访问DB
            left = redisTemplate.opsForValue().get(key);
            if(StrUt.isNotBlank(left)){
                Console.tpln("[%t]get from redis : " + left);
                return Integer.parseInt(left.trim());
            }
            int avaliable = service.getTicket(line).getAvaliable();
            Console.tpln("[%t]get from mysql : " + avaliable);
            redisTemplate.execute((RedisCallback<? extends Boolean>) con->{
                return con.setEx(key.getBytes(),2,String.valueOf(avaliable).getBytes());
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return  0 ;
    }
}

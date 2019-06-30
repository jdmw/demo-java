package jd.demo.adv.class163.cache.ticket;

import jd.demo.adv.class163.cache.ticket.service.TicketService;
import jd.util.StrUt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

public class GetTicketV1 {

    //@Autowired
    StringRedisTemplate redisTemplate ;

    //@Autowired
    TicketService service;

    public GetTicketV1(TicketService service,StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.service = service;
    }

    private int getTicket(String line){
        String key = "ticket."+line ;
        // 先从redis取，redis没有才访问DB，Redis数据有时效性，过期会自动清除
        String left = redisTemplate.opsForValue().get(key);
        if(StrUt.isNotBlank(left)){
            return Integer.parseInt(left.trim());
        }

        int avaliable = service.getTicket(line).getAvaliable();
        redisTemplate.execute((RedisCallback<? extends Boolean>) con->{
            return con.setEx(key.getBytes(),120,String.valueOf(avaliable).getBytes());
        });
        return  avaliable;
    }
}

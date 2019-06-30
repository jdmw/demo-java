package jd.demo.adv.class163.cache.ticket;

import com.alibaba.druid.pool.DruidDataSource;
import jd.demo.adv.class163.cache.ticket.config.Config;
import jd.demo.adv.class163.cache.ticket.service.TicketService;
import jd.demo.common.JdbcConst;
import jd.util.StrUt;
import jd.util.lang.Console;
import jd.util.lang.concurrent.CcUt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.CountDownLatch;


@SpringBootApplication
@ComponentScan(basePackages={"jd.demo.adv.class163.cache.ticket.service"})
public class DemoTicketApplication implements CommandLineRunner {


    @Resource
    StringRedisTemplate redisTemplate ;

    @Autowired
    TicketService service;

    @Override
    public void run(String... args) throws Exception {
        String line = "G42" ;
        //GetTicketV2 gt = new GetTicketV2(service,redisTemplate);
        GetTicketV3 gt = new GetTicketV3(service,redisTemplate,new Config().bakRedisTemplate());
        //System.out.format("ticket %s : %s\n",line,getTicket(line));
        CcUt.join(CcUt.latchedRun(100,()->{
            Console.tpln("[%t]ticket %s   : %s\n",line,gt.getTicket(line));
        }));
    }


    public static void main(String[] args) {
        SpringApplication.run(DemoTicketApplication.class, args);
        //System.out.print(Integer.parseInt("99"));
/*        Demo163adv11ticketApplication a = new Demo163adv11ticketApplication();
        TicketService service = new TicketService();
        service.setJt(a.jdbcTemplate(a.dataSource()));
        System.out.println(service.getTicket("G106"));*/


    }



}

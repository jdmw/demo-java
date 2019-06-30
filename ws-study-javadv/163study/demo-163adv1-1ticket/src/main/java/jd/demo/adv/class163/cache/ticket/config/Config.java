package jd.demo.adv.class163.cache.ticket.config;

import com.alibaba.druid.pool.DruidDataSource;
import jd.demo.common.JdbcConst;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class Config {

    //@Bean
    //@ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(JdbcConst.DRIVER);
        ds.setUrl(JdbcConst.DB_URL);
        ds.setUsername(JdbcConst.USERNAME);
        ds.setPassword(JdbcConst.PASSWORD);
        return ds;
    }

    /*@Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }*/



    //@Bean
    private RedisConnectionFactory getConnectionFactory(int port) {
        // 单点redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        // 哨兵redis
        // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
        // 集群redis
        // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        redisConfig.setHostName("localhost");
        redisConfig.setPort(port);
        return new JedisConnectionFactory(redisConfig);
    }


    //@Bean(name="mainRedisTemplate")
    public StringRedisTemplate bakRedisTemplate(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getConnectionFactory(6380));
        return template;
    }



/*
    @Bean(name="bakRedisTemplate")
    //@Primary
    @ConfigurationProperties(prefix = "spring.redis.bak")
    public RedisStandaloneConfiguration bakRedisConfig(){
        return new RedisStandaloneConfiguration();
    }

    @Bean(name="bakRedisTemplate")
    public StringRedisTemplate bakRedisTemplate(RedisStandaloneConfiguration bakRedisConfig){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(new LettuceConnectionFactory());
        return template;
    }

*/


}

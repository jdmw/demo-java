package jd.demo.springboot.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *  mybatisPlus 配置类，使其加载配置文件
 * @author oKong
 *
 */
@Configuration
@ImportResource(locations = {"classpath:/mybatis/spring-mybatis.xml"})
//@MapperScan("cn.lqdev.learning.springboot.chapter9.biz.dao")
//@EnableTransactionManagement
public class MybatisBoot {

	public static void main(String[] args) {
		SpringApplication.run(MybatisBoot.class, args);

	}

}

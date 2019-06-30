package jd.demo.dubbo.demos.c1startcheck;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import jd.demo.dubbo.common.service.IHelloService;
import jd.demo.dubbo.hello.ancfg.provider.DubboConfiguration;
import jd.demo.dubbo.hello.ancfg.provider.DubboProviderLauncher;
import jd.util.lang.concurrent.CcUt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Configuration
class DubboStartCheckAnConfig extends DubboConfiguration{ }

@SpringBootApplication
@DubboComponentScan(basePackages = {"jd.demo.dubbo.demos.c1startcheck"})
public class DubboStartCheckAnConfigApplication implements CommandLineRunner {


    @Reference(retries = 3)
    IHelloService service;

    public static void main(String[] args) throws IOException, InterruptedException {
        // start provider
        //CountDownLatch latch = CcUt.startAndWait(()-> DubboProviderLauncher.main(args));
        //CcUt.start(()->DubboProviderLauncher.main(args),true);
        // start Consumer
        SpringApplication.run(DubboStartCheckAnConfigApplication.class,args);

    }

    @Override
    public void run(String... args) throws Exception {
        //System.in.read();
        System.out.println(service.sayHello("dubbo"));
    }
}

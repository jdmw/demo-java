package jd.demo.spring.cloud.netflix.eureka.server;

import com.netflix.eureka.registry.AbstractInstanceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        // service registry
        AbstractInstanceRegistry registry;
        // renew
        //  step 1 get instance of by serviceId, renewLeaseInfo
        //  step 2 replicateToPeers : com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.replicateToPeers()
        SpringApplication.run(EurekaServerApplication.class, args);


        // get Instances registered with Eureka : http://localhost:8761/
        // org.springframework.cloud.netflix.eureka.server.EurekaController.populateApps
        //  -> invoke PeerAwareInstanceRegistryImpl.getSortedApplications()
    }

}


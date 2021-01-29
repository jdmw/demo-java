package jd.demo.spring.cloud.netflix.eureka.server;

import com.netflix.eureka.registry.AbstractInstanceRegistry;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    // change port,run: EurekaServerApplication -Dserver.port=8762 -Deureka.client.registerWithEureka=true -Deureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka,http://localhost:8762/eureka

    public static void main(String[] args) {
        // service registry
        AbstractInstanceRegistry registry;
        // renew
        //  step 1 get instance of by serviceId, renewLeaseInfo
        //  step 2 replicateToPeers : com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.replicateToPeers()
        new SpringApplicationBuilder(EurekaServerApplication.class).web(WebApplicationType.SERVLET).run(args);

        // get Instances registered with Eureka : http://localhost:8761/
        // org.springframework.cloud.netflix.eureka.server.EurekaController.populateApps
        //  -> invoke PeerAwareInstanceRegistryImpl.getSortedApplications()

        //com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.replicateToPeers()
    }

}


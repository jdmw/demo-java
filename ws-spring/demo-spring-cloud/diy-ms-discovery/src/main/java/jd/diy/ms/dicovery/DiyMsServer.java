package jd.diy.ms.dicovery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.eureka.registry.AbstractInstanceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.netflix.appinfo.InstanceInfo;

//import com.netflix.appinfo.InstanceInfo;

@SpringBootApplication
public class DiyMsServer {

    public static void main(String[] args) throws JsonProcessingException {
        // service registry
        AbstractInstanceRegistry registry;
        // renew
        //  step 1 get instance of by serviceId, renewLeaseInfo
        //  step 2 replicateToPeers : com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.replicateToPeers()
        SpringApplication.run(DiyMsServer.class, args);


        // get Instances registered with Eureka : http://localhost:8761/
        // org.springframework.cloud.netflix.eureka.server.EurekaController.populateApps
        //  -> invoke PeerAwareInstanceRegistryImpl.getSortedApplications()

        //com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.replicateToPeers()
    }

}


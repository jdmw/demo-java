package jd.diy.ms.discovery.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import jd.diy.ms.discovery.structs.AppBasicInfo;
import jd.util.StrUt;
import jd.util.io.IOUt;
import jd.util.io.net.NetUtil;
import jd.util.io.net.http.HttpUt;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class JdDiscoveryClient {

    private static final String SERVICE_URL_DEFAULT_ZONE = "http://localhost:8761/eureka/" ;
    private static final int DEFAULT_LEASE_RENEWAL_INTERVAL_IN_SECS = 30 ;
    private static final int DEFAULT_LEASE_DURATION_IN_SECS = 90 ;

    private String serviceUrlZone = SERVICE_URL_DEFAULT_ZONE;
    private final AppBasicInfo bi ;
    private volatile InstanceInfo instanceInfo ;

    public JdDiscoveryClient( @NonNull AppBasicInfo bi){
        this.bi = bi ;
    }
    private Socket connect()  {
        try {
            URL url = new URL(serviceUrlZone);
            String remoteHost = url.getHost();
            int port = url.getPort();
            Socket socket = new Socket(remoteHost,port);
            return socket ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InstanceInfo getInstanceInfo(){
        if ( instanceInfo == null){
            InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder();
            if(bi.getPort() != null){
                builder.setPort(bi.getPort());
            }
            if(bi.getSecurePort() != null){
                builder.setSecurePort(bi.getSecurePort());
            }
            String ipAddress ;
            String hostName ;
            Socket socket = null;
            try{
                socket = connect() ;
                ipAddress = NetUtil.ipV4AddressToText(socket.getLocalAddress().getAddress());
                try{
                    hostName = socket.getLocalAddress().getHostName() ;
                    /*if(Objects.equals(hostName,"localhost") || Objects.equals(hostName,"127.0.0.1")){
                        hostName = InetAddress.getLocalHost().getHostName();
                    }*/
                }catch (Exception e){
                    hostName = ipAddress ;
                }
            }finally {
                IOUt.close(socket);
            }
            //InetAddress.getLocalHost().getHostName();
            //socket.getLocalAddress().getHostName();
            builder.setHostName(hostName);
            builder.setIPAddr(ipAddress);
            String appName = StrUt.isBlank(bi.getAppName()) ? hostName: bi.getAppName();
            builder.setInstanceId(ipAddress +":" + appName + ":" + builder.getRawInstance().getPort());
            builder.setAppName(hostName.toUpperCase());
            builder.setDataCenterInfo(() -> DataCenterInfo.Name.MyOwn);

            /**
             * "leaseInfo": {
             *      "renewalIntervalInSecs": 30,
             *      "durationInSecs": 90,
             *      "registrationTimestamp": 0,
             *      "lastRenewalTimestamp": 0,
             *      "evictionTimestamp": 0,
             *      "serviceUpTimestamp": 0
             *  },
             */
            builder.setLeaseInfo(new LeaseInfo(DEFAULT_LEASE_RENEWAL_INTERVAL_IN_SECS,DEFAULT_LEASE_DURATION_IN_SECS,
                    0L,0L,0L,0,0));
            Map<String,String> metadata = new ConcurrentHashMap<String, String>();
            metadata.put( "management.port", String.valueOf(builder.getRawInstance().getPort()));
            builder.setMetadata(metadata);
            /**
             * "homePageUrl": "http://10.10.60.219:8770/",
             *         "statusPageUrl": "http://10.10.60.219:8770/actuator/info",
             *         "healthCheckUrl": "http://10.10.60.219:8770/actuator/health",
             *         "vipAddress": "eureka-client",
             *         "secureVipAddress": "eureka-client"
             */
            builder.setHomePageUrl( "/",null);
            builder.setStatusPageUrl("/actuator/info",null);
            builder.setHealthCheckUrls("/actuator/health",null,null);
            builder.setVIPAddress(appName.toLowerCase());
            builder.setSecureVIPAddress(appName.toLowerCase());
            synchronized (this){
                if(instanceInfo == null){
                    instanceInfo = builder.build();
                }
            }
        }
        return instanceInfo ;

    }

    public static void main(String[] args) throws JsonProcessingException, UnknownHostException, ConnectException {
        String appName = InetAddress.getLocalHost().getHostName();
        JdDiscoveryClient client = new JdDiscoveryClient(AppBasicInfo.builder().appName(appName).port(7001).build());
        InstanceInfo instanceInfo = client.getInstanceInfo();
        ObjectMapper mapper = new ObjectMapper();

        // 写为字符串
        Map<String,Object> body = new HashMap<>();
        body.put("instance",instanceInfo);
        String text = mapper.writeValueAsString(body);
        System.out.println(text);
        HttpUt.sendPost(SERVICE_URL_DEFAULT_ZONE + "apps/" + appName, text);
    }
}

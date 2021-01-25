package jd.diy.ms.dicovery.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.eureka.lease.Lease;
import com.sun.jersey.client.impl.CopyOnWriteHashMap;
import jd.diy.ms.dicovery.registry.struct.GetAppsApplicationVO;
import jd.diy.ms.dicovery.registry.struct.InstanceInfo;
import jd.diy.ms.dicovery.registry.struct.InstanceRegisterParam;
import jd.util.lang.concurrent.ThreadSafeCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//import com.netflix.appinfo.InstanceInfo;

/**
 * Created by huangxia on 2008/1/22.
 */
@RestController
@RequestMapping("/eureka")
@Slf4j
public class MsRegistryServerController {

    private final ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registryMap = new ConcurrentHashMap();
    private final ThreadSafeCounter counter = new ThreadSafeCounter();

    @PostMapping("/apps/{appName}")
    public void register(@PathVariable(value = "appName",required = true) String appName,
                         @RequestBody String paramBody) throws JsonProcessingException {
        //InstanceRegisterParam param
        //InstanceInfo instance = param.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        InstanceRegisterParam param = mapper.readValue(paramBody, InstanceRegisterParam.class);
        InstanceInfo instance = param.getInstance();
        log.debug("Registered instance {}/{} with status {} (replication=false)",appName, instance.getInstanceId(), instance.getStatus());

        Map<String, Lease<InstanceInfo>> instanceLeaseMap = registryMap.get(appName);
        if(instanceLeaseMap == null){
            instanceLeaseMap = new CopyOnWriteHashMap<>();
            registryMap.put(appName,instanceLeaseMap);
        }
        String instanceId = getInstanceId(instance);
        Lease<InstanceInfo> existedLease= instanceLeaseMap.get(instanceId);
        if(existedLease != null && instance.getLastDirtyTimestamp() != null && instance.getLastDirtyTimestamp() > 0 &&
                existedLease.getHolder().getLastDirtyTimestamp() > instance.getLastDirtyTimestamp()){
            log.warn("There is an existing lease and the existing lease\'s dirty timestamp {} is greater than the one that is being registered {}",existedLease,instance);
            // TODO not sure
            existedLease.renew();
            existedLease.getHolder().setLastUpdatedTimestamp();
            return;
        }
        instance.setLastUpdatedTimestamp();
        Lease<InstanceInfo> lease = new Lease<>(instance,Lease.DEFAULT_DURATION_IN_SECS);
        instance.setLeaseInfo(LeaseInfo.Builder.newBuilder()
                .setRegistrationTimestamp(instance.getLastUpdatedTimestamp())
                .setRenewalTimestamp(instance.getLastUpdatedTimestamp())
                .setServiceUpTimestamp(instance.getLastUpdatedTimestamp()).build());

        instance.setActionType(InstanceInfo.ActionType.ADDED);

        instanceLeaseMap.put(instanceId,lease);
        counter.countUp();
    }

    private String getInstanceId(InstanceInfo instanceInfo){
        int port = instanceInfo.isPortEnabled(InstanceInfo.PortType.SECURE) ? instanceInfo.getSecurePort() : instanceInfo.getPort();
        return String.format("%s:%s:%d",instanceInfo.getIPAddr(),instanceInfo.getAppName().toLowerCase(), port);
    }

    @PutMapping("/apps/{appName}/{instanceId}")
    public void renew( HttpServletResponse response,
                      @PathVariable(value = "appName",required = true) String appName,
                      @PathVariable(value = "instanceId",required = true) String instanceId,
                      // status : UP / DOWN
                      @RequestParam String status,
                      @RequestParam long lastDirtyTimestamp) throws IOException {
        Lease<InstanceInfo> instanceInfoLease = null;
        Map<String, Lease<InstanceInfo>> instanceLeaseMap = registryMap.get(appName);
        if (instanceLeaseMap != null){
            instanceInfoLease = instanceLeaseMap.get(instanceId);
        }
        if(instanceInfoLease == null){
            // TODO error : after 404 not found status code sent, provider should register itself again
            // but actually it doesn't work ,it request for this url again
            response.sendError(404);
            return;
        }else{
            synchronized (registryMap){
                if("UP".equals(status)){
                    instanceInfoLease.renew();
                    instanceInfoLease.getHolder().setLastUpdatedTimestamp();
                    instanceInfoLease.getHolder().setLastDirtyTimestamp(lastDirtyTimestamp);
                    if(!Objects.equals(InstanceInfo.InstanceStatus.UP,instanceInfoLease.getHolder().getStatus())){
                        instanceInfoLease.serviceUp();
                        //counter.countUp();
                    }
                    instanceInfoLease.getHolder().setStatus(InstanceInfo.InstanceStatus.UP);
                    instanceInfoLease.getHolder().setLeaseInfo(LeaseInfo.Builder.newBuilder()
                            .setEvictionTimestamp(instanceInfoLease.getEvictionTimestamp())
                            .setDurationInSecs(instanceInfoLease.DEFAULT_DURATION_IN_SECS)
                            .setRegistrationTimestamp(instanceInfoLease.getRegistrationTimestamp())
                            //.setRenewalIntervalInSecs(0)
                            .setServiceUpTimestamp(instanceInfoLease.getServiceUpTimestamp()).build());
                }else if("DOWN".equals(status)){
                    instanceInfoLease.cancel();
                    if(!Objects.equals(InstanceInfo.InstanceStatus.DOWN,instanceInfoLease.getHolder().getStatus())){
                        //counter.countUp();
                    }
                    instanceInfoLease.getHolder().setStatus(InstanceInfo.InstanceStatus.DOWN);
                }else{
                    return;
                }
            }
            // TODO ? if status does change ,should version countered up
            counter.countUp();
            // TODO other status
            response.setStatus(200);
        }
    }


    @DeleteMapping("/apps/{appName}/{instanceId}")
    public void delete(@PathVariable(value = "appName",required = true) String appName,
                       @PathVariable(value = "instanceId",required = true) String instanceId,
                       HttpServletResponse response) throws IOException {
        Map<String, Lease<InstanceInfo>> instanceLeaseMap = registryMap.get(appName);
        if (instanceLeaseMap != null && !instanceLeaseMap.isEmpty()){
            Lease<InstanceInfo> old = instanceLeaseMap.remove(instanceId);
            if(old != null){
                counter.countUp();
                return;
            }
        }
        response.sendError(404);
    }


    /**
     *
     * get all apps
     *
     * return {
     *    "applications": {
     *      "versions__delta": "74",
     *      "apps__hashcode": "UP_3_",
     *      "application": [
     *      {
     *         "name": "EUREKA-CLIENT-A",
     *         "instance": [{}]
     *      }]
     *    }
     * }
     * @param response
     * @throws IOException
     */
    @GetMapping("/apps")
    public String getApps(HttpServletRequest request,HttpServletResponse response) throws IOException {
        long version = counter.getCount();
        String result = doGetApps(true);
        request.getSession().setAttribute(SESSION_ATTR_VERSION,version);
        return result;
    }

    @GetMapping("/apps/delta")
    public String getAppsDelta(HttpServletRequest request,HttpServletResponse response) throws IOException {
        long version = counter.getCount();
        Long lastVersion = (Long) request.getSession().getAttribute(SESSION_ATTR_VERSION);
        // take care : if server restart( lastVersion is null) , should return all apps
        boolean delta = lastVersion != null && lastVersion < version ;
        String result = doGetApps(!delta);
        request.getSession().setAttribute(SESSION_ATTR_VERSION,version);
        return result;
    }

    private final static String SESSION_ATTR_VERSION = "APPS_VERSION" ;


    private String doGetApps(boolean hasAppInfos) throws JsonProcessingException {
        Map<String,Object> applicationInfo = new HashMap();
        applicationInfo.put("versions__delta", String.valueOf(counter.getCount()));
        applicationInfo.put("apps__hashcode", "UP_" + evict() + "_");
        List<GetAppsApplicationVO> applications = new ArrayList<>();
        if(hasAppInfos){
            registryMap.forEach((k,v)->{
                if(v != null && !v.isEmpty()){
                    List<InstanceInfo> list = v.values().stream().map(Lease::getHolder)
                            .sorted(Comparator.comparing(InstanceInfo::getInstanceId)).collect(Collectors.toList());
                    applications.add(new GetAppsApplicationVO(k,list));
                }
            });
        }
        applicationInfo.put("application",applications);
        Map<String,Object> result = new HashMap<>();
        result.put("applications",applicationInfo);
        return new ObjectMapper().writeValueAsString(result);
    }

    private int evict(){
        List<String> activeApps = new ArrayList<>();
        registryMap.forEach((appName,map)->{
            map.values().parallelStream().forEach(lease->{
                if(lease.isExpired()){
                    lease.getHolder().setStatus(InstanceInfo.InstanceStatus.DOWN);
                }else{
                    activeApps.add(lease.getHolder().getInstanceId());
                }
            });
        });
        return activeApps.size();
    }
}

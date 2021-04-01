package jd.diy.ms.discovery.structs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppBasicInfo {
    private Integer port ;
    private Integer securePort ;
    private String appName ;
}

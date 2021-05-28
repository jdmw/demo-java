package jd.demo.lib.json.jackson.deserialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.Data;
import lombok.ToString;

/**
 * Created by hubery on 2021/4/2.
 */
public class DemoFastJson {
    /**
     * FastJson下划线转驼峰
     1 parse（字符串转对象）的时候 设置下划线转驼峰
        ParserConfig.getGlobalInstance().propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        JSON.parseObject(str, RespOrderDTO.class);

     2 toString（对象转字符串）的时候，设置下划线转驼峰
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String json = JSON.toJSONString(user, config);

     3 单个字段 设置下划线转驼峰
        @JSONField(name="user_name")
     */
    @Data
    @ToString
    public static final class VO {
        int aB ;
        @JSONField(name="_c")
        int c ;
    }
    public static void main(String args[]) throws Exception{
        // global :
        // ParserConfig.getGlobalInstance().propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

        ParserConfig config = new ParserConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        VO a = JSON.parseObject("{\"a_b\" : 1,\"_c\" : 2}",VO.class,config);
        System.out.println(a);

        SerializeConfig config1 = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String json = JSON.toJSONString(a, config1);
        System.out.println(json);
    }
}

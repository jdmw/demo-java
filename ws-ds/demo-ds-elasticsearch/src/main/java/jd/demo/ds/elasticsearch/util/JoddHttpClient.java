package jd.demo.ds.elasticsearch.util;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.util.Map;



public class JoddHttpClient {

    public  static String get(String url){
        return get(url,null);
    }
    /**
     *  发送get请求
     * @param url
     * @param params 无参数传递null
     * @return
     */
    public  static String get(String url, Map<String,String> params) {
        HttpRequest request = HttpRequest.get(url);
        if(params != null && !params.isEmpty()){
            request.query(params);
        }
        HttpResponse response = request.send();
        String bodyText = response.bodyText();
        bodyText = transFormToUtf8(bodyText);
        return bodyText;
    }

    /**
     *
     * @param url
     * @param obj 发送json格式的参数  无参传递null
     * @return
     */
    public static String postJson(String url, Object obj){
        HttpRequest request = HttpRequest.post(url);
        return sendJson(request,obj);
    }

    public static String putJson(String url, Object obj){
        HttpRequest request = HttpRequest.post(url);
        return sendJson(request,obj);
    }

    private static String sendJson(HttpRequest request, Object obj){
        request.contentType("application/json");
        request.charset("utf-8");
        if(obj != null){
            request.body(JSON.toJSONString(obj));
        }
        HttpResponse response = request.send();
        return response.bodyText();
    }
    /**
     *
     * @param url
     * @param params 发送文本参数 无参传递null
     * @return
     */
    public static String post(String url, Map<String,Object> params){
        HttpRequest request = HttpRequest.post(url);
        if(params != null && !params.isEmpty()) {
            request.form(params);
        }
        HttpResponse response = request.send();
        return response.bodyText();
    }


    /**
     * 字符串转换为utf-8输出
     * @param source
     * @return
     */
    private static String transFormToUtf8(String source) {
        try {
            return new String(source.getBytes(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



}

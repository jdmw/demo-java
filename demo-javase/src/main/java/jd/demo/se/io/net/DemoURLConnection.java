package jd.demo.se.io.net;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.sun.istack.internal.NotNull;
import jd.util.StrUt;
import jd.util.lang.collection.MultiValueMap;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemoURLConnection {

    private static Logger logger = LoggerFactory.getLogger(DemoURLConnection.class);


    public static MultiValueMap<String,String> DEFAULT_HEADERS = new MultiValueMap<>();
    private static final int DEFAULT_CONNECTION_TIMEOUT = 0 ;
    private static final int DEFAULT_READ_TIMEOUT = 0 ;
    static {
        DEFAULT_HEADERS.add("accept", "*/*");
        DEFAULT_HEADERS.add("connection", "Keep-Alive");
        DEFAULT_HEADERS.add("Content-Type", "application/json;charset=UTF-8");
    }


    public static <V extends Object> String sendGet(String url, Map<String,V> params) {
        MultiValueMap<String,Object> multiValueMap = new MultiValueMap<>();
        params.forEach((k,v)->multiValueMap.add(k,v));
        return sendGet(url,multiValueMap);
    }

    public static <V extends Object> String sendGet(String url, String key,List<V> values) {
        MultiValueMap<String,V> multiValueMap = new MultiValueMap<>();
        values.forEach((v)->multiValueMap.add(key,v));
        return sendGet(url,multiValueMap);
    }

    public static <V extends Object> String sendGet(String url, MultiValueMap<String,V> params) {
        return sendGet(url,null,params);
    }
    public static <V extends Object> String sendGet(String url, MultiValueMap<String,String> headers, MultiValueMap<String,V> params) {
        String param = null ;

        if(params != null && !params.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,List<V>> entry : params.entrySet()){
                if(StrUt.isNotBlank(entry.getKey()) && entry.getValue() != null && !entry.getValue().isEmpty()){
                    for (Object v : entry.getValue()) {
                        if(v != null && !"".equals(v)){
                            String value = null;
                            try {
                                value = URLEncoder.encode(v.toString().trim(), Charsets.UTF_8.displayName());
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException("不支持utf8编码",e);
                            }
                            sb.append("&").append(entry.getKey().trim()).append("=").append(value);
                        }
                    }
                }
            }

            if(sb.length() > 1){
                param = sb.toString().substring(1);
            }
        }
        return sendGet(url,headers,param);
    }

    public static String sendGet(String url) {
        return sendGet(url,null,"");
    }
    public static String sendGet(String url, String param) {
        return sendGet(url,null,param);
    }
    public static String sendGet(String url, MultiValueMap<String,String> headers, String param) {
        return doRequest("GET",url,param,headers,DEFAULT_CONNECTION_TIMEOUT,DEFAULT_READ_TIMEOUT);
    }



    public static String sendPostWithJson(String url, Object vo) {
        return sendPost(url, JSONObject.toJSONString(vo));
    }
    public static String sendPost(String url, String param)  {
        return sendPost(url,param,DEFAULT_CONNECTION_TIMEOUT,DEFAULT_READ_TIMEOUT);
    }

    public static String sendPost(String url, String param, int connectionTimeOut, int readTimeout) {
        return doRequest("POST",url,param,null,connectionTimeOut,readTimeout);
    }

    public static String sendPut(@NotNull String url, String param) {
        return doRequest("PUT",url,param,null,DEFAULT_CONNECTION_TIMEOUT,DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送请求
     * @param method
     * @param url
     * @param param
     * @param connectionTimeOut
     * @param readTimeout
     * @return
     */
    private static String doRequest(@NotNull String method, @NotNull String url, String param,MultiValueMap<String,String> headers,
                                    int connectionTimeOut, int readTimeout){
        PrintWriter out = null;
        method = method.toUpperCase();
        boolean doOutput = "POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method) ;
        if( !doOutput && StrUt.isNotBlank(param) ){
            url += (url.contains("?") ? "&" : "?") + param  ;
        }
        logger.info("mini-app request {} url : {} ,param : {} ,connectionTimeOut/readTimeout: {}/{}", method,
                url, param,connectionTimeOut,readTimeout);
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection connection = (HttpURLConnection)conn;
            if(!method.equalsIgnoreCase(connection.getRequestMethod())){
                connection.setRequestMethod(method.toUpperCase());
            }
            // 设置http header
            setHeader(conn,headers);

            // 从服务器获取资源,默认为true
            // conn.setDoInput(true);

            // 允许发送数据,发送POST/PUT请求必须设置doOutput=true
            // 如果未设置requestMethod,当doOutput变为true,将发送post方法,否则使用默认的get方法
            if(doOutput ){
                conn.setDoOutput(true);
            }

            // connectionTimeOut为建立tcp连接的超时时间
            if(connectionTimeOut > 0){
                conn.setConnectTimeout(connectionTimeOut);
            }
            // readTimeout为等待远程服务执行完成返回的超时时间
            if(readTimeout > 0){
                conn.setReadTimeout(readTimeout);
            }

            // 发送请求参数
            if(doOutput && StrUt.isNotBlank(param)){
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                out.print(param);
                // flush输出流的缓冲;对于HTTP协议,flush()操作之后才会发出报文,flush()之前数据是存放在缓存区的
                out.flush();
            }

            if(logger.isDebugEnabled()){
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    logger.debug("mini-app response header " + key + "--->" + map.get(key));
                }
            }

            String result = readInput(conn.getInputStream());
            logger.info("mini-app response {} url : {} result : {}", method,url, result);
            return result ;
        } catch (IOException e) {
            logger.error("mini-app request error 发送"+method+"请求出现异常 url:"+url+",param:"+param,e);
            throw new RuntimeException(e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            IOUtils.close(out);
        }
    }

    /**
     * 设置http header
     * @param conn
     * @param headers
     */
    private static void setHeader(@NotNull URLConnection conn,MultiValueMap<String,String> headers){
        // 设置通用的请求属性
        if(headers == null || headers.isEmpty()){
            headers = DEFAULT_HEADERS ;
        }else{
            List<String> headerNames = headers.keySet().stream().map(String::toLowerCase).collect(Collectors.toList());
            MultiValueMap<String,String> m = headers;
            DEFAULT_HEADERS.forEach((name,value)->{
                if(!headerNames.contains(name.toLowerCase())){
                    m.add(name,DEFAULT_HEADERS.getFirstValue(name));
                }
            });
        }
        headers.forEach((k,vs)->{
            if(k != null && !k.trim().isEmpty() && vs != null && !vs.isEmpty()){
                vs.stream().filter(v-> v != null && !"".equals(v.trim()) )
                        .forEach(v->conn.setRequestProperty(k.trim(),v.trim()));
            }
        });
    }

    /**
     * 以文本方式读取输入流,注意读取完会关闭输入流
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readInput(@NotNull InputStream inputStream) throws IOException {
        StringBuffer sb = new StringBuffer();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))){
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();

    }
    public static String sendHttpPost(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        StringEntity str = new StringEntity(body,"UTF-8");
        httpPost.setEntity(str);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        logger.info(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        logger.info(responseContent);

        response.close();
        httpClient.close();
        return responseContent;
    }

    public static String sendHttpPatch(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPatch = new HttpPut(url);
        httpPatch.addHeader("Content-Type", "application/json");
        StringEntity str = new StringEntity(body,"UTF-8");
        httpPatch.setEntity(str);

        CloseableHttpResponse response = httpClient.execute(httpPatch);
        logger.info(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        logger.info(responseContent);

        response.close();
        httpClient.close();
        return responseContent;
    }
}


package jd.demo.http.apache.httpcomponets;

import jodd.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;


public class DemoMultipartFormEntity {

    private static final File FILE= new File("/Users/jdmw/Downloads/docx/2.docx") ;
    private static final String COMPANY_ID = "4" ;

    public static String postMultipartForm(String url,Map<String,Object> parameter,ContentType contentType, String fileName) throws IOException{
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost(url);

            if(parameter != null && !parameter.isEmpty()){
                if( fileName == null){
                    for(Map.Entry<String,Object> entry : parameter.entrySet()){
                        if ( entry.getKey().toUpperCase().equals("FILENAME") && entry.getValue() instanceof String){
                            fileName = entry.getValue().toString();
                        }
                    }
                }
                String filename = fileName;
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                parameter.forEach((name,value)->{
                    if( value != null){
                        ContentBody contentBody;
                        if( value instanceof String){
                            contentBody = new StringBody( value.toString(), ContentType.TEXT_PLAIN);
                        }else if( value instanceof File) {
                            if( contentType != null && StringUtil.isNotBlank(filename)){
                                contentBody = new FileBody((File) value,contentType,filename);
                            }else{
                                contentBody = new FileBody((File) value);
                            }
                        }else if( value instanceof byte[]) {
                            if( filename == null) {
                                throw new RuntimeException("filename不可为空" );
                            }
                            if( contentType != null && StringUtil.isNotBlank(filename)){
                                contentBody = new ByteArrayBody((byte[]) value,contentType,filename);
                            }else{
                                contentBody = new ByteArrayBody((byte[]) value,filename);
                            }
                        }else if( value instanceof InputStream) {
                            if( contentType != null){
                                contentBody = new InputStreamBody((InputStream) value,contentType,filename);
                            }else{
                                contentBody = new InputStreamBody((InputStream) value,filename);
                            }
                        }else if( value instanceof MultipartFile) {
                            MultipartFile multipartFile = (MultipartFile)value;
                            byte[] bytes ;
                            try {
                                bytes = multipartFile.getBytes();
                            } catch (IOException e) {
                                throw new RuntimeException("上传文件读取异常",e);
                            }
                            contentBody = new ByteArrayBody(bytes,multipartFile.getContentType(),multipartFile.getOriginalFilename());
                        }else if( value instanceof ContentBody) {
                            contentBody = (ContentBody)value ;
                        }else {
                            // 其他参数统统转化为String
                            contentBody = new StringBody( value.toString(), ContentType.TEXT_PLAIN);
                        }
                        builder.addPart(name,contentBody);
                    }
                });

                HttpEntity reqEntity = builder.build();
                httppost.setEntity(reqEntity);
            }

            System.out.println("executing request " + httppost.getRequestLine());
            try(CloseableHttpResponse response = httpclient.execute(httppost)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                try(InputStream is = resEntity.getContent()){
                    return IOUtils.toString(is,"UTF-8");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        byte[] bytes = "\211PNG\r\n\032\n".getBytes();
        String url = "http://localhost:11027/v4/profile/upload/parser";
        Map<String, Object> parameter = new LinkedHashMap<>();
        //parameter.put("file", FILE);
        //parameter.put("file", new FileInputStream(FILE));
        parameter.put("file", IOUtils.toByteArray(new FileInputStream(FILE)));
        parameter.put("filename", FILE.getName());
        parameter.put("companyId", COMPANY_ID);
        System.out.println(postMultipartForm(url, parameter,ContentType.DEFAULT_BINARY,FILE.getName()));
    }
    /*String url = "";
    HttpPost httpPost = new HttpPost(url);

    CloseableHttpClient client = HttpClients.createDefault();
    String respContent = null;
    Gson gson = new Gson();



    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("json", "{}", ContentType.MULTIPART_FORM_DATA);

    HttpEntity multipart = builder.build();

    HttpResponse resp = null;
        httpPost.setEntity(multipart);
        resp = client.execute(httpPost);

        //注意，返回的结果的状态码是302，非200
        if (resp.getStatusLine().getStatusCode() == 302) {
            HttpEntity he = resp.getEntity();
            System.out.println("----------------123----------666666---");
            respContent = EntityUtils.toString(he, "UTF-8");
        }
    

        System.out.println("=========================:\t" + respContent);
        System.out.println("=========================:\t" + resp.getStatusLine().getStatusCode());
*/
}

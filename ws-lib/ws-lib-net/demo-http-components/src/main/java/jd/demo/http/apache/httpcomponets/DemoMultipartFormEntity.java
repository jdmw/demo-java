package jd.demo.http.apache.httpcomponets;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DemoMultipartFormEntity {

    public static void main(String[] args) throws IOException {
        if (args.length != 1)  {
            System.out.println("File path not given");
            System.exit(1);
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://localhost:8080" +
                    "/servlets-examples/servlet/RequestInfoExample");

            FileBody bin = new FileBody(new File(args[0]));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("bin", bin)
                    .addPart("comment", comment)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
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

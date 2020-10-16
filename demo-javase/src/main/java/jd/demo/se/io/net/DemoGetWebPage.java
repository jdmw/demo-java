package jd.demo.se.io.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DemoGetWebPage{
   public static void main(String args[]) throws Exception{

      URL url = null;
      String search = "http://www.baidu.com/s?wd=" + URLEncoder.encode("Ԧ��","UTF-8") + "&tn=49029047_oem_dg&ch=33&abar=0";
      InputStream input = null ;
      try{
         //url = new URL("http://www.u8xs.com/html/0/647/index.html");
         //url = new URL("http","www.u8xs.com",81,"/html/0/647/index.html");
         url = new URL(search);
         input = url.openStream();
         input.close();
         System.out.println("This url could be connected.");
         System.out.println("      **************************** ");
         System.out.println("Protocol   :" + url.getProtocol());
         System.out.println("Port       :" + url.getPort());
         System.out.println("DefaultPort:" + url.getDefaultPort());
         System.out.println("Host       :" + url.getHost());
         System.out.println("Path       :" + url.getPath());
         System.out.println("File       :" + url.getFile());
         System.out.println("Content    :" + url.getContent());
         System.out.println("Query part :" + url.getQuery());
         System.out.println("anchor:    " + url.getRef());
         System.out.println("UserInfo:" + url.getUserInfo());
         System.out.println("      ############################ ");
         input = url.openStream();
         input.close();
      }catch(MalformedURLException m){
         System.out.println("Error��can not link to this url");
      }catch(IOException i){
         System.out.println("Error��can not open this connection");
      }
   }
};

/**
url = new URL("http://www.u8xs.com/html/0/647/index.html");
���н��
      **************************** 
Protocol   :http
Port       :-1
DefaultPort:80
Host       :www.u8xs.com
Path       :/html/0/647/index.html
File       :/html/0/647/index.html
Content    :sun.net.www.protocol.http.HttpURLConnection$HttpInputStream@665753
Query part :null
anchor:    null
UserInfo:null
      ############################ 
*/
package jd.demo.business.qiniu.cdn;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by huangxia on 2008/8/28.
 */
public class QiniuUploadDemo {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QiniuSecretKey {
        String accessKey ;
        String secretKey ;
        String bucket  ;
    }

    /**
     * 上传文件
     * @param fileContent 二进制文件内容
     * @param filePath 文件夹位置
     * @param sk 七牛ak/sk/bucket
     * @return
     */
    public static String uploadByteArrays(@NonNull byte[] fileContent,@NonNull String filePath, @NonNull QiniuSecretKey sk){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath + "/" + UUID.randomUUID() + ".log";
        Auth auth = Auth.create(sk.accessKey, sk.secretKey);
        String upToken = auth.uploadToken(sk.bucket);
        try {
            Response response = uploadManager.put(fileContent, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null ;
    }

    public static void listFiles(@NonNull QiniuSecretKey sk,String prefix){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(sk.accessKey, sk.secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        if(prefix == null){
            prefix = "";
        }
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(sk.bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                System.out.println(item.key);
                // System.out.println(item.hash);
                // System.out.println(item.fsize);
                // System.out.println(item.mimeType);
                // System.out.println(item.putTime);
                // System.out.println(item.endUser);
            }
        }
    }
    // command : {0} -ak xxx -sk xxx -bucket xxx
    // example : -ak XZkSId0xAE1sWt2ABdJXJpMkb8gMcToNZ6TeCvaJ -sk obCdn6xxbil61KkCQX7aEDlNKeFurFlXZ0atzqVbH -bucket jdmw
    public static void main(String[] args){
        String accessKey = "" ;
        String secretKey = "" ;
        String bucket = "" ;
        for (int i = 0; i < args.length-1; i+=2) {
            switch (Objects.toString(args[i])){
                case "-ak" : accessKey = args[i+1];break;
                case "-sk" : secretKey = args[i+1];break;
                case "-bucket" : bucket = args[i+1];break;
            }
        }
        if(accessKey.isEmpty() || secretKey.isEmpty() || bucket.isEmpty() ){
            System.err.println("args empty, please input args : -ak xxx -sk xxx -bucket xxx");
        }

        QiniuSecretKey qiniuSecretKey = new QiniuSecretKey(accessKey, secretKey, bucket);
        listFiles(qiniuSecretKey,"upload");
        //String key = uploadByteArrays("Hello qiniu".getBytes(), "upload", qiniuSecretKey);
        //System.out.println("upload---key:"+key);
    }
}


/**
 result:

 key:upload/28490fa2-6bf7-4011-a15e-1104cbc468b8.log hash : FlN8o9kf_iUA6ZaUorVXhhrvqiFV



 */
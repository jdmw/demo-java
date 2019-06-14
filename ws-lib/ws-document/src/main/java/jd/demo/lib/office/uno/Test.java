package jd.demo.lib.office.uno;


import com.artofsolving.jodconverter.*;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ConnectException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

    private static final String ERROR_PDF = "Evaluation Only. Created with Aspose.Words. Copyright 2003-2015 Aspose Pty Ltd.";

    //private static final String COMMAND = "libreoffice --invisible --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$";
    //private static final String COMMAND = "xvfb-run -a -s '-screen 0 640x480x16'  libreoffice --invisible --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$";
    //private static final String COMMAND = "soffice --invisible --convert-to pdf:writer_pdf_Export $src$ --outdir $outdir$";
    // libreoffice卡死进程，改成soffice
    private static final String COMMAND = "soffice --headless --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$ ";

    private static DocumentFormat DOCX_FMT = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT,
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
    private static DefaultDocumentFormatRegistry DOC_FMT_REGISTRY = new DefaultDocumentFormatRegistry();
    static{
        DOC_FMT_REGISTRY.addDocumentFormat(DOCX_FMT);
    }

    private static final int UNO_PORT = 8100;
    private static final String UNO_LIS_CMD = "soffice --headless --accept=\"socket,host=127.0.0.1,port="+UNO_PORT+";urp;\" --nofirststartwizard &";

    static{
        //try {
            new Thread(() -> {
                try {
                    executeCommand(UNO_LIS_CMD.split(" "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            //startLibreOfficeUnoServer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public static void main(String[] args) throws IOException {
        String dir = "/mnt/nfs/attachment/profile_attachement/20196";
        String file = "f2dff92d-5850-41d0-9313-19ea3aa22366";
        dir = "/Users/huangxia/Downloads/docx";
        file = "3";

        if (args.length > 0) {
            dir = args[0];
            file = args[1];
        }

        convertThroughUNO(new File(dir, file + ".docx"), new File(dir, file+".pdf"));
        System.in.read();
    }

    public static Process startLibreOfficeUnoServer() throws IOException {
        Runtime r = Runtime.getRuntime();
        System.out.println(System.currentTimeMillis() + "|execute: " + UNO_LIS_CMD);

        executeCommand("ps -aA |grep soffice".split(" "));
        // 启动soffice监听端口
        final Process process = r.exec(UNO_LIS_CMD);
        System.out.println(System.currentTimeMillis() + "|alive: " +process.isAlive());
        // JVM停止时自动结束线程
        r.addShutdownHook(new Thread(()->{
            System.out.println(System.currentTimeMillis() + "|jvm shutdown," +  (process.isAlive()?" to close":"dead") + " processs : " + UNO_LIS_CMD  + "");
            if(process.isAlive()) process.destroy();
        }));
        System.out.println(System.currentTimeMillis() + "|alive: " +process.isAlive());
        // 等待监听器完全启动起来
        new Thread(()->{
        try {
            process.waitFor();
            //TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}).start();

        /*System.out.println(System.currentTimeMillis() + "|alive: " +process.isAlive());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() + "|alive: " +process.isAlive());*/
        return process;
    }

    public static void convertThroughUNO(File inputFile, File outputFile) {
        System.out.format(System.currentTimeMillis() + "|convert %s --> %s\n", inputFile, outputFile);
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(UNO_PORT);
        try {
            try{
                connection.connect();
            }catch (ConnectException ce){
                System.out.println("openoffice uno 连接" + UNO_PORT + " 端口失败，使用命令启动");
                // 如果连接失败
                startLibreOfficeUnoServer();
                connection.connect();
            }
            OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection,DOC_FMT_REGISTRY);
            if(inputFile.getName().toLowerCase().endsWith(".docx")){
                converter.convert(inputFile,DOCX_FMT,outputFile,null);
            }else{
                converter.convert(inputFile, outputFile);
            }
            System.out.format(System.currentTimeMillis() + "|convert %s --> %s end \n", inputFile, outputFile);
        } catch (ConnectException ce) {
            System.out.println("openoffice uno 再次连接" + UNO_PORT + " 端口失败，请求使用当前用户权限执行以下命令启动libreoffice："+UNO_LIS_CMD);
            try {
                System.out.println(IOUtils.toString(Runtime.getRuntime().exec("ps -aA |grep soffice").getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ce.printStackTrace();
        } catch (Exception e){
            e.printStackTrace(System.err);
        } finally{
            try {
                if (connection != null) {
                    connection.disconnect();
                    connection = null;
                }
            } catch (Exception e) {
            }
        }
    }

    public static String executeCommand(String ... command) throws IOException {
        StringBuffer output = new StringBuffer();
        Process p;
        int data =0;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try(ByteArrayOutputStream obaos = new ByteArrayOutputStream();ByteArrayOutputStream ebaos = new ByteArrayOutputStream();){
            ProcessBuilder pb = new ProcessBuilder();
            pb.redirectErrorStream(true);
            pb.command(command);
            p = pb.start();
            //p = Runtime.getRuntime().exec(command);

            inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
            reader = new BufferedReader(inputStreamReader);

            while((data = inputStreamReader.read())!=-1){
                obaos.write(data);
                //System.out.println((byte)data);
            }
            System.out.println(obaos);

            InputStream isErr = p.getErrorStream();
            data =0;
            while((data = isErr.read())!=-1){
                //System.out.println((byte)data);
                ebaos.write(data);
            }
            if(ebaos.size() > 0){
                System.err.println("[error]"+ebaos.toString());
            }
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
            inputStreamReader.close();
        }
        //System.out.println(output.toString());
        return output.toString();

    }
}
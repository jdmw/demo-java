package jd.demo.lib.office.uno;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.moseeker.profile.utils.OfficeUtils;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class A {


    private static final String ERROR_PDF = "Evaluation Only. Created with Aspose.Words. Copyright 2003-2015 Aspose Pty Ltd.";

    //private static final String COMMAND = "libreoffice --invisible --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$";
    //private static final String COMMAND = "xvfb-run -a -s '-screen 0 640x480x16'  libreoffice --invisible --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$";
    //private static final String COMMAND = "soffice --invisible --convert-to pdf:writer_pdf_Export $src$ --outdir $outdir$";
    // libreoffice卡死进程，改成soffice
    private static final String COMMAND = "soffice --headless --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$ ";


    private static final int UNO_PORT = 8100;
    private static final String UNO_LIS_CMD = "nohup soffice --headless --accept=\"socket,host=127.0.0.1,port="+UNO_PORT+";urp;\" --nofirststartwizard &";


    public static void main(String[] args) throws IOException {
        String dir = "/mnt/nfs/attachment/profile_attachement/20196" ;
        String file = "f2dff92d-5850-41d0-9313-19ea3aa22366" ;
        dir = "/Users/huangxia/Downloads/docx" ;
        file = "2" ;

        if(args.length > 0){
            dir = args[0] ;
            file = args[1];
        }

        OfficeUtils.executeCommand(UNO_LIS_CMD);
        /*
        ExecutorService pool = Executors.newFixedThreadPool(20);
        //System.out.println(executeCommand("whoami"));
        new File(dir).list((d,name)->{
            if(name.endsWith(".docx")){
                //pool.submit(()->Word2Pdf(f.getAbsoluteFile(),name.substring(0,name.indexOf("."))));
                convertThroughUNO(new File(d ,name),new File(d ,name.replace("docx","pdf")));
            }
            return true ;
        });
        pool.shutdown();
        */
        //int rst = Word2Pdf(new File(dir).getAbsoluteFile(),file);
        OfficeUtils.convertThroughUNO(new File(dir,file+".docx"),new File(dir,file+".pdf"));
        //System.out.println(executeCommand("ls " + dir + " | grep " + file));
    }

    public static void convertThroughUNO(File inputFile,File outputFile){
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(UNO_PORT);
        try {
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            System.out.format("convert %s --> %s\n",inputFile,outputFile);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{ if(connection != null){connection.disconnect(); connection = null;}}catch(Exception e){}
        }
    }

    // 多线程调用libreoffice有可能存在部分word没有转换
    public static synchronized int Word2Pdf(File dir, String filename) {
        try {
            System.out.println("使用备用方案生成pdf文件");
            //采用备用方案
            File errorPdf = new File(dir,filename+".pdf");
            if(errorPdf.exists()){
                errorPdf.delete();
            }
            //只传入文件夹路径
            String command = COMMAND.replace("$outdir$",dir.getAbsolutePath()).replace("$src$", new File(dir,filename+".docx").getAbsolutePath());
            System.out.println("The word2pdf command is "+command);
            //执行生成命令
            String output = OfficeUtils.executeCommand(command);
            //String output = executeCommand(command.split(" "));
            System.out.println("The pdf profile has been created at "+output);
        }catch (Exception e){
            e.printStackTrace(System.err);
            return 0;
        }
        return 1;
    }

    /*
     * 执行libreoffice命令生成pdf文件
     *
     * @return
     * */
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
            p.waitFor();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
            inputStreamReader.close();
        }
        //System.out.println(output.toString());
        return output.toString();

    }

    /*
     * 执行libreoffice命令生成pdf文件
     *
     * @return
     * */
    public static String executeCommand(String command) throws IOException {
        StringBuffer output = new StringBuffer();
        Process p;
        int data =0;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try(ByteArrayOutputStream obaos = new ByteArrayOutputStream();ByteArrayOutputStream ebaos = new ByteArrayOutputStream();){
            ProcessBuilder pb = new ProcessBuilder();
            pb.redirectError();
            pb.redirectOutput();
            pb.command(command.split(" "));
            p = pb.start();
            //p = Runtime.getRuntime().exec(command);
            p.waitFor(1, TimeUnit.SECONDS);
            inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
            reader = new BufferedReader(inputStreamReader);

            /*while((data = inputStreamReader.read())!=-1){
                obaos.write(data);
                //System.out.println((byte)data);
            }*/
            //System.out.println(obaos);

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

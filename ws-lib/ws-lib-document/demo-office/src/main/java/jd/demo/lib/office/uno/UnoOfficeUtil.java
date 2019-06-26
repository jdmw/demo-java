package jd.demo.lib.office.uno;

import java.io.File;
import java.io.IOException;

import jd.demo.lib.office.uno.src.ProcessPoolOfficeManager;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.artofsolving.jodconverter.office.OfficeUtils;
import org.artofsolving.jodconverter.util.PlatformUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UnoOfficeUtil {
    // 日志记录功能
    private final static Logger logger = LoggerFactory.getLogger(UnoOfficeUtil.class);

    private static int UNO_PORT = 8100 ;
    private static OfficeManager officeManager = new ProcessPoolOfficeManager(UNO_PORT);;
    //private static String OFFICE_HONE = PlatformUtils.isLinux()?"/usr/lib64/libreoffice": "/Applications/LibreOffice.app/Contents";

    static {
       /* if(OFFICE_HONE == null || !new File(OFFICE_HONE).exists()  ){
            OFFICE_HONE = OfficeUtils.getDefaultOfficeHome().getAbsolutePath(); //
        }*/

        try{
            startService();
            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                stopService();
            }));
        }catch(Exception e){
            logger.debug("office启动失败",e);
        }
    }
    private UnoOfficeUtil(){}

    // 打开服务器
    private static void startService() {
        officeManager.start(); // 启动服务
        logger.info("office转换服务启动成功!");
    }

    // 关闭服务器
    private static void stopService() {
        logger.info("关闭office转换服务....");
        if (officeManager != null) {
            officeManager.stop();
        }
        logger.info("关闭office转换成功!");
    }

    public static void convertToPDF(String inputPath, String outputPath) throws IOException {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        convertToPDF(inputFile,outputFile);
    }


    public static void convertToPDF(File inputFile, File outputFile) throws IOException {

        // 判断inputFile文件存在
        if(inputFile.exists()){
            /*if(!outputFile.exists()){
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw e ;
                    //throw new OpenOfficeException(e.getMessage(),e);
                }
            }*/
            // 开始文件转换
            //startService();
            logger.info("进行文档转换:" + inputFile + " --> " + outputFile);

            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            converter.convert(inputFile, outputFile);
            //stopService();
            //logger.info("文档转换完成:" + inputFile + " --> " + outputFile);
        } else {
            // inputFile文件不存在
            throw new RuntimeException("inputFile " + inputFile + "文件不存在");
        }
    }


}
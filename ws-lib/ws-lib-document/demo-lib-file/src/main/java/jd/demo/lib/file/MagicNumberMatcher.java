package jd.demo.lib.file;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Arrays;

public class MagicNumberMatcher {

    private static final byte[] MAGIC_JPEG_HEAD = {(byte) 0xFF,(byte) 0xD8} ;
    private static final byte[] MAGIC_JPEG_END = {(byte) 0xFF,(byte) 0xD9} ;
    private static final byte[] MAGIC_PNG = {(byte) 0x89,(byte) 0x50,(byte) 0x4E,(byte) 0x47,
            (byte) 0x0D,(byte) 0x0A,(byte) 0x1A,(byte) 0x0A} ; // \211 P N G \r \n \032 \n
    private static final byte[] MAGIC_PDF = {(byte) 0x25,(byte) 0x50,(byte) 0x44,(byte) 0x46} ; // %PDF
    private static final byte[] MAGIC_JPG = {(byte) 0xFF,(byte) 0xD8,(byte) 0xFF,(byte) 0xE0} ;
    private static final byte[] MAGIC_MICROSOFT_OFFICE = {(byte) 0xD0,(byte) 0xCF,(byte) 0x11,(byte) 0xE0} ; // doc xls
    private static final byte[] MAGIC_MICROSOFT_OFFICE_NEW = {(byte) 0x50,(byte) 0x4B,(byte) 0x3,(byte) 0x4} ;// docx xlsx

    public enum Matcher{
        JPEG(MagicNumberMatcher.MAGIC_JPEG_HEAD,MagicNumberMatcher.MAGIC_JPEG_END,"JPEG"),
        PNG(MagicNumberMatcher.MAGIC_PNG,"PNG"),
        JPG(MagicNumberMatcher.MAGIC_JPG,"JPG"),
        PDF(MagicNumberMatcher.MAGIC_PDF,"PDF"),
        OFFICE2003(MagicNumberMatcher.MAGIC_MICROSOFT_OFFICE,"DOC","XLS","PPT"),
        OFFICE2007(MagicNumberMatcher.MAGIC_MICROSOFT_OFFICE_NEW,"DOCX","XLSX","PPTX");

        private final String[] fileExtNames ;
        private final byte[] head ;
        private byte[] tail ;

        private Matcher(byte[] head,String ... fileExtNames) {
            this.head = head;
            this.fileExtNames = fileExtNames;
        }
        private Matcher(byte[] head, byte[] tail,String ... fileExtNames) {
            this.head = head;
            this.tail = tail;
            this.fileExtNames = fileExtNames;
        }

        private boolean match(byte[] fileData){
            if ( head != null && head.length > 0){
                for(int i=0;i<head.length;i++){
                    if(fileData[i] != head[i]){
                        return false ;
                    }
                }
            }
            if ( tail != null && tail.length > 0){
                //
                int lenDiff = fileData.length - tail.length;
                if ( lenDiff < 0) return false ;

                for(int i=0;i<tail.length;i++){
                    if(fileData[lenDiff+i] != tail[i]){
                        return false ;
                    }
                }
            }
            return true ;
        }
    }


    public static MagicNumberMatcher.Matcher matchByFilenameAndMagicNumber(String fileName,byte[] fileData){
        int idx = (fileName = fileName.trim()).lastIndexOf(".");
        if ( idx < 0 || idx == fileName.length() - 1 ) return null ;
        String extName = fileName.substring(idx+1).toUpperCase();
        for(MagicNumberMatcher.Matcher  matcher : MagicNumberMatcher.Matcher.values()){
            if(Arrays.binarySearch(matcher.fileExtNames,extName) != -1 && matcher.match(fileData)){
                return matcher ;
            }
        }
        return null ;
    }


    public static void main(String[] args) throws IOException {
        File file = new File("/Users/huangxia/Desktop/EMPLOYEE.XLSX");
        System.out.print(matchByFilenameAndMagicNumber(file.getName(), IOUtils.toByteArray(new FileInputStream(file))));
    }

}

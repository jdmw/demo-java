package jd.diy.db.engine.storage;

import jd.util.io.IOUt;

import java.io.File;

/**
 * Created by huangxia on 2008/9/30.
 */
public class DiyJdEngineStorage {

    // sudo cp  /usr/local/mysql/data/innodbdemo/t1.ibd  ~



    class TableFieldInfo {
        private String name ;
        //private
    }
    final static int start = 0x10170 ;

    public static void main(String[] args){
        File ibd = new File("~/t1.ibd");
        byte[] bytes = IOUt.toByteArray(ibd);

    }
}

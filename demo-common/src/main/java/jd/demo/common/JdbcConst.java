package jd.demo.common;

public class JdbcConst {

    //public static String DB_URL = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false" ;
    public static String DB_URL = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8" ;
    public static String DRIVER = "com.mysql.jdbc.Driver" ;
    public static String USERNAME = "user" ;
    public static String PASSWORD = "pass" ;


    /*static {
        try {
            Class.forName("com.mysql.jdbc.Driver", true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}

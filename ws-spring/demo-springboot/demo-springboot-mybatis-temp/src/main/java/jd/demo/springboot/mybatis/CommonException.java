package jd.demo.springboot.mybatis;


import lombok.NonNull;

/**
 * Created by jdmw on 2020/6/11.
 */
public class CommonException extends RuntimeException{
    private String code ;
    private String message ;

    public CommonException(@NonNull String code, @NonNull String message1) {
        //super(message);
        this.code = code;
        this.message = message1;
    }
}

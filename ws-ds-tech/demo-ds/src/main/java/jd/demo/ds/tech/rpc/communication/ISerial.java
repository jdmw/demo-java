package jd.demo.ds.tech.rpc.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jd.demo.ds.tech.rpc.server.rpcontext.IRpcContext;

/**
   *  序列化
 * @author jdmw
 *
 */
public interface ISerial {

	SerialVO read(InputStream inputSteam) throws IOException, ClassNotFoundException ;

    void write(OutputStream os, Class<?> interfaceClass, Method method,Object... args) throws IOException ;
    
    <R> ResultSerialVO<R> readResult(InputStream inputSteam) throws IOException, ClassNotFoundException;

    public void writeResult(OutputStream os, boolean hasResult,Object result,Throwable throwable) throws IOException ;
}

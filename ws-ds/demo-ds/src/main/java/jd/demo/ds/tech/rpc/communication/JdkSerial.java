package jd.demo.ds.tech.rpc.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;


public class JdkSerial implements ISerial{

    public SerialVO read(InputStream inputSteam) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(inputSteam);
        String interfaceName = ois.readUTF();
        String methodName = ois.readUTF();
        Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
        Object[] args = (Object[]) ois.readObject();
        SerialVO vo = new SerialVO();
        vo.setInterfaceClass(interfaceName);
        vo.setMethodName(methodName);
        vo.setArgs(args);
        vo.setParameterTypes(parameterTypes);
        return vo ;
    }

    public void write(OutputStream os, Class<?> interfaceClass, Method method,Object... args) throws IOException {
        if(!interfaceClass.isInterface()) throw new IllegalArgumentException(interfaceClass.getName() + " is not a interface");
        
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeUTF(interfaceClass.getName());
        oos.writeUTF(method.getName());
        oos.writeObject(method.getParameterTypes());
        oos.writeObject(args);
        oos.flush();
    }

	@Override
	public <R> ResultSerialVO<R> readResult(InputStream inputSteam) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(inputSteam);
		ResultSerialVO<R> vo = new ResultSerialVO<R>();
		/*
		 * vo.setHasResult(ois.readBoolean()); if(vo.isHasResult()) {
		 * vo.setResult((R)ois.readObject()); } vo.setHasResult(ois.readBoolean());
		 * if(vo.isHasError()) { vo.setThrowable((Throwable)ois.readObject()); }
		 */
		vo.setResult((R)ois.readObject());
		vo.setThrowable((Throwable)ois.readObject());
		vo.setHasResult(vo.getResult() != null);
		vo.setHasError(vo.getThrowable() != null);
		return vo ;
	}

	@Override
	public void writeResult(OutputStream os, boolean hasResult,Object result,Throwable throwable) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(result);
		oos.writeObject(throwable);
		
		/*
		 * oos.writeBoolean(hasResult); if(hasResult) { oos.writeObject(result); }
		 * 
		 * if(throwable != null) { oos.writeBoolean(true); oos.writeObject(throwable);
		 * }else { oos.writeBoolean(false); }
		 */
        oos.flush();
	}
    
}

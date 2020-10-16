package jd.demo.se.techs.rmi;

import java.lang.reflect.Field;
import java.rmi.RemoteException;

public class ComputeEngine implements Compute {

	protected static final String NAME = "Compute" ;

	@Override
	public <T> T executeTask(Task<T> t) throws RemoteException {
		for(Field f : t.getClass().getDeclaredFields()) {
			System.out.println(f);
		}
		
		return t.execute();
	}

}

package jd.demo.rpc.thrift.service;

import org.apache.thrift.TException;

import jd.demo.rpc.thrift.bean.Sex;
import jd.demo.rpc.thrift.bean.User;
import jd.demo.rpc.thrift.iface.Interview;

public class InterviewImpl implements Interview.Iface {

	public boolean interview(User user) throws TException {
		if(user == null || !user.isSetSex()) {
			throw new TException("user or its sex is not set ");
		}
		
		if(user.isSetAge() && user.getSex().equals(Sex.M)) {
			if(user.getAge() > 20 && user.getAge() < 40 ) {
				return true ;
			}
		}
		return false ;
	}

}

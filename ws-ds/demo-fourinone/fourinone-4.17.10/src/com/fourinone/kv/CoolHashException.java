package com.fourinone.kv;

import com.fourinone.util.LogUtil;
import com.fourinone.ServiceException;

import java.util.regex.Pattern;

public class CoolHashException extends ServiceException {
	Pattern pk=null,pkw=null;
	
	public CoolHashException(){
		super();
		pk=Pattern.compile("^[a-z0-9A-Z_]+(\\.[a-z0-9A-Z_]+)*$");
		//pkw=Pattern.compile("^([a-z0-9A-Z_]+|\\*?)(\\.([a-z0-9A-Z_]+|\\*?))*$");//Pattern.compile("^([a-z0-9A-Z_]+|\\*{1})(\\.([a-z0-9A-Z_]+|\\*{1}))*$");
		pkw=Pattern.compile("^([a-z0-9A-Z_]+|([a-z0-9A-Z_]*\\*{1}[a-z0-9A-Z_]*))(\\.([a-z0-9A-Z_]+|([a-z0-9A-Z_]*\\*{1}[a-z0-9A-Z_]*)))*$");//2015.7.28
	}
	
	public CoolHashException(String msg){
		super(msg);
	}

	public CoolHashException(String msg, Throwable cause){
		super(msg, cause);
	}
	
	public CoolHashException(Throwable cause){
		super(cause);
	}
	
	<T> String checkTargetLog(Class<T> value){
		return "this class type cant be provided "+value;
	}
	
	<T> void checkTargetMatch(Class<T> value) throws CoolHashException{
		try{
			 ConstantBit.Target.valueOf(value.getSimpleName().toUpperCase());
		}catch(Exception e){
			throw new CoolHashException(checkTargetLog(value));
		}
	}
	
	void checking(String str) throws CoolHashException{
		if(str==null||pk.matcher(str).matches()==false)
			throw new CoolHashException("[Syntax Error] the keyname '"+str+"' must be composed of 'a-z' or '0-9' or '_' and split by '.'");
		if(str.length()> DumpAdapter.mk)
			throw new CoolHashException("[Exceed Error] the size of the keyname exceed");
		
	}
	
	void checking(byte[] bts) throws CoolHashException{
		if(bts==null||bts.length<=1||bts.length>DumpAdapter.mv)
			throw new CoolHashException("[Value Error] value be null or size of the value exceed");
		//if(CoolBitSet) checkingCoolBitSet
	}
	
	boolean checkKey(String key){
		try{
			checking(key);
		}catch(Exception ex){
			LogUtil.info("[CoolHashException]", "[checkKey]", ex);
			return false;
		}
		return true;
	}
	
	boolean checkWild(String str){
		if(str==null||pkw.matcher(str).matches()==false){
			LogUtil.fail("[CoolHashException]","[Syntax Error]","the findname '"+str+"' must be composed of 'a-z' or '0-9' or '_' or '*' and split by '.'");
			return false;
		}
		return true;
	}
	
	boolean checkKeyValue(Object key, Object value){
		try{
			checkingType(key,value);
			checking((String)key,(byte[])value);
		}catch(Exception ex){
			LogUtil.info("[CoolHashException]", "[checkKeyValue]", ex);
			return false;
		}
		return true;
	}
	
	void checkingType(Object key, Object value)throws CoolHashException{
		if(key==null||value==null)
			throw new CoolHashException("[NullPointerException] key or value cant be null!");
		if(!(key instanceof String&&value.getClass().isArray()&&value.getClass().getComponentType().equals(byte.class)))
			throw new CoolHashException("[ClassCastException] key type or value type error!");
	}
	
	//2017.10
	void checkingPlusValue(Object plusValue) throws CoolHashException{
		if(!(plusValue instanceof String)&&!(plusValue instanceof Number))
			throw new CoolHashException("[TypeErrorException] the plusValue is not instanceof Number or String!");
	}
	
	Object getCheckPlus(Object value, Object plusValue){
		if(plusValue instanceof Short&&(value==null||value instanceof Short))
			return value==null?plusValue:(Short)value+(Short)plusValue;
		else if(plusValue instanceof Integer&&(value==null||value instanceof Integer))
			return value==null?plusValue:(Integer)value+(Integer)plusValue;
		else if(plusValue instanceof Float&&(value==null||value instanceof Float))
			return value==null?plusValue:(Float)value+(Float)plusValue;
		else if(plusValue instanceof Long&&(value==null||value instanceof Long))
			return value==null?plusValue:(Long)value+(Long)plusValue;
		else if(plusValue instanceof Double&&(value==null||value instanceof Double))
			return value==null?plusValue:(Double)value+(Double)plusValue;
		else if(plusValue instanceof String&&(value==null||value instanceof String))
			return value==null?plusValue:(String)value+(String)plusValue;
		else
			LogUtil.fail("[CoolHashException]", "[ValuePlus Type Error]", "the plusValue is null or not Number&String or is not same type of the value!");
		return null;
	}
	
	void checkingCoolBitSet(int index, String logical) throws CoolHashException{
		if(index<0||index>=CoolBitSet.defaultMax)
			throw new CoolHashException("[Exceed Error] the size of the CoolBitSet less-equal 0 or exceed:"+CoolBitSet.defaultMax);
			
		if(logical!=null){
			String logicallower = logical.toLowerCase();
			if(!logicallower.equals("and")&&!logicallower.equals("or")&&!logicallower.equals("xor")&&!logicallower.equals("andnot")&&!logicallower.equals("new"))
				throw new CoolHashException("[Syntax Error] the logical is not and/or/xor/andnot/new:"+logical);
		}
	}
	
	void checkingRegex(String regex)throws CoolHashException{
		try{
			Pattern.compile(regex);
		}catch(Exception e){
			throw new CoolHashException(e);
		}
	}
	
	void exceedException(){
		LogUtil.fail("[CoolHashException]", "[ExceedException]", "the size of the key/value exceed!");
	}
	
	void checking(String str, byte[] bts) throws CoolHashException{
		checking(str);
		checking(bts);
	}
	
	void checkKeyPoint(String keyPoint) throws CoolHashException
	{
		checking(keyPoint);
		if(!keyPoint.endsWith("\u002E\u006B\u0065\u0079"))
			throw new CoolHashException("[Syntax Error] keypoint must be end with .key");
	}
	
	void pointLoopException(){
		LogUtil.fail("[CoolHashException]", "[PointLoopException]", "key point loop be canceled, return point key string maybe cause unpredictable data format conversion error!");
	}
	
	void rollback(){
		LogUtil.fail("[CoolHashException]", "[TransactionException]", "something exception, transaction roll back");
	}
	
	void commitException(Exception ex){
		LogUtil.fail("[CoolHashException]", "[CommitException]", ex.toString());
	}
	
	void rollbackException(Exception ex){
		LogUtil.info("[CoolHashException]", "[RollbackException]", "roll back failed", ex);
	}
}
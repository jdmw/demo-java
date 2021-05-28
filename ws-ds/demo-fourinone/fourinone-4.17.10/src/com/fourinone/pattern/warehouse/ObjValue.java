package com.fourinone.pattern.warehouse;

import com.fourinone.pattern.park.ParkStatg;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.List;

public class ObjValue extends LinkedHashMap implements ParkStatg
{
	public void setString(String keyStr, String valueStr){
		super.put(keyStr, valueStr);
	}

	public String getString(Object keyStr){
		return (String)super.get(keyStr);
	}
	
	public int getStringInt(String keyStr){
		return Integer.parseInt(getString(keyStr));
	}
	
	public boolean getStringBool(String keyStr){
		return Boolean.parseBoolean(getString(keyStr));
	}
	
	public String getSeparator(Object keyStr){
		return getString(keyStr).replaceAll("\\\\","/");
	}
	

	public byte getByte(Object keyStr){
		Object obj = super.get(keyStr);
		return obj!=null?(byte)obj:Byte.MIN_VALUE;
	}
	
	public short getShort(Object keyStr){
		Object obj = super.get(keyStr);
		return obj!=null?(short)obj:Short.MIN_VALUE;
	}
	

	public int getInt(Object keyStr){
		Object obj = super.get(keyStr);
		return obj!=null?(int)obj:Integer.MIN_VALUE;
	}
	
	public boolean getBoolean(Object keyStr){
		Object obj = super.get(keyStr);
		return obj!=null?(boolean)obj:false;
	}
	
	public ObjValue getWidely(String widelykey){
		ObjValue obj = new ObjValue();
		for(Iterator iter=this.keySet().iterator();iter.hasNext();){
			String curkey = (String)iter.next();
			if(Pattern.matches(widelykey, curkey))
				obj.put(curkey, this.getObj(curkey));
		}
		return obj;
	}
	
	public ObjValue removeWidely(String widelykey){
		ObjValue obj = new ObjValue();
		
		List<String> keylist = new ArrayList<String>();
		for(Iterator iter=this.keySet().iterator();iter.hasNext();){
			String curkey = (String)iter.next();
			if(Pattern.matches(widelykey, curkey))
				keylist.add(curkey);
		}
		
		for(String ck:keylist){
			Object rvobj = this.remove(ck);
			if(rvobj!=null)obj.put(ck, rvobj);
		}
		
		return obj;
	}
	
	public void setObj(String keyStr, Object valueObj){
		super.put(keyStr, valueObj);
	}

	public Object getObj(String keyStr){
		return super.get(keyStr);
	}

	public ArrayList getObjNames(){
		return new ArrayList(keySet());
	}
	
	public ArrayList getObjValues() {
		return new ArrayList(values());
	}
	
	public static void main(String[] args){
		ObjValue ov = new ObjValue();
		ov.setString("1","1");
		ov.setString("1.1","11");
		ov.setString("1.1.1","1111");
		ov.setString("1.1.1.1","1111");
		ov.setString("1.2","12");
		ov.setString("1.2.1","121");
		ov.setString("2","2");
		ov.setString("22","22");
		ov.setString("domain","1");
		ov.setString("domain._me_ta.version","1");
		ov.setString("domain.node1","1");
		ov.setString("domain.node1._me_ta.version","1");
		ov.setString("domain.node2","1");
		ov.setString("domain.node2._me_ta.version","1");
		ov.setString("domain.node2.a","1");
		ov.setString("domain.node2.a._me_ta.version","1");
		System.out.println(ov.getWidely("1.[^.]*.1"));
		System.out.println(ov.getWidely("1.1.*"));
		System.out.println(ov.getWidely("2\\w"));
		System.out.println(ov.getWidely("domain..*._me_ta.version"));
		System.out.println(ov.getWidely("domain.[^_me_ta]*"));
		String a = "domain.node1._me_ta.version";
		System.out.println(a.substring(0,a.indexOf("._me_ta.")));
	}
}
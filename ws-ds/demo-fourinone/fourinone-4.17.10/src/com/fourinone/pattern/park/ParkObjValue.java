package com.fourinone.pattern.park;

import com.fourinone.util.LogUtil;
import com.fourinone.pattern.warehouse.ObjValue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

public class ParkObjValue extends ObjValue
{	
	public static boolean checkGrammar(String keyname){
		if(keyname!=null&&Pattern.matches("^[a-z0-9A-Z_-]+$", keyname))
			return true;
		else{
			LogUtil.info("[KeyName]", "[error domain or node name]", keyname);
			return false;
		}
	}
	
	public static boolean checkGrammar(String domain, String node){
		return checkGrammar(domain)&&checkGrammar(node);
	}
	
	public static boolean checkGrammar(String domain, String node, Object obj){
		if(obj==null)
			LogUtil.info("[checkGrammar]", "[error]", obj);
		return checkGrammar(domain, node)&&obj!=null;
	}
	
	public static String getDomainnodekey(String domain, String node){
		String domainnodekey = node==null?domain:domain+"."+node;
		return domainnodekey;
	}
	
	public static String[] getDomainNode(String domainnodekey){
		//String[] keyarr = domainnodekey.split("\\.");
		//System.out.println("getDomainNode domainnodekey:"+domainnodekey);
		return domainnodekey.split("\\.");
	}
	
	public static void main(String[] args)
	{
		//String[] keyarr = getDomainNode("aaa.bbb");
		//System.out.println("getDomainNode keyarr.length:"+keyarr.length);
		ParkObjValue ob = new ParkObjValue();
		ob.checkGrammar("d","a.b");
		//System.out.println(Pattern.matches("^[a-z0-9A-Z_-]+$", " d "));
		ob.setString("a"+ParkMeta.getVersion(),"0");
		ob.setString("a.b"+ParkMeta.getVersion(),"0");
		ob.setString("a.c"+ParkMeta.getVersion(),"0");
		System.out.println(ob.getWidely(ParkMeta.getVersion("a"+"\\..+")));
	}
	
	public ObjValue getNodeWidely(String nodekey){
		ObjValue obj = new ObjValue();
		Object getobj = this.getObj(nodekey);
		if(getobj!=null)
		{
			obj.put(nodekey, this.getObj(nodekey));
			for(Iterator iter=this.keySet().iterator();iter.hasNext();){
				String curkey = (String)iter.next();
				if(Pattern.matches(nodekey+"\\..*", curkey))
					obj.put(curkey, this.getObj(curkey));
			}
		}
		return obj;
	}
	
	public ObjValue getNode(String domain, String node)
	{
		ObjValue ov = new ObjValue();
		if(domain!=null)
		{
			if(node!=null)
			{
				String domainnodekey = getDomainnodekey(domain, node);
				Object obj = this.getObj(domainnodekey);
				if(obj!=null)
					ov.setObj(domainnodekey, obj);

				for (ParkMeta parkMeta : Arrays.asList(ParkMeta.VERSION, ParkMeta.CREATE_BY, ParkMeta.AUTH, ParkMeta.CREATE_IP, ParkMeta.CREATE_TIME,
						ParkMeta.PROP, ParkMeta.UPDATE_BY, ParkMeta.UPDATE_IP, ParkMeta.UPDATE_TIME)) {
					String key = parkMeta.get(domainnodekey);
					Object value = this.getObj(key);
					if(value != null){
						ov.setObj(key,value);
					}
				}
			}
			else
				ov = getNodeWidely(domain);
		}
		return ov;
	}
	
	public ObjValue getParkInfo(){
		ParkObjValue obj = (ParkObjValue)this.clone();
		return obj;
	}
	
	public List<String[]> getParkInfoExp(long exp)
	{
		ArrayList<String[]> keyexplist = new ArrayList<String[]>();

		for(Iterator iter=this.keySet().iterator();iter.hasNext();){
			String curkey = (String)iter.next();
			if(curkey.indexOf(ParkMeta.getCreateTime())!=-1)
			{
				String domainnodekey = curkey.substring(0,curkey.indexOf(ParkMeta.getCreateTime()));
				String[] keyarr = getDomainNode(domainnodekey);
				if(keyarr!=null&&keyarr.length==2)
				{
					String propvalue = this.getString(ParkMeta.getProp(domainnodekey));
					//System.out.println("propvalue:"+propvalue);
					if(propvalue==null||!propvalue.equals(ParkMeta.getHeartBeat()))
						if(System.currentTimeMillis()-(Long)this.get(curkey)>=exp)
							keyexplist.add(keyarr);
				}
			}
		}
		//System.out.println("keyexplist:"+keyexplist);
		return keyexplist;
	}
	
	public ObjValue removeNodeWidely(String nodekey){
		ObjValue obj = new ObjValue();
		Object node = this.remove(nodekey);

		if(node!=null)
		{
			obj.put(nodekey, node);

			List<String> keylist = new ArrayList<String>();
			for(Iterator iter=this.keySet().iterator();iter.hasNext();){
				String curkey = (String)iter.next();
				if(Pattern.matches(nodekey+"\\..*", curkey))
					keylist.add(curkey);
			}

			for(String ck:keylist){
				Object rvobj = this.remove(ck);
				if(rvobj!=null)obj.put(ck, rvobj);
			}
		}

		return obj;
	}
	
	public ObjValue removeDomain(String domain)
	{
		ObjValue ov = new ObjValue();
		if(domain!=null)
		{
			Object obj = this.remove(domain);
			Object version = this.remove(ParkMeta.getVersion(domain));
			Object createby = this.remove(ParkMeta.getCreateBy(domain));
			Object creatip = this.remove(ParkMeta.getCreateIP(domain));
			Object creattime = this.remove(ParkMeta.getCreateTime(domain));

			if(obj!=null)
				ov.setObj(domain, obj);
			if(version!=null)
				ov.setObj(ParkMeta.getVersion(domain), version);
			if(createby!=null)
				ov.setObj(ParkMeta.getCreateBy(domain), createby);
			if(creatip!=null)
				ov.setObj(ParkMeta.getCreateIP(domain), creatip);
			if(creattime!=null)
				ov.setObj(ParkMeta.getCreateTime(domain), creattime);
		}
		return ov;
	}
	
	public ObjValue removeNode(String domain, String node)
	{
		ObjValue ov = new ObjValue();
		if(domain!=null)
		{
			if(node!=null)
			{
				String domainnodekey = getDomainnodekey(domain, node);
				Object obj = this.remove(domainnodekey);
				Object version = this.remove(ParkMeta.getVersion(domainnodekey));
				Object createby = this.remove(ParkMeta.getCreateBy(domainnodekey));
				Object auth = this.remove(ParkMeta.getAuth(domainnodekey));
				Object creatip = this.remove(ParkMeta.getCreateIP(domainnodekey));
				Object creattime = this.remove(ParkMeta.getCreateTime(domainnodekey));
				Object prop = this.remove(ParkMeta.getProp(domainnodekey));
				Object updateby = this.remove(ParkMeta.getUpdateBy(domainnodekey));
				Object updateip = this.remove(ParkMeta.getUpdateIP(domainnodekey));
				Object updatetime = this.remove(ParkMeta.getUpdateTime(domainnodekey));

				if(obj!=null)
					ov.setObj(domainnodekey, obj);
				if(version!=null)
					ov.setObj(ParkMeta.getVersion(domainnodekey), version);
				if(createby!=null)
					ov.setObj(ParkMeta.getCreateBy(domainnodekey), createby);
				if(auth!=null)
					ov.setObj(ParkMeta.getAuth(domainnodekey), auth);
				if(creatip!=null)
					ov.setObj(ParkMeta.getCreateIP(domainnodekey), creatip);
				if(creattime!=null)
					ov.setObj(ParkMeta.getCreateTime(domainnodekey), creattime);
				if(prop!=null)
					ov.setObj(ParkMeta.getProp(domainnodekey), prop);
				if(updateby!=null)
					ov.setObj(ParkMeta.getUpdateBy(domainnodekey), updateby);
				if(updateip!=null)
					ov.setObj(ParkMeta.getUpdateIP(domainnodekey), updateip);
				if(updatetime!=null)
					ov.setObj(ParkMeta.getUpdateTime(domainnodekey), updatetime);
			}
			else
				ov = removeNodeWidely(domain);
		}
		return ov;
	}
}